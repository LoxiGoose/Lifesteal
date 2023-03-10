package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.registry.RegistrySupplier;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LootProvider extends LootTableProvider {
    public LootProvider(PackOutput arg, Set<ResourceLocation> set, List<SubProviderEntry> list) {
        super(arg, set, list);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationContext) {
        for (Map.Entry<ResourceLocation, LootTable> entry : map.entrySet())
            LootTables.validate(validationContext, entry.getKey(), entry.getValue());
    }

    /**
     * This nested class extends the `BlockLootSubProvider` class from the Minecraft game engine and is
     * responsible for generating loot tables for blocks in the game.
     */
    public static class ModBlockLoot extends BlockLootSubProvider {
        public ModBlockLoot() {
            super(Stream.of(Blocks.DRAGON_EGG, Blocks.BEACON, Blocks.CONDUIT, Blocks.SKELETON_SKULL, Blocks.WITHER_SKELETON_SKULL, Blocks.PLAYER_HEAD, Blocks.ZOMBIE_HEAD, Blocks.CREEPER_HEAD, Blocks.DRAGON_HEAD, Blocks.PIGLIN_HEAD, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX).map(ItemLike::asItem).collect(Collectors.toSet()), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            this.add(ModBlocks.HEART_ORE.get(), (block) -> createOreDrop(block, ModItems.HEART_FRAGMENT.get()));
            this.add(ModBlocks.DEEPSLATE_HEART_ORE.get(), (block) -> createOreDrop(block, ModItems.HEART_FRAGMENT.get()));
            this.add(ModBlocks.NETHERRACK_HEART_ORE.get(), (block) -> createOreDrop(block, ModItems.HEART_FRAGMENT.get()));
            this.add(ModBlocks.REVIVE_HEAD.get(), (block) -> LootTable.lootTable().withPool((LootPool.lootPool().add(LootItem.lootTableItem(ModItems.REVIVE_HEAD_ITEM.get()).apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("SkullOwner", "SkullOwner").copy("note_block_sound", "BlockEntityTag.note_block_sound"))))));
            dropSelf(ModBlocks.HEART_CORE_BLOCK.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            ArrayList<Block> blocks = new ArrayList<>();
            for (RegistrySupplier<Block> entry : ModBlocks.BLOCKS.getEntries()) {
                blocks.add(entry.get());
            }
            return blocks;
        }
    }

    public static class ModChestLoot implements LootTableSubProvider {

        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
            biConsumer.accept(LifeSteal.BARREL_1, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(4.0F))
                            .add(LootItem.lootTableItem(Items.STRING).setWeight(10)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 24.0F))))
                            .add(LootItem.lootTableItem(Items.PAPER).setWeight(10)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                            .add(LootItem.lootTableItem(Items.BOOK).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))));
            biConsumer.accept(LifeSteal.MINERS_HOME_TABLE, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1F, 4F))
                            .add(LootItem.lootTableItem(ModItems.HEART_FRAGMENT.get()).setWeight(55)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 7.0F))))
                            .add(LootItem.lootTableItem(ModBlocks.HEART_ORE.get().asItem()).setWeight(35)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                            .add(LootItem.lootTableItem(ModBlocks.DEEPSLATE_HEART_ORE.get().asItem()).setWeight(25)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                            .add(LootItem.lootTableItem(ModBlocks.NETHERRACK_HEART_ORE.get()).setWeight(35)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 14.0F))))
                            .add(LootItem.lootTableItem(ModItems.HEART_CRYSTAL.get()).setWeight(2)
                                    .when(LootItemRandomChanceCondition.randomChance(0.01F)))
                            .add(LootItem.lootTableItem(ModBlocks.HEART_CORE_BLOCK.get()).setWeight(25)))
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1F, 4F))
                            .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(50)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                            .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                            .add(LootItem.lootTableItem(Items.COAL).setWeight(35)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(16.0F, 32.0F))))
                            .add(LootItem.lootTableItem(Items.EMERALD_ORE).setWeight(25)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                            .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(35)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F)))))
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0F, 1F))
                            .add(LootItem.lootTableItem(Items.STONE_PICKAXE).setWeight(75))
                            .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(20)
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(1.0F, 10.0F))
                                            .allowTreasure()
                                            .when(LootItemRandomChanceCondition.randomChance(0.10F))))));
            biConsumer.accept(LifeSteal.MINERS_RUINED_SHACK_TABLE, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(2.0F, 3.0F))
                            .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(50)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                            .add(LootItem.lootTableItem(Items.COAL).setWeight(35)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(8.0F, 12.0F))))
                            .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(70)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(16.0F, 32.0F))))
                            .add(LootItem.lootTableItem(Items.EMERALD).setWeight(25)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                            .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(35)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))));
            biConsumer.accept(LifeSteal.RICH_CART_TABLE, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 7.0F)).setBonusRolls(ConstantValue.exactly(5))
                            .add(LootItem.lootTableItem(Items.IRON_ORE).setWeight(5000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))))
                            .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(5000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 18.0F))))
                            .add(LootItem.lootTableItem(Items.GOLD_ORE).setWeight(5000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                            .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(5000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 18.0F))))
                            .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(4000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                            .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(4000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
                            .add(LootItem.lootTableItem(Items.EMERALD).setWeight(4000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 11.0F))))
                            .add(LootItem.lootTableItem(ModItems.HEART_FRAGMENT.get()).setWeight(4000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 24.0F))))
                            .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(2500)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 9.0F)))
                                    .when(LootItemRandomChanceCondition.randomChance(0.25F)))
                            .add(LootItem.lootTableItem(Items.EMERALD_ORE).setWeight(2500)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                            .add(LootItem.lootTableItem(ModBlocks.HEART_ORE.get().asItem()).setWeight(2500)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 14.0F))))
                            .add(LootItem.lootTableItem(Items.DIAMOND_ORE).setWeight(1000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                    .when(LootItemRandomChanceCondition.randomChance(0.10F)))
                            .add(LootItem.lootTableItem(Items.EMERALD).setWeight(1000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 27.0F)))
                                    .when(LootItemRandomChanceCondition.randomChance(0.10F)))
                            .add(LootItem.lootTableItem(ModItems.HEART_CORE.get()).setWeight(1000)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(5.0F, 7.0F)))
                                    .when(LootItemRandomChanceCondition.randomChance(0.10F)))
                            .add(LootItem.lootTableItem(Items.DIAMOND_BLOCK).setWeight(100)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(6.0F, 18.0F)))
                                    .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                            .add(LootItem.lootTableItem(ModBlocks.HEART_CORE_BLOCK.get()).setWeight(100)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(6.0F, 10.0F)))
                                    .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                            .add(LootItem.lootTableItem(ModItems.HEART_CRYSTAL.get()).setWeight(100)
                                    .when(LootItemRandomChanceCondition.randomChance(0.05F)))
                            .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP).setWeight(1)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 12.0F))))
                            .when(LootItemRandomChanceCondition.randomChance(0.01F))));
            biConsumer.accept(LifeSteal.RUINED_LIBRARY_TABLE, LootTable.lootTable()
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(2.0F))
                            .add(LootItem.lootTableItem(Items.PAPER).setWeight(10)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 18.0F))))
                            .add(LootItem.lootTableItem(Items.BOOK).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F)))
                                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(1.0F, 24.0F))
                                            .allowTreasure()
                                            .when(LootItemRandomChanceCondition.randomChance(0.10F)))))
                    .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3.0F, 4.0F))
                            .add(LootItem.lootTableItem(ModItems.HEART_CORE.get()).setWeight(35))
                            .add(LootItem.lootTableItem(ModItems.HEART_FRAGMENT.get()).setWeight(60)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 15.0F))))
                            .add(LootItem.lootTableItem(ModBlocks.HEART_CORE_BLOCK.get().asItem()).setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                            .add(LootItem.lootTableItem(ModItems.HEART_CRYSTAL.get()).setWeight(1)
                                    .when(LootItemRandomChanceCondition.randomChance(0.50F)))));

        }
    }
}
