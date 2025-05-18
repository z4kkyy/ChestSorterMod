package com.z4kkyy.chestsorter;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
// import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;


public class ItemSorter {

    public static final Logger LOGGER = LogUtils.getLogger();

    private static final Map<String, Integer> CATEGORY_PRIORITIES = createCategoryPriorities();

    public static Comparator<ItemStack> getItemSorter() {
        return Comparator.comparing((ItemStack stack) -> stack.isEmpty() ? 1 : 0)
                .thenComparing(ItemSorter::getItemCategory)
                .thenComparing(stack -> {
                    if (stack.isEmpty()) return "";
                    Item item = stack.getItem();
                    ResourceLocation regName = ForgeRegistries.ITEMS.getKey(item);
                    return regName != null ? regName.toString() : "";
                });
    }

    private static Map<String, Integer> createCategoryPriorities() {
        Map<String, Integer> priorities = new LinkedHashMap<>();
        int priority = 0;

        // ==== ツールと武器 ====
        priorities.put("sword", priority++);
        priorities.put("axe", priority++);
        priorities.put("pickaxe", priority++);
        priorities.put("shovel", priority++);
        priorities.put("hoe", priority++);
        priorities.put("bow", priority++);
        priorities.put("trident", priority++);
        priorities.put("crossbow", priority++);
        priorities.put("elytra", priority++);
        priorities.put("shield", priority++);

        // ==== 防具 ====
        priorities.put("helmet", priority++);
        priorities.put("chestplate", priority++);
        priorities.put("leggings", priority++);
        priorities.put("boots", priority++);
        priorities.put("shield", priority++);
        priorities.put("armor", priority++);
        priorities.put("smithing_template", priority++);

        // ==== 建材 - 木材関連 ====
        priorities.put("log", priority++);
        priorities.put("wood", priority++);
        priorities.put("stem", priority++);
        priorities.put("planks", priority++);

        // ==== 木材加工品 ====
        priorities.put("wooden", priority++);
        priorities.put("sign", priority++);

        // ==== 石材関連 ====
        priorities.put("stone", priority++);
        priorities.put("cobble", priority++);
        priorities.put("deepslate", priority++);
        priorities.put("cobblestone", priority++);
        priorities.put("granite", priority++);
        priorities.put("diorite", priority++);
        priorities.put("andesite", priority++);

        // ==== 建材 - 汎用 ====
        priorities.put("slab", priority++);
        priorities.put("stairs", priority++);
        priorities.put("fence", priority++);
        priorities.put("door", priority++);
        priorities.put("trapdoor", priority++);
        priorities.put("wall", priority++);
        priorities.put("button", priority++);
        priorities.put("pressure_plate", priority++);

        // ==== その他汎用 ====
        priorities.put("dirt", priority++);
        priorities.put("sand", priority++);
        priorities.put("sandstone", priority++);
        priorities.put("gravel", priority++);

        // ==== 装飾関連 ====
        priorities.put("coral_block", priority++);
        priorities.put("coral", priority++);
        priorities.put("wool", priority++);
        priorities.put("carpet", priority++);
        priorities.put("bed", priority++);
        priorities.put("banner", priority++);
        priorities.put("candle", priority++);
        priorities.put("terracotta", priority++);
        priorities.put("concrete", priority++);
        priorities.put("glass", priority++);
        priorities.put("pumpkin", priority++);

        // ==== 素材 ====
        priorities.put("ingot", priority++);
        priorities.put("nugget", priority++);
        priorities.put("coal", priority++);
        priorities.put("iron", priority++);
        priorities.put("copper", priority++);
        priorities.put("gold", priority++);
        priorities.put("redstone", priority++);
        priorities.put("lapis", priority++);
        priorities.put("diamond", priority++);
        priorities.put("emerald", priority++);
        priorities.put("netherite", priority++);
        priorities.put("dripstone", priority++);
        priorities.put("quartz", priority++);
        priorities.put("sculk", priority++);

        // ==== その他ブロック ====
        priorities.put("ore", priority++);
        priorities.put("amethyst", priority++);

        // ==== 機能ブロック ====
        priorities.put("chest", priority++);
        priorities.put("furnace", priority++);
        priorities.put("table", priority++);
        priorities.put("shulker_box", priority++);
        priorities.put("anvil", priority++);
        priorities.put("bookshelf", priority++);
        priorities.put("lectern", priority++);
        priorities.put("barrel", priority++);

        // ==== Utility ====
        priorities.put("torch", priority++);
        priorities.put("bucket", priority++);
        priorities.put("firework", priority++);

        // ==== レッドストーン関連 ====
        priorities.put("redstone", priority++);
        priorities.put("piston", priority++);
        priorities.put("rail", priority++);
        priorities.put("observer", priority++);
        priorities.put("comparator", priority++);
        priorities.put("repeater", priority++);
        priorities.put("hopper", priority++);
        priorities.put("dropper", priority++);
        priorities.put("dispenser", priority++);

        // ==== 自然ブロック ====
        priorities.put("leaves", priority++);
        priorities.put("sapling", priority++);
        priorities.put("flower", priority++);
        priorities.put("grass", priority++);
        // priorities.put("kerp", priority++);

        // ==== 交通手段 ====
        priorities.put("boat", priority++);
        priorities.put("minecart", priority++);

        // ==== 食料 ====
        priorities.put("food", priority++);
        priorities.put("cake", priority++);
        priorities.put("apple", priority++);
        priorities.put("bread", priority++);
        priorities.put("beef", priority++);
        priorities.put("porkchop", priority++);
        priorities.put("chicken", priority++);
        priorities.put("fish", priority++);
        priorities.put("potato", priority++);
        priorities.put("carrot", priority++);
        priorities.put("stew", priority++);
        priorities.put("soup", priority++);

        // ==== その他のカテゴリ ====
        priorities.put("arrow", priority++);
        priorities.put("bundle", priority++);
        priorities.put("compass", priority++);
        priorities.put("map", priority++);
        priorities.put("bucket", priority++);
        priorities.put("book", priority++);
        priorities.put("paper", priority++);
        priorities.put("dye", priority++);
        priorities.put("music_disc", priority++);
        priorities.put("potion", priority++);
        priorities.put("splash_potion", priority++);
        priorities.put("lingering_potion", priority++);
        priorities.put("spawn_egg", priority++);
        priorities.put("enchanted_book", priority++);
        priorities.put("experience_bottle", priority++);
        priorities.put("sherd", priority++);

        return priorities;
    }

    private static int getItemCategory(ItemStack stack) {
        if (stack.isEmpty()) return 10000;

        Item item = stack.getItem();
        ResourceLocation regName = ForgeRegistries.ITEMS.getKey(item);
        if (regName == null) return 9000;

        // LOGGER.info("getItemCategory called for item: {}", regName);

        FoodProperties foodComponent = stack.getComponents().get(DataComponents.FOOD);
        if (foodComponent != null) {
            // LOGGER.debug("Food component found: {}, value: {}", foodComponent.toString(), CATEGORY_PRIORITIES.get("food"));
            return CATEGORY_PRIORITIES.get("food");
        }

        String path = regName.getPath();

        // Check for exact match in CATEGORY_PRIORITIES
        if (CATEGORY_PRIORITIES.containsKey(path)) {
            // LOGGER.debug("Exact match found: {}", path);
            return CATEGORY_PRIORITIES.get(path);
        }

        // Check for partial match in CATEGORY_PRIORITIES
        int categoryPriority = -1;
        for (Map.Entry<String, Integer> entry : CATEGORY_PRIORITIES.entrySet()) {
            String key = entry.getKey();

            if (path.contains(key)) {
                // LOGGER.debug("Partial match found: {} -> {}", path, key);
                categoryPriority = entry.getValue();
            }
        }

        if (categoryPriority != -1) {
            // LOGGER.debug("Partial match found for path: {}, Final priority value: {}", path, categoryPriority);
            return categoryPriority;
        }

        // Check for other items
        // LOGGER.debug(path + " is not in CATEGORY_PRIORITIES, returning default priority.");
        return 8000;
    }
}
