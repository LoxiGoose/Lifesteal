package net.goose.lifesteal.common.item;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.item.custom.HeartCoreItem;
import net.goose.lifesteal.common.item.custom.HeartCrystalItem;
import net.goose.lifesteal.common.item.custom.ReviveCrystalItem;
import net.goose.lifesteal.common.item.custom.ReviveHeadItem;
import net.goose.lifesteal.registry.DeferredRegistry;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ModItems {
    public static final DeferredRegistry<Item> ITEMS = DeferredRegistry.create(LifeSteal.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> HEART_FRAGMENT = ITEMS.register("heart_fragment",
            () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> HEART_CORE = ITEMS.register("heart_core",
            () -> new HeartCoreItem(new Item.Properties().food(HeartCoreItem.HeartCore)));
    public static final RegistrySupplier<Item> HEART_CRYSTAL = ITEMS.register("heart_crystal",
            () -> new HeartCrystalItem(new Item.Properties().stacksTo(1).fireResistant().food(HeartCrystalItem.HeartCrystal)));
    public static final RegistrySupplier<Item> REVIVE_CRYSTAL = ITEMS.register("revive_crystal",
            () -> new ReviveCrystalItem(new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> REVIVE_HEAD_ITEM = ITEMS.register("revive_head",
            () -> new ReviveHeadItem(ModBlocks.REVIVE_HEAD.get(), ModBlocks.REVIVE_WALL_HEAD.get(), new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1).fireResistant()));

    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModItems for " + LifeSteal.MOD_ID);
        ITEMS.register();
    }
}
