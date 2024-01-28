package net.goose.lifesteal.datagen;

import net.goose.lifesteal.LifeSteal;
import net.goose.lifesteal.advancement.LSAdvancementTrigger;
import net.goose.lifesteal.common.block.ModBlocks;
import net.goose.lifesteal.common.item.ModItems;
import net.goose.lifesteal.util.ModResources;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementsProvider extends AdvancementProvider {
    /**
     * Constructs an advancement provider using the generators to write the
     * advancements to a file.
     *
     * @param output             the target directory of the data generator
     * @param registries         a future of a lookup for registries and their objects
     * @param existingFileHelper a helper used to find whether a file exists
     * @param subProviders       the generators used to create the advancements
     */
    public ModAdvancementsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper, List<AdvancementGenerator> subProviders) {
        super(output, registries, existingFileHelper, subProviders);
    }

    private static DisplayInfo simpleDisplay(ItemLike icon, String name, AdvancementType frameType) {
        return simpleDisplayWithBackground(icon, name, frameType, null);
    }

    private static DisplayInfo simpleDisplayWithBackground(ItemLike icon, String name, AdvancementType frameType, @Nullable ResourceLocation background) {
        return display(new ItemStack(icon), name, frameType, background, true, true, false);
    }

    private static DisplayInfo display(ItemStack icon, String name, AdvancementType frameType, ResourceLocation background, boolean showToast, boolean announceChat, boolean hidden) {
        String expandedName = "advancement." + LifeSteal.MOD_ID + ":" + name;
        return new DisplayInfo(icon, Component.translatable(expandedName), Component.translatable(expandedName + ".desc"), Optional.ofNullable(background), frameType, showToast, announceChat, hidden);
    }

    public static class AdvancementsGenerator implements AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder ROOT = Advancement.Builder.advancement()
                    .display(display(
                            ModItems.HEART_FRAGMENT.get().getDefaultInstance(),
                            "root",
                            AdvancementType.TASK,
                            new ResourceLocation("lifesteal:textures/block/heart_core_block.png"),
                            false,
                            false,
                            false))
                    .addCriterion("has_heart_fragment", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HEART_FRAGMENT.get()))
                    .save(saver, ModResources.modLocString("root"));
            Advancement.Builder.advancement()
                    .parent(ROOT)
                    .display(simpleDisplay(ModBlocks.REVIVE_HEAD.get(), "revived_from_dead", AdvancementType.CHALLENGE))
                    .addCriterion("revived", LSAdvancementTrigger.TriggerInstance.REVIVED())
                    .save(saver, ModResources.modLocString("revived_from_dead"));
            AdvancementHolder HEART_CORE = Advancement.Builder.advancement()
                    .parent(ROOT)
                    .display(simpleDisplay(ModItems.HEART_CORE.get(), "get_heart_core", AdvancementType.TASK))
                    .addCriterion("has_heart_core", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HEART_CORE.get()))
                    .save(saver, ModResources.modLocString("get_heart_core"));
            AdvancementHolder HEART_CRYSTAL = Advancement.Builder.advancement()
                    .parent(HEART_CORE)
                    .display(simpleDisplay(ModItems.HEART_CRYSTAL.get(), "get_heart_crystal", AdvancementType.TASK))
                    .addCriterion("has_heart_crystal", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HEART_CRYSTAL.get()))
                    .save(saver, ModResources.modLocString("get_heart_crystal"));
            AdvancementHolder GET_10_MAX_HEARTS = Advancement.Builder.advancement()
                    .parent(HEART_CRYSTAL)
                    .display(simpleDisplay(ModBlocks.HEART_CORE_BLOCK.get(), "get_10_max_hearts", AdvancementType.GOAL))
                    .addCriterion("has_10_max_hearts", LSAdvancementTrigger.TriggerInstance.GET_10_MAX_HEARTS())
                    .rewards(AdvancementRewards.Builder.experience(500))
                    .save(saver, ModResources.modLocString("get_10_max_hearts"));
            Advancement.Builder.advancement()
                    .parent(GET_10_MAX_HEARTS)
                    .display(simpleDisplay(Items.TOTEM_OF_UNDYING, "use_totem_while_20_max_hearts", AdvancementType.CHALLENGE))
                    .addCriterion("used_totem_while_20_max_hearts", LSAdvancementTrigger.TriggerInstance.USE_TOTEM_WHILE_20_MAX_HEARTS())
                    .rewards(AdvancementRewards.Builder.experience(500))
                    .save(saver, ModResources.modLocString("use_totem_while_20_max_hearts"));
            Advancement.Builder.advancement()
                    .parent(GET_10_MAX_HEARTS)
                    .display(simpleDisplay(Items.NETHERITE_HELMET, "get_10_max_hearts_with_netherite_armor", AdvancementType.CHALLENGE))
                    .addCriterion("has_full_netherite_armor", InventoryChangeTrigger.TriggerInstance.hasItems(Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS))
                    .addCriterion("has_10_max_hearts", LSAdvancementTrigger.TriggerInstance.GET_10_MAX_HEARTS())
                    .rewards(AdvancementRewards.Builder.experience(500))
                    .save(saver, ModResources.modLocString("get_10_max_hearts_with_netherite_armor"));
        }
    }
}
