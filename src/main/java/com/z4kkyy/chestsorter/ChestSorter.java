package com.z4kkyy.chestsorter;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(ChestSorter.MODID)
public class ChestSorter {

    public static final String MODID = "chestsorter";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChestSorter(FMLJavaModLoadingContext context) {
        context.getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(new PlayerContainerEventHandler());
        MinecraftForge.EVENT_BUS.register(new BlockBreakEventHandler());
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("ChestSorter mod is setting up!");
    }
}
