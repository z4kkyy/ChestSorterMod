package com.z4kkyy.chestsorter;

// import net.minecraft.resources.ResourceLocation;
// import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
// import net.minecraftforge.registries.ForgeRegistries;

// import java.util.List;
// import java.util.Set;
// import java.util.stream.Collectors;

// Configuration class for the Chest Sorter mod
@Mod.EventBusSubscriber(modid = ChestSorter.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue ENABLE_SORTING = BUILDER
            .comment("Whether to enable chest sorting when a chest is closed")
            .define("enableSorting", true);

    private static final ForgeConfigSpec.BooleanValue ENABLE_MERGING = BUILDER
            .comment("Whether to enable merging of similar items when sorting")
            .define("enableMerging", true);

    private static final ForgeConfigSpec.BooleanValue SHOW_SORT_MESSAGE = BUILDER
            .comment("Whether to show a message when a chest is sorted")
            .define("showSortMessage", false);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean enableSorting;
    public static boolean enableMerging;
    public static boolean showSortMessage;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        enableSorting = ENABLE_SORTING.get();
        enableMerging = ENABLE_MERGING.get();
        showSortMessage = SHOW_SORT_MESSAGE.get();
    }
}
