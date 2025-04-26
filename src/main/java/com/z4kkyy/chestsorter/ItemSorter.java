package com.z4kkyy.chestsorter;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemSorter {

    private static final Map<String, Integer> CATEGORY_PRIORITIES = createCategoryPriorities();

    public static Comparator<ItemStack> getItemSorter() {
        return Comparator.comparing((ItemStack stack) -> stack.isEmpty() ? 1 : 0)
                .thenComparing(ItemSorter::getItemCategory);
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
        priorities.put("cobblestone", priority++);
        priorities.put("dirt", priority++);
        priorities.put("sand", priority++);
        priorities.put("gravel", priority++);

        // ==== 建材 - 汎用 ====
        priorities.put("stairs", priority++);
        priorities.put("slab", priority++);
        priorities.put("fence", priority++);
        priorities.put("door", priority++);
        priorities.put("trapdoor", priority++);
        priorities.put("wall", priority++);
        priorities.put("button", priority++);
        priorities.put("pressure_plate", priority++);

        // ==== 自然ブロック ====
        priorities.put("leaves", priority++);
        priorities.put("sapling", priority++);
        priorities.put("flower", priority++);
        priorities.put("grass", priority++);

        // ==== 装飾関連 ====
        priorities.put("wool", priority++);
        priorities.put("carpet", priority++);
        priorities.put("bed", priority++);
        priorities.put("banner", priority++);
        priorities.put("candle", priority++);
        priorities.put("terracotta", priority++);
        priorities.put("concrete", priority++);
        priorities.put("glass", priority++);

        // ==== 鉱石 ====
        priorities.put("_ore", priority++);

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
        priorities.put("quartz", priority++);
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

        // ==== 交通手段 ====
        priorities.put("boat", priority++);
        priorities.put("minecart", priority++);

        // ==== 食料 ====
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

        String path = regName.getPath();

        // Check for exact match in CATEGORY_PRIORITIES
        if (CATEGORY_PRIORITIES.containsKey(path)) {
            return CATEGORY_PRIORITIES.get(path);
        }

        // Check for partial match in CATEGORY_PRIORITIES
        for (Map.Entry<String, Integer> entry : CATEGORY_PRIORITIES.entrySet()) {
            String key = entry.getKey();

            if (path.equals(key) ||
                path.startsWith(key + "_") ||
                path.endsWith("_" + key) ||
                path.contains("_" + key + "_")) {

                // LOGGER.info("Partial match: {} -> {} (key: {})", path, entry.getValue(), key);
                return entry.getValue();
            }
        }

        // Check for food items
        if (stack.getComponents().has(DataComponents.FOOD)) {
            for (String foodKey : new String[]{"food", "meat", "fishes", "eggs"}) {
                if (CATEGORY_PRIORITIES.containsKey(foodKey)) {
                    return CATEGORY_PRIORITIES.get(foodKey);
                }
            }
        }

        // Check for block items
        if (item instanceof BlockItem) {
            return 5000;
        }

        // Check for other items
        return 8000;
    }
}
