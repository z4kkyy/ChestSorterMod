package com.z4kkyy.chestsorter;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

// import net.minecraft.resources.ResourceLocation;
// import net.minecraft.world.item.Item;
// import net.minecraft.world.item.ItemStack;
// import net.minecraft.world.item.Items;
// import net.minecraft.world.level.block.state.BlockState;
// import net.minecraft.world.level.block.Block;
// import net.minecraft.core.component.DataComponentMap;
import net.minecraftforge.eventbus.api.SubscribeEvent;
// import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.event.level.BlockEvent;


public class BlockBreakEventHandler {

    public static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        // BlockState state = event.getState();
        // Block block = state.getBlock();
        // Item blockItem = block.asItem();

        // ItemStack blockItemStack = new ItemStack(blockItem);
        // DataComponentMap blockProperties = blockItemStack.getComponents();
        // if (blockProperties != null) {
        //     LOGGER.info("Block properties: {}", blockProperties);
        // } else {
        //     LOGGER.info("No block properties found for item: {}", blockItem.getName().getString());
        // }

        // ResourceLocation regName = ForgeRegistries.ITEMS.getKey(blockItem);

        // if (blockItem == Items.AIR || regName == null) {
        //     LOGGER.info("Broken block has no item form");
        // } else {
        //     LOGGER.info("Broken block as item registry name: {}", regName);
        // }
    }
}
