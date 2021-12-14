package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.creatures.*;
import io.github.bioplethora.entity.projectile.BellophiteArrowEntity;
import io.github.bioplethora.entity.projectile.BellophiteClusterEntity;
import io.github.bioplethora.entity.projectile.UltimateBellophiteClusterEntity;
import io.github.bioplethora.entity.projectile.WindblazeEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BioplethoraEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Bioplethora.MOD_ID);

    //Avifauna
    public static final RegistryObject<EntityType<CrephoxlEntity>> CREPHOXL = ENTITIES.register("crephoxl", () -> EntityType.Builder.of(CrephoxlEntity::new, EntityClassification.MONSTER).sized(3.5f, 5f).build(new ResourceLocation(Bioplethora.MOD_ID, "crephoxl").toString()));
    public static final RegistryObject<EntityType<PeaguinEntity>> PEAGUIN = ENTITIES.register("peaguin", () -> EntityType.Builder.of(PeaguinEntity::new, EntityClassification.WATER_CREATURE).sized(1.2f, 1.4f).build(new ResourceLocation(Bioplethora.MOD_ID, "peaguin").toString()));

    //Fairy
    public static final RegistryObject<EntityType<AlphemEntity>> ALPHEM = ENTITIES.register("alphem", () -> EntityType.Builder.of(AlphemEntity::new, EntityClassification.MONSTER).sized(1.2f, 1.4f).build(new ResourceLocation(Bioplethora.MOD_ID, "alphem").toString()));
    public static final RegistryObject<EntityType<BellophgolemEntity>> BELLOPHGOLEM = ENTITIES.register("bellophgolem", () -> EntityType.Builder.of(BellophgolemEntity::new, EntityClassification.MONSTER).sized(3.5f, 4.75f).build(new ResourceLocation(Bioplethora.MOD_ID, "bellophgolem").toString()));
    public static final RegistryObject<EntityType<AltyrusEntity>> ALTYRUS = ENTITIES.register("altyrus", () -> EntityType.Builder.of(AltyrusEntity::new, EntityClassification.MONSTER).sized(3.5f, 4.75f).build(new ResourceLocation(Bioplethora.MOD_ID, "altyrus").toString()));

    //Terraneutral
    public static final RegistryObject<EntityType<NandbriEntity>> NANDBRI = ENTITIES.register("nandbri", () -> EntityType.Builder.of(NandbriEntity::new, EntityClassification.MONSTER).sized(2.6f, 1.15f).build(new ResourceLocation(Bioplethora.MOD_ID, "nandbri").toString()));

    //maritime
    public static final RegistryObject<EntityType<CuttlefishEntity>> CUTTLEFISH = ENTITIES.register("cuttlefish", () -> EntityType.Builder.of(CuttlefishEntity::new, EntityClassification.WATER_CREATURE).sized(0.55f, 1.15f).build(new ResourceLocation(Bioplethora.MOD_ID, "cuttlefish").toString()));

    //Elite Undead
    public static final RegistryObject<EntityType<GaugalemEntity>> GAUGALEM = ENTITIES.register("gaugalem", () -> EntityType.Builder.of(GaugalemEntity::new, EntityClassification.MONSTER).sized(1.2f, 4.0f).build(new ResourceLocation(Bioplethora.MOD_ID, "gaugalem").toString()));

    //others ---
    //Projectiles
    public static final RegistryObject<EntityType<BellophiteClusterEntity>> BELLOPHITE_CLUSTER = ENTITIES.register("bellophite_cluster", () -> EntityType.Builder.<BellophiteClusterEntity>of(BellophiteClusterEntity::new, EntityClassification.MISC).sized(2.0F, 2.0F).clientTrackingRange(4).updateInterval(20)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "bellophite_cluster").toString()));
    public static final RegistryObject<EntityType<BellophiteArrowEntity>> BELLOPHITE_ARROW = ENTITIES.register("bellophite_arrow", () -> EntityType.Builder.<BellophiteArrowEntity>of(BellophiteArrowEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles").toString()));
    public static final RegistryObject<EntityType<WindblazeEntity>> WINDBLAZE = ENTITIES.register("windblaze", () -> EntityType.Builder.<WindblazeEntity>of(WindblazeEntity::new, EntityClassification.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "windblaze").toString()));
    public static final RegistryObject<EntityType<UltimateBellophiteClusterEntity>> ULTIMATE_BELLOPHITE_CLUSTER = ENTITIES.register("ultimate_bellophite_cluster", () -> EntityType.Builder.<UltimateBellophiteClusterEntity>of(UltimateBellophiteClusterEntity::new, EntityClassification.MISC).sized(2.0F, 2.0F).clientTrackingRange(4).updateInterval(20)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "ultimate_bellophite_cluster").toString()));
}
