package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.BPBoatEntity;
import io.github.bioplethora.entity.creatures.AlphemEntity;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.entity.creatures.CavernFleignarEntity;
import io.github.bioplethora.entity.creatures.CrephoxlEntity;
import io.github.bioplethora.entity.creatures.CuttlefishEntity;
import io.github.bioplethora.entity.creatures.DwarfMossadileEntity;
import io.github.bioplethora.entity.creatures.EurydnEntity;
import io.github.bioplethora.entity.creatures.FrostbiteGolemEntity;
import io.github.bioplethora.entity.creatures.GaugalemEntity;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import io.github.bioplethora.entity.creatures.MyliothanEntity;
import io.github.bioplethora.entity.creatures.MyuthineEntity;
import io.github.bioplethora.entity.creatures.NandbriEntity;
import io.github.bioplethora.entity.creatures.OnofishEntity;
import io.github.bioplethora.entity.creatures.PeaguinEntity;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import io.github.bioplethora.entity.creatures.TelemreyeEntity;
import io.github.bioplethora.entity.creatures.TerraithEntity;
import io.github.bioplethora.entity.creatures.TrapjawEntity;
import io.github.bioplethora.entity.creatures.TriggerfishEntity;
import io.github.bioplethora.entity.creatures.VoidjawEntity;
import io.github.bioplethora.entity.others.AlphanumShardEntity;
import io.github.bioplethora.entity.others.AltyrusSummoningEntity;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.entity.others.FrostbiteMetalShieldWaveEntity;
import io.github.bioplethora.entity.others.GrylynenCoreBombEntity;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import io.github.bioplethora.entity.projectile.AbyssalScalesEntity;
import io.github.bioplethora.entity.projectile.AlphanumObliteratorSpearEntity;
import io.github.bioplethora.entity.projectile.CryeanumGaidiusEntity;
import io.github.bioplethora.entity.projectile.CryoblazeEntity;
import io.github.bioplethora.entity.projectile.EchoGaidiusEntity;
import io.github.bioplethora.entity.projectile.FrostbiteMetalArrowEntity;
import io.github.bioplethora.entity.projectile.FrostbiteMetalClusterEntity;
import io.github.bioplethora.entity.projectile.GaidiusBaseEntity;
import io.github.bioplethora.entity.projectile.MagmaBombEntity;
import io.github.bioplethora.entity.projectile.NetheriteObsidianGaidiusEntity;
import io.github.bioplethora.entity.projectile.UltimateFrostbiteMetalClusterEntity;
import io.github.bioplethora.entity.projectile.VermilionBladeProjectileEntity;
import io.github.bioplethora.entity.projectile.WindArrowEntity;
import io.github.bioplethora.entity.projectile.WindblazeEntity;
import io.github.bioplethora.entity.projectile.WindyEssenceEntity;
import io.github.bioplethora.enums.BPGrylynenTier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Bioplethora.MOD_ID);

    //Ecoharmless
    public static final RegistryObject<EntityType<CuttlefishEntity>> CUTTLEFISH = ENTITIES.register("cuttlefish", () -> EntityType.Builder.of(CuttlefishEntity::new, MobCategory.WATER_CREATURE).sized(0.75f, 0.6f).build(new ResourceLocation(Bioplethora.MOD_ID, "cuttlefish").toString()));
    public static final RegistryObject<EntityType<OnofishEntity>> ONOFISH = ENTITIES.register("onofish", () -> EntityType.Builder.of(OnofishEntity::new, MobCategory.CREATURE).sized(0.75f, 0.85f).build(new ResourceLocation(Bioplethora.MOD_ID, "onofish").toString()));
    public static final RegistryObject<EntityType<TriggerfishEntity>> TRIGGERFISH = ENTITIES.register("triggerfish", () -> EntityType.Builder.of(TriggerfishEntity::new, MobCategory.CREATURE).sized(0.75f, 0.85f).build(new ResourceLocation(Bioplethora.MOD_ID, "triggerfish").toString()));

    public static final RegistryObject<EntityType<EurydnEntity>> SOUL_EURYDN = ENTITIES.register("soul_eurydn", () -> EntityType.Builder.of((EntityType.EntityFactory<EurydnEntity>) (type, world) ->
            new EurydnEntity(type, world, EurydnEntity.Variant.SOUL), MobCategory.CREATURE).sized(1.8f, 1.1f).build(new ResourceLocation(Bioplethora.MOD_ID, "oul_eurydn").toString()));
    public static final RegistryObject<EntityType<EurydnEntity>> FIERY_EURYDN = ENTITIES.register("fiery_eurydn", () -> EntityType.Builder.of((EntityType.EntityFactory<EurydnEntity>) (type, world) ->
            new EurydnEntity(type, world, EurydnEntity.Variant.FIERY), MobCategory.CREATURE).sized(1.8f, 1.1f).build(new ResourceLocation(Bioplethora.MOD_ID, "fiery_eurydn").toString()));

    //Plethoneutral
    public static final RegistryObject<EntityType<PeaguinEntity>> PEAGUIN = ENTITIES.register("peaguin", () -> EntityType.Builder.of(PeaguinEntity::new, MobCategory.WATER_CREATURE).sized(1.2f, 1.4f).build(new ResourceLocation(Bioplethora.MOD_ID, "peaguin").toString()));
    public static final RegistryObject<EntityType<NandbriEntity>> NANDBRI = ENTITIES.register("nandbri", () -> EntityType.Builder.of(NandbriEntity::new, MobCategory.MONSTER).sized(2.6f, 1.15f).build(new ResourceLocation(Bioplethora.MOD_ID, "nandbri").toString()));
    public static final RegistryObject<EntityType<CavernFleignarEntity>> CAVERN_FLEIGNAR = ENTITIES.register("cavern_fleignar", () -> EntityType.Builder.of(CavernFleignarEntity::new, MobCategory.MONSTER).sized(0.8f, 4.5f).build(new ResourceLocation(Bioplethora.MOD_ID, "cavern_fleignar").toString()));

    //Dangerum
    public static final RegistryObject<EntityType<AlphemEntity>> ALPHEM = ENTITIES.register("alphem", () -> EntityType.Builder.of(AlphemEntity::new, MobCategory.CREATURE).sized(1.2f, 1.4f).build(new ResourceLocation(Bioplethora.MOD_ID, "alphem").toString()));
    public static final RegistryObject<EntityType<GaugalemEntity>> GAUGALEM = ENTITIES.register("gaugalem", () -> EntityType.Builder.of(GaugalemEntity::new, MobCategory.MONSTER).sized(1.2f, 4.0f).build(new ResourceLocation(Bioplethora.MOD_ID, "gaugalem").toString()));
    public static final RegistryObject<EntityType<DwarfMossadileEntity>> DWARF_MOSSADILE = ENTITIES.register("dwarf_mossadile", () -> EntityType.Builder.of(DwarfMossadileEntity::new, MobCategory.MONSTER).sized(1.4f, 0.5f).build(new ResourceLocation(Bioplethora.MOD_ID, "dwarf_mossadile").toString()));
    public static final RegistryObject<EntityType<TrapjawEntity>> TRAPJAW = ENTITIES.register("trapjaw", () -> EntityType.Builder.of(TrapjawEntity::new, MobCategory.MONSTER).sized(2.2f, 1.5f).build(new ResourceLocation(Bioplethora.MOD_ID, "trapjaw").toString()));
    public static final RegistryObject<EntityType<TerraithEntity>> TERRAITH = ENTITIES.register("terraith", () -> EntityType.Builder.of(TerraithEntity::new, MobCategory.MONSTER).sized(1.5F, 1.8F).build(new ResourceLocation(Bioplethora.MOD_ID, "terraith").toString()));
    public static final RegistryObject<EntityType<MyuthineEntity>> MYUTHINE = ENTITIES.register("myuthine", () -> EntityType.Builder.of(MyuthineEntity::new, MobCategory.MONSTER).sized(1.0F, 3.2F).build(new ResourceLocation(Bioplethora.MOD_ID, "myuthine").toString()));

    public static final RegistryObject<EntityType<GrylynenEntity>> WOODEN_GRYLYNEN = ENTITIES.register("wooden_grylynen", () -> EntityType.Builder.of((EntityType.EntityFactory<GrylynenEntity>) (type, world) ->
                    new GrylynenEntity(type, world, BPGrylynenTier.WOODEN), MobCategory.MONSTER).sized(1f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "wooden_grylynen").toString()));
    public static final RegistryObject<EntityType<GrylynenEntity>> STONE_GRYLYNEN = ENTITIES.register("stone_grylynen", () -> EntityType.Builder.of((EntityType.EntityFactory<GrylynenEntity>) (type, world) ->
                    new GrylynenEntity(type, world, BPGrylynenTier.STONE), MobCategory.MONSTER).sized(1f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "stone_grylynen").toString()));
    public static final RegistryObject<EntityType<GrylynenEntity>> GOLDEN_GRYLYNEN = ENTITIES.register("golden_grylynen", () -> EntityType.Builder.of((EntityType.EntityFactory<GrylynenEntity>) (type, world) ->
            new GrylynenEntity(type, world, BPGrylynenTier.GOLDEN), MobCategory.MONSTER).sized(1f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "golden_grylynen").toString()));
    public static final RegistryObject<EntityType<GrylynenEntity>> IRON_GRYLYNEN = ENTITIES.register("iron_grylynen", () -> EntityType.Builder.of((EntityType.EntityFactory<GrylynenEntity>) (type, world) ->
            new GrylynenEntity(type, world, BPGrylynenTier.IRON), MobCategory.MONSTER).sized(1f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "iron_grylynen").toString()));
    public static final RegistryObject<EntityType<GrylynenEntity>> DIAMOND_GRYLYNEN = ENTITIES.register("diamond_grylynen", () -> EntityType.Builder.of((EntityType.EntityFactory<GrylynenEntity>) (type, world) ->
            new GrylynenEntity(type, world, BPGrylynenTier.DIAMOND), MobCategory.MONSTER).sized(1f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "diamond_grylynen").toString()));
    public static final RegistryObject<EntityType<GrylynenEntity>> NETHERITE_GRYLYNEN = ENTITIES.register("netherite_grylynen", () -> EntityType.Builder.of((EntityType.EntityFactory<GrylynenEntity>) (type, world) ->
            new GrylynenEntity(type, world, BPGrylynenTier.NETHERITE), MobCategory.MONSTER).sized(1f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "netherite_grylynen").toString()));

    //Hellsent
    public static final RegistryObject<EntityType<CrephoxlEntity>> CREPHOXL = ENTITIES.register("crephoxl", () -> EntityType.Builder.of(CrephoxlEntity::new, MobCategory.MONSTER).sized(3.5f, 5f).build(new ResourceLocation(Bioplethora.MOD_ID, "crephoxl").toString()));
    public static final RegistryObject<EntityType<FrostbiteGolemEntity>> FROSTBITE_GOLEM = ENTITIES.register("frostbite_golem", () -> EntityType.Builder.of(FrostbiteGolemEntity::new, MobCategory.MONSTER).sized(2.8f, 4.2f).build(new ResourceLocation(Bioplethora.MOD_ID, "frostbite_golem").toString()));
    public static final RegistryObject<EntityType<ShachathEntity>> SHACHATH = ENTITIES.register("shachath", () -> EntityType.Builder.of(ShachathEntity::new, MobCategory.MONSTER).sized(0.6f, 1.8f).build(new ResourceLocation(Bioplethora.MOD_ID, "shachath").toString()));
    public static final RegistryObject<EntityType<TelemreyeEntity>> TELEMREYE = ENTITIES.register("telemreye", () -> EntityType.Builder.of(TelemreyeEntity::new, MobCategory.MONSTER).sized(2.6f, 1.15f).build(new ResourceLocation(Bioplethora.MOD_ID, "telemreye").toString()));
    public static final RegistryObject<EntityType<VoidjawEntity>> VOIDJAW = ENTITIES.register("voidjaw", () -> EntityType.Builder.of(VoidjawEntity::new, MobCategory.MONSTER).sized(2.2f, 1.5f).build(new ResourceLocation(Bioplethora.MOD_ID, "voidjaw").toString()));

    //Elderia
    public static final RegistryObject<EntityType<AltyrusEntity>> ALTYRUS = ENTITIES.register("altyrus", () -> EntityType.Builder.of(AltyrusEntity::new, MobCategory.MONSTER).sized(3.5f, 4.75f).build(new ResourceLocation(Bioplethora.MOD_ID, "altyrus").toString()));
    public static final RegistryObject<EntityType<MyliothanEntity>> MYLIOTHAN = ENTITIES.register("myliothan", () -> EntityType.Builder.of(MyliothanEntity::new, MobCategory.MONSTER).sized(14.5f, 5.5f).build(new ResourceLocation(Bioplethora.MOD_ID, "myliothan").toString()));
    public static final RegistryObject<EntityType<AlphemKingEntity>> ALPHEM_KING = ENTITIES.register("alphem_king", () -> EntityType.Builder.of(AlphemKingEntity::new, MobCategory.MONSTER).sized(3.5f, 4.75f).build(new ResourceLocation(Bioplethora.MOD_ID, "alphem_king").toString()));

    //Projectiles
    public static final RegistryObject<EntityType<FrostbiteMetalClusterEntity>> BELLOPHITE_CLUSTER = ENTITIES.register("frostbite_metal_cluster", () -> EntityType.Builder.<FrostbiteMetalClusterEntity>of(FrostbiteMetalClusterEntity::new, MobCategory.MISC).sized(2.0F, 2.0F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "frostbite_metal_cluster").toString()));
    public static final RegistryObject<EntityType<FrostbiteMetalArrowEntity>> BELLOPHITE_ARROW = ENTITIES.register("frostbite_metal_arrow", () -> EntityType.Builder.<FrostbiteMetalArrowEntity>of(FrostbiteMetalArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles").toString()));
    public static final RegistryObject<EntityType<WindblazeEntity>> WINDBLAZE = ENTITIES.register("windblaze", () -> EntityType.Builder.<WindblazeEntity>of(WindblazeEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "windblaze").toString()));
    public static final RegistryObject<EntityType<UltimateFrostbiteMetalClusterEntity>> ULTIMATE_BELLOPHITE_CLUSTER = ENTITIES.register("ultimate_frostbite_metal_cluster", () -> EntityType.Builder.<UltimateFrostbiteMetalClusterEntity>of(UltimateFrostbiteMetalClusterEntity::new, MobCategory.MISC).sized(2.0F, 2.0F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "ultimate_frostbite_metal_cluster").toString()));
    public static final RegistryObject<EntityType<MagmaBombEntity>> MAGMA_BOMB = ENTITIES.register("magma_bomb", () -> EntityType.Builder.<MagmaBombEntity>of(MagmaBombEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "magma_bomb").toString()));
    public static final RegistryObject<EntityType<VermilionBladeProjectileEntity>> VERMILION_BLADE_PROJECTILE = ENTITIES.register("vermilion_blade_projectile", () -> EntityType.Builder.<VermilionBladeProjectileEntity>of(VermilionBladeProjectileEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "vermilion_blade_projectile").toString()));
    public static final RegistryObject<EntityType<WindArrowEntity>> WIND_ARROW = ENTITIES.register("wind_arrow", () -> EntityType.Builder.<WindArrowEntity>of(WindArrowEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "textures/projectiles").toString()));
    public static final RegistryObject<EntityType<CryoblazeEntity>> CRYOBLAZE = ENTITIES.register("cryoblaze", () -> EntityType.Builder.<CryoblazeEntity>of(CryoblazeEntity::new, MobCategory.MISC).sized(2.0F, 2.0F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "cryoblaze").toString()));
    public static final RegistryObject<EntityType<AlphanumObliteratorSpearEntity>> ALPHANUM_OBLITERATOR_SPEAR = ENTITIES.register("alphanum_obliterator_spear", () -> EntityType.Builder.<AlphanumObliteratorSpearEntity>of(AlphanumObliteratorSpearEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "alphanum_obliterator_spear").toString()));
    public static final RegistryObject<EntityType<WindyEssenceEntity>> WINDY_ESSENCE = ENTITIES.register("windy_essence", () -> EntityType.Builder.of(WindyEssenceEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "windy_essence").toString()));
    public static final RegistryObject<EntityType<AbyssalScalesEntity>> ABYSSAL_SCALES = ENTITIES.register("abyssal_scales", () -> EntityType.Builder.<AbyssalScalesEntity>of(AbyssalScalesEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "abyssal_scales").toString()));

    public static final RegistryObject<EntityType<CryeanumGaidiusEntity>> CRYEANUM_GAIDIUS = ENTITIES.register("cryeanum_gaidius", () -> EntityType.Builder.<CryeanumGaidiusEntity>of(CryeanumGaidiusEntity::new, MobCategory.MISC)
            .sized(1.5F, 1.5F).build(new ResourceLocation(Bioplethora.MOD_ID, "cryeanum_gaidius").toString()));
        public static final RegistryObject<EntityType<NetheriteObsidianGaidiusEntity>> NETHERITE_OBSIDIAN_GAIDIUS = ENTITIES.register("netherite_obsidian_gaidius", () -> EntityType.Builder.<NetheriteObsidianGaidiusEntity>of(NetheriteObsidianGaidiusEntity::new, MobCategory.MISC)
            .sized(1.5F, 1.5F).build(new ResourceLocation(Bioplethora.MOD_ID, "netherite_obsidian_gaidius").toString()));
    public static final RegistryObject<EntityType<EchoGaidiusEntity>> ECHO_GAIDIUS = ENTITIES.register("echo_gaidius", () -> EntityType.Builder.<EchoGaidiusEntity>of(EchoGaidiusEntity::new, MobCategory.MISC)
            .sized(1.5F, 1.5F).build(new ResourceLocation(Bioplethora.MOD_ID, "echo_gaidius").toString()));

    //Others
    public static final RegistryObject<EntityType<BPBoatEntity>> CAERULWOOD_BOAT = createBoat("caerulwood");
    public static final RegistryObject<EntityType<BPBoatEntity>> ENIVILE_BOAT = createBoat("enivile");

    public static final RegistryObject<EntityType<PrimordialRingEntity>> PRIMORDIAL_RING = ENTITIES.register("primordial_ring", () -> EntityType.Builder.of(PrimordialRingEntity::new, MobCategory.MONSTER).sized(3.5f, 2f).build(new ResourceLocation(Bioplethora.MOD_ID, "primordial_ring").toString()));
    public static final RegistryObject<EntityType<AltyrusSummoningEntity>> ALTYRUS_SUMMONING = ENTITIES.register("altyrus_summoning", () -> EntityType.Builder.of(AltyrusSummoningEntity::new, MobCategory.MISC).sized(2.0F, 2.0F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "altyrus_summoning").toString()));
    public static final RegistryObject<EntityType<FrostbiteMetalShieldWaveEntity>> BELLOPHITE_SHIELD_WAVE = ENTITIES.register("frostbite_metal_shield_wave", () -> EntityType.Builder.of(FrostbiteMetalShieldWaveEntity::new, MobCategory.MISC).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "frostbite_metal_shield_wave").toString()));
    public static final RegistryObject<EntityType<GrylynenCoreBombEntity>> GRYLYNEN_CORE_BOMB = ENTITIES.register("grylynen_core_bomb", () -> EntityType.Builder.of(GrylynenCoreBombEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "grylynen_core_bomb").toString()));
    public static final RegistryObject<EntityType<AlphanumShardEntity>> ALPHANUM_SHARD = ENTITIES.register("alphanum_shard", () -> EntityType.Builder.of(AlphanumShardEntity::new, MobCategory.MISC).sized(0.8F, 1.5F).clientTrackingRange(4)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "alphanum_shard").toString()));

    public static final RegistryObject<EntityType<BPEffectEntity>> BP_EFFECT = ENTITIES.register("bp_effect", () -> EntityType.Builder.of(BPEffectEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(16)
            .build(new ResourceLocation(Bioplethora.MOD_ID, "bp_effect").toString()));

    //============================
    //       HELPERS
    //============================
    public static RegistryObject<EntityType<BPBoatEntity>> createBoat(String woodType) {
        return ENTITIES.register(woodType + "_boat", () -> EntityType.Builder.<BPBoatEntity>of(BPBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).build(new ResourceLocation(Bioplethora.MOD_ID, woodType + "_boat").toString()));
    }

    public static RegistryObject<EntityType<? extends GaidiusBaseEntity>> createGaidius(String name, EntityType.EntityFactory<? extends GaidiusBaseEntity> gaidius) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(gaidius, MobCategory.MISC).sized(2.0F, 2.0F).build(new ResourceLocation(Bioplethora.MOD_ID, name).toString()));
    }
}
