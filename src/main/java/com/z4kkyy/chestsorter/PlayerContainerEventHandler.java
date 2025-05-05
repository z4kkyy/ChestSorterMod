package com.z4kkyy.chestsorter;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import net.minecraft.world.Container;
import net.minecraft.world.CompoundContainer;
// import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class PlayerContainerEventHandler {

    public static final Logger LOGGER = LogUtils.getLogger();

    private static final Set<MenuType<?>> SUPPORTED_CHEST_MENU_TYPES = Set.of(
            MenuType.GENERIC_9x3,
            MenuType.GENERIC_9x6
    );

    @SubscribeEvent
    public void onContainerClose(PlayerContainerEvent.Close event) {
        // LOGGER.info("onContainerClose event fired for {}", event.getEntity().getName().getString());
        AbstractContainerMenu menu = event.getContainer();

        MenuType<?> type;
        try {
            type = menu.getType();
        } catch (UnsupportedOperationException e) {
            // Player's own inventory or other unsupported container
            LOGGER.debug("Skipping unsupported container: {}, maybe user inventory?", e.getMessage());
            return;
        }

        // Check if the menu type is generic
        if (!SUPPORTED_CHEST_MENU_TYPES.contains(type)) return;

        // Check if the menu is a ChestMenu, not other GUI
        if (!(menu instanceof ChestMenu chestMenu)) return;

        // Get actual inventory from the menu
        Container inv = chestMenu.getContainer();

        // Check the inventory is a standard chest or double chest
        if (isStandardChestInventory(inv)) {
            // Sort and merge
            try {
                sortAndMergeChestContents(inv);
            } catch (Exception e) {
                LOGGER.error("Error during sorting chest contents", e);
            }
            // Player player = event.getEntity();
            // LOGGER.info("Chest sort logic complete for {}", player.getName().getString());
        }
    }

    private boolean isStandardChestInventory(Container inv) {
        // Check if the inventory is a ChestBlockEntity (single chest)
        if (inv instanceof ChestBlockEntity) {
            // LOGGER.debug("Found single chest inventory");
            return true;
        }

        // Large chest is wrapped as CompoundContainer (size 54)
        // We should check if Container objects wrapped by CompoundContainer is actually ChestBlockEntity
        // As they are private fields, use reflection maybe?
        // :(
        if (inv instanceof CompoundContainer && inv.getContainerSize() == 54) {
            // LOGGER.debug("Found large chest inventory");
            return true;
        }

        return false;
    }

    private void sortAndMergeChestContents(Container container) {
        if (container.getContainerSize() <= 1) return;

        List<ItemStack> itemStackList = new ArrayList<>();
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack s = container.getItem(i);
            if (!s.isEmpty()) {
                itemStackList.add(s.copy());
                container.setItem(i, ItemStack.EMPTY);
            }
        }

        mergeStacks(itemStackList);
        itemStackList.sort(ItemSorter.getItemSorter());

        int idx = 0;
        for (ItemStack s : itemStackList) {
            if (idx >= container.getContainerSize()) break;
            container.setItem(idx++, s);
        }

        // Sync with client
        container.setChanged();
    }

    private static void mergeStacks(List<ItemStack> list) {
        List<ItemStack> merged = new ArrayList<>();

        outer:
        for (ItemStack current : list) {
            if (current.isEmpty()) continue;

            // keep Itemstack which can't be stacked
            if (current.getMaxStackSize() == 1) {
                merged.add(current.copy());
                continue;
            }

            // Look for a stack to merge with
            // Check if the ItemStack has the same item and the same NBT data
            for (ItemStack acc : merged) {
                if (ItemStack.isSameItemSameComponents(current, acc)
                        && acc.getCount() < acc.getMaxStackSize()) {

                    int transferable = Math.min(
                            current.getCount(),
                            acc.getMaxStackSize() - acc.getCount()
                    );

                    acc.grow(transferable);
                    current.shrink(transferable);

                    if (current.isEmpty()) continue outer;
                }
            }

            // Add the remaining stack to the merged list
            merged.add(current.copy());
        }

        list.clear();
        list.addAll(merged);
    }
}
