package net.goose.lifesteal.world.gen;

import net.fabricmc.fabric.api.biome.v1.*;
import net.goose.lifesteal.LifeSteal;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.BiConsumer;

public class ModBiomeModifier {
    public static final ResourceKey<PlacedFeature> HEART_ORE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(LifeSteal.MOD_ID, "heart_ore_placed"));
    public static final ResourceKey<PlacedFeature> NETHER_HEART_ORE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(LifeSteal.MOD_ID, "nether_heart_ore_placed"));
    public static final ResourceKey<PlacedFeature> DEEPSLATE_HEART_GEODE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(LifeSteal.MOD_ID, "deepslate_heart_geode_placed"));
    public static final ResourceKey<PlacedFeature> NETHER_HEART_GEODE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(LifeSteal.MOD_ID, "nether_heart_geode_placed"));
    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> HEART_MODIFIER() {
        return (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(
                    GenerationStep.Decoration.UNDERGROUND_ORES,
                    HEART_ORE_PLACED);
            biomeModificationContext.getGenerationSettings().addFeature(
                    GenerationStep.Decoration.UNDERGROUND_ORES,
                    DEEPSLATE_HEART_GEODE_PLACED);
        };

    }

    private static BiConsumer<BiomeSelectionContext, BiomeModificationContext> NETHER_MODIFIER() {
        return (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(
                    GenerationStep.Decoration.UNDERGROUND_ORES,
                    NETHER_HEART_ORE_PLACED);
            biomeModificationContext.getGenerationSettings().addFeature(
                    GenerationStep.Decoration.UNDERGROUND_ORES,
                    NETHER_HEART_GEODE_PLACED);
        };

    }

    public static void register() {
        LifeSteal.LOGGER.debug("Registering ModBiomeModifier for " + LifeSteal.MOD_ID);
        BiomeModifications.create(new ResourceLocation(LifeSteal.MOD_ID, "add_overworld_features"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInOverworld(), HEART_MODIFIER());
        BiomeModifications.create(new ResourceLocation(LifeSteal.MOD_ID, "add_nether_features"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.foundInTheNether(), NETHER_MODIFIER());
    }
}