package io.github.bioplethora.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BioAdvancementProvider extends AdvancementProvider {

    // TODO: 01/02/2022 W.I.P. Datagenerator
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Bioplethora.MOD_ID, "textures/block/frostbite_metal_core_block.png");
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator datagen;

    public BioAdvancementProvider(DataGenerator generatorIn, ExistingFileHelper exFileHelper) {
        super(generatorIn, exFileHelper);
        this.datagen = generatorIn;
    }

    /**
     * List every advancements here.
     */
    public void register(Consumer<Advancement> t) {
        // STARTUP
        Advancement bioStartup = registerAdvancement("bioplethora_startup", FrameType.TASK, BPItems.BIOPEDIA.get()).addCriterion("startup",
        		PlayerTrigger.TriggerInstance.located(LocationPredicate.inDimension(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("overworld"))))).save(t, id("bioplethora_startup"));

        // ENTITY KILL
        Advancement frostbite_golemKill = entityKillAdvancement(BPEntities.FROSTBITE_GOLEM, BPItems.FROSTBITE_GOLEM_SPAWN_EGG, FrameType.GOAL, bioStartup, t);
        Advancement altyrusKill = entityKillAdvancement(BPEntities.ALTYRUS, BPItems.ALTYRUS_SPAWN_EGG, FrameType.CHALLENGE, frostbite_golemKill, t);

        Advancement alphemKill = entityKillAdvancement(BPEntities.ALPHEM, BPItems.ALPHEM_SPAWN_EGG, FrameType.TASK, bioStartup, t);
        Advancement alphemKingKill = entityKillAdvancement(BPEntities.ALPHEM_KING, BPItems.ALPHEM_KING_SPAWN_EGG, FrameType.CHALLENGE, alphemKill, t);

        // ENTITY TAME
        Advancement peaguinTame = entityTameAdvancement(BPEntities.PEAGUIN, BPItems.PEAGUIN_SPAWN_EGG, FrameType.TASK, bioStartup, t);
        Advancement trapjawTame = entityTameAdvancement(BPEntities.TRAPJAW, BPItems.TRAPJAW_SPAWN_EGG, FrameType.GOAL, bioStartup, t);

        // CUSTOM TRIGGERS
        Advancement grylynenSummon = customTriggerAdvancement("grylynen_summon", BPItems.GREEN_GRYLYNEN_CRYSTAL, FrameType.TASK, bioStartup, t);
    }

    //==================================================
    //            ADVANCEMENT FORMATS
    //==================================================
    public Advancement entityKillAdvancement(Supplier<? extends EntityType<?>> entity, RegistryObject<Item> iconItem, FrameType achievementLevel, Advancement parent, Consumer<Advancement> consumer) {
    	ResourceLocation registryName = ForgeRegistries.ENTITY_TYPES.getKey(entity.get());

        return registerAdvancement( registryName.getPath() + "_kill", achievementLevel, iconItem.get())
                .parent(parent).addCriterion( registryName.getPath(), KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity()
                        .of(entity.get()))).save(consumer, id(registryName.getPath() + "_kill"));
    }

    public Advancement entityTameAdvancement(Supplier<? extends EntityType<?>> entity, RegistryObject<Item> iconItem, FrameType achievementLevel, Advancement parent, Consumer<Advancement> consumer) {
        ResourceLocation registryName = ForgeRegistries.ENTITY_TYPES.getKey(entity.get());

        return registerAdvancement( registryName.getPath() + "_tame", achievementLevel, iconItem.get())
                .parent(parent).addCriterion(registryName.getPath(), TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity()
                        .of(entity.get()).build())).save(consumer, id(registryName.getPath() + "_tame"));
    }

    public Advancement customTriggerAdvancement(String name, RegistryObject<Item> iconItem, FrameType achievementLevel, Advancement parent, Consumer<Advancement> consumer) {
        return registerAdvancement(name, achievementLevel, iconItem.get())
                .parent(parent).addCriterion(name, new ImpossibleTrigger.TriggerInstance()).save(consumer, id(name));
    }

    //================================================================
    //             OTHER ADVANCEMENT GENERATOR HELPERS
    //================================================================
    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    private static String id(String save) {
        return Bioplethora.MOD_ID + ":" + save;
    }

    @Override
    public void run(CachedOutput cache) {
        Path path = this.datagen.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if (!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            } else {
                Path path1 = getPath(path, advancement);
                try {
                    DataProvider.saveStable(cache, advancement.deconstruct().serializeToJson(), path1);
                } catch (IOException e) {
                    Bioplethora.LOGGER.error("Couldn't save advancement {}", path1, e);
                }
            }
        };
        this.register(consumer);
    }

    private Advancement.Builder registerAdvancement(String name, FrameType type, Item... items) {
        Validate.isTrue(items.length > 0);
        return Advancement.Builder.advancement().display(items[0],
                Component.translatable("advancements.bioplethora." + name + ".title"),
                Component.translatable("advancements.bioplethora." + name + ".desc"),
                BACKGROUND_TEXTURE, type, true, true, false);
    }
}
