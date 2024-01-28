package net.goose.lifesteal;

import net.goose.lifesteal.advancement.ModCriteria;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.blockentity.ModBlockEntityTypes;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.common.tab.ModTabs;
import net.goose.lifesteal.configuration.ModConfig;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LifeSteal {
    public static final String MOD_ID = "lifesteal";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static ModConfig config;

    public static void init() {
        LOGGER.info("Lifestealers are on the loose!");
        ModItems.register();
        ModBlocks.register();
        ModBlockEntityTypes.register();
        ModTabs.register();
    }
}