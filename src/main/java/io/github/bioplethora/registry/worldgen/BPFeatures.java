package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.featureconfigs.ExpandedLakeFeatureConfig;
import io.github.bioplethora.world.featureconfigs.FleignariteSplotchConfig;
import io.github.bioplethora.world.featureconfigs.NBTFeatureConfig;
import io.github.bioplethora.world.featureconfigs.PendentBlocksFeatureConfig;
import io.github.bioplethora.world.features.CaeriCavernFeature;
import io.github.bioplethora.world.features.CelestiaBudFeature;
import io.github.bioplethora.world.features.ChorusMychrodegiaFeature;
import io.github.bioplethora.world.features.CorsascileFeature;
import io.github.bioplethora.world.features.EndFrozenIslandFeature;
import io.github.bioplethora.world.features.EndIcicleFeature;
import io.github.bioplethora.world.features.EndLandsCavernFeature;
import io.github.bioplethora.world.features.EndLandsRockFeature;
import io.github.bioplethora.world.features.EndLandsSpikeFeature;
import io.github.bioplethora.world.features.EndLandsSpongeFeature;
import io.github.bioplethora.world.features.EnredeKelpFeature;
import io.github.bioplethora.world.features.ExpandedLakeFeature;
import io.github.bioplethora.world.features.FleignaritePatchFeature;
import io.github.bioplethora.world.features.LavaEdgeClusterFeature;
import io.github.bioplethora.world.features.NBTFeature;
import io.github.bioplethora.world.features.PendentBlocksFeature;
import io.github.bioplethora.world.features.PendentFleignariteFeature;
import io.github.bioplethora.world.features.SingularBlockFeature;
import io.github.bioplethora.world.features.WaterPlantFeature;
import io.github.bioplethora.world.features.treefeatures.CaerulwoodTreeFeature;
import io.github.bioplethora.world.features.treefeatures.EnivileTreeFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Bioplethora.MOD_ID);

    public static final RegistryObject<Feature<BlockClusterFeatureConfig>> LAVA_EDGE_CLUSTER = FEATURES.register("lava_edge_cluster", () -> new LavaEdgeClusterFeature(BlockClusterFeatureConfig.CODEC.stable()));
    public static final RegistryObject<Feature<PendentBlocksFeatureConfig>> PENDENT_BLOCKS = FEATURES.register("pendent_blocks", () -> new PendentBlocksFeature(PendentBlocksFeatureConfig.CODEC.stable()));
    public static final RegistryObject<Feature<FleignariteSplotchConfig>> FLEIGNARITE_PATCH = FEATURES.register("fleignarite_patch", () -> new FleignaritePatchFeature(FleignariteSplotchConfig.CODEC.stable()));
    public static final RegistryObject<Feature<PendentBlocksFeatureConfig>> PENDENT_FLEIGNARITE = FEATURES.register("pendent_fleignarite", () -> new PendentFleignariteFeature(PendentBlocksFeatureConfig.CODEC.stable()));
    public static final RegistryObject<Feature<ExpandedLakeFeatureConfig>> EXPANDED_LAKE = FEATURES.register("expanded_lake", () -> new ExpandedLakeFeature(ExpandedLakeFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BlockClusterFeatureConfig>> SINGULAR_BLOCK = FEATURES.register("singular_block", () -> new SingularBlockFeature(BlockClusterFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NBTFeatureConfig>> NBT_DRIVEN = FEATURES.register("nbt_driven", () -> new NBTFeature(NBTFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> END_ICICLE = FEATURES.register("end_icicle", () -> new EndIcicleFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> END_FROZEN_ISLAND = FEATURES.register("end_frozen_island", () -> new EndFrozenIslandFeature(NoneFeatureConfiguration.CODEC.stable()));
    public static final RegistryObject<Feature<BasaltDeltasFeature>> END_LAND_SPONGE = FEATURES.register("end_land_sponge", () -> new EndLandsSpongeFeature(BasaltDeltasFeature.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> END_LAND_ROCK = FEATURES.register("end_land_rock", () -> new EndLandsRockFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> END_LAND_SPIKE = FEATURES.register("end_land_spike", () -> new EndLandsSpikeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> END_LANDS_CAVERN = FEATURES.register("end_lands_cavern", () -> new EndLandsCavernFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CAERI_CAVERN = FEATURES.register("caeri_cavern", () -> new CaeriCavernFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<BlockStateFeatureConfig>> WATER_PLANT = FEATURES.register("water_plant", () -> new WaterPlantFeature(BlockStateFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ENREDE_KELP = FEATURES.register("enrede_kelp", () -> new EnredeKelpFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CELESTIA_BUD = FEATURES.register("celestia_bud", () -> new CelestiaBudFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<ProbabilityConfig>> CORSASCILE = FEATURES.register("corsascile", () -> new CorsascileFeature(ProbabilityConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CHORUS_MYCHRODEGIA = FEATURES.register("chorus_mychrodegia", () -> new ChorusMychrodegiaFeature(NoneFeatureConfiguration.CODEC));

    // Trees
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ENIVILE_TREE = FEATURES.register("enivile_tree", () -> new EnivileTreeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> CAERULWOOD_TREE = FEATURES.register("caerulwood_tree", () -> new CaerulwoodTreeFeature(NoneFeatureConfiguration.CODEC));
}
