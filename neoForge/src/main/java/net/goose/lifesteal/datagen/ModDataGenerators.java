package net.goose.lifesteal.datagen;

import net.goose.lifesteal.datagen.tags.ModBiomeTagsProvider;
import net.goose.lifesteal.datagen.tags.ModBlockTagsProvider;
import net.goose.lifesteal.datagen.tags.ModItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModDataGenerators {
    @SubscribeEvent
    public static void generateData(GatherDataEvent ev) {
        final CompletableFuture<HolderLookup.Provider> provider = ev.getLookupProvider();
        final DataGenerator gen = ev.getGenerator();
        final PackOutput packOutput = gen.getPackOutput();
        final ExistingFileHelper efh = ev.getExistingFileHelper();

        if (ev.includeServer()) {
            gen.addProvider(ev.includeServer(), new ModWorldGenProvider(packOutput, provider)); // ConfiguredFeatures&PlacedFeatures with BiomeModifiers
            gen.addProvider(ev.includeServer(), new ModRecipesProvider(packOutput, provider)); // Recipes
            gen.addProvider(ev.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(), // LootTables
                    List.of(new LootTableProvider.SubProviderEntry(ModLootProvider.ModBlockLoot::new, LootContextParamSets.BLOCK),
                            new LootTableProvider.SubProviderEntry(ModLootProvider.ModChestLoot::new, LootContextParamSets.CHEST))));
            gen.addProvider(ev.includeServer(), new ModBiomeTagsProvider(packOutput, provider, efh)); // BiomeTags
            TagsProvider<Block> blockTagsProvider = gen.addProvider(ev.includeServer(), new ModBlockTagsProvider(packOutput, provider, efh)); // BlockTags
            gen.addProvider(ev.includeServer(), new ModItemTagsProvider(packOutput, provider, blockTagsProvider.contentsGetter(), efh)); // ItemTags
            gen.addProvider(ev.includeServer(), new ModAdvancementsProvider(packOutput, provider, efh, // Advancements
                    List.of(new ModAdvancementsProvider.AdvancementsGenerator())));
        }
    }
}
