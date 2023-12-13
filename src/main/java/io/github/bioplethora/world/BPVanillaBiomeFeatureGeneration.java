package io.github.bioplethora.world;

import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
//TODO
public class BPVanillaBiomeFeatureGeneration {

	/*public static final ForgeBiomeModifiers.AddFeaturesBiomeModifier OVERWORLD = 
    		new AddFeaturesBiomeModifier(BuiltinRegistries.BIOME.getTag(BiomeTags.IS_OVERWORLD).get(), 
    				BuiltinRegistries.PLACED_FEATURE.getOrCreateTag(BPConfiguredFeatures.FLEIGNARITE_VINES), Decoration.VEGETAL_DECORATION);
    
    
    public static void generateFeatures(final BiomeLoadingEvent event) {
        RegistryKey<Biome> key = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
        Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(key);

        List<Supplier<ConfiguredCarver<?>>> liqCarver = event.getGeneration().getCarvers(Carving.LIQUID);

        List<Supplier<ConfiguredFeature<?, ?>>> localDeco = event.getGeneration().getFeatures(Decoration.LOCAL_MODIFICATIONS);
        List<Supplier<ConfiguredFeature<?, ?>>> topLayerDeco = event.getGeneration().getFeatures(Decoration.TOP_LAYER_MODIFICATION);
        List<Supplier<ConfiguredFeature<?, ?>>> undergroundDeco = event.getGeneration().getFeatures(Decoration.UNDERGROUND_DECORATION);
        List<Supplier<ConfiguredFeature<?, ?>>> vegDeco = event.getGeneration().getFeatures(Decoration.VEGETAL_DECORATION);
        
        if (types.contains(BiomeDictionary.Type.OVERWORLD)) {
            undergroundDeco.add(() -> BPConfiguredFeatures.FLEIGNARITE_REMAINS);
            undergroundDeco.add(() -> BPConfiguredFeatures.FLEIGNARITE_VINES);
            vegDeco.add(() -> BPConfiguredFeatures.FLEIGNARITE_REMAINS);
            vegDeco.add(() -> BPConfiguredFeatures.FLEIGNARITE_VINES);
        }

        if (types.contains(BiomeDictionary.Type.NETHER)) {
            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.BASALT_DELTAS)) {
                vegDeco.add(() -> BPConfiguredFeatures.BASALT_SPELEOTHERM);

                vegDeco.add(() -> BPConfiguredFeatures.LAVA_SPIRE);
            }
            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.NETHER_WASTES)) {
                vegDeco.add(() -> BPConfiguredFeatures.THONTUS_THISTLE);

                vegDeco.add(() -> BPConfiguredFeatures.LAVA_SPIRE);
            }
            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.WARPED_FOREST)) {
                vegDeco.add(() -> BPConfiguredFeatures.WARPED_DANCER);

                vegDeco.add(() -> BPConfiguredFeatures.TURQUOISE_PENDENT);
            }
            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.CRIMSON_FOREST)) {
                vegDeco.add(() -> BPConfiguredFeatures.CERISE_IVY);

                vegDeco.add(() -> BPConfiguredFeatures.LAVA_SPIRE);
            }
            if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.SOUL_SAND_VALLEY)) {
                vegDeco.add(() -> BPConfiguredFeatures.SOUL_MINISHROOM);
                vegDeco.add(() -> BPConfiguredFeatures.SOUL_BIGSHROOM);

                vegDeco.add(() -> BPConfiguredFeatures.SOUL_SPROUTS);
                vegDeco.add(() -> BPConfiguredFeatures.SOUL_TALL_GRASS);

                vegDeco.add(() -> BPConfiguredFeatures.SPIRIT_DANGLER);

                vegDeco.add(() -> BPConfiguredFeatures.SOUL_ETERN);
            }
        }

        if (types.contains(BiomeDictionary.Type.END)) {
            if (BPConfig.WORLDGEN.cyraLakesEnd.get()) undergroundDeco.add(() -> BPConfiguredFeatures.CYRA_LAKE);

            if (!BPConfig.WORLDGEN.createNewSpongeBiome.get()) {
                if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.END_HIGHLANDS)) {

                    //if (BPConfig.WORLDGEN.endSpongeHighlands.get()) liqCarver.add(() -> BPConfiguredLevelCarvers.END_SPRINGS_CARVER);
                    //if (BPConfig.WORLDGEN.endSpongeHighlands.get()) undergroundDeco.add(() -> BPConfiguredFeatures.END_LAND_ROCK);

                    vegDeco.add(() -> BPConfiguredFeatures.CHORUS_MYCHRODEGIA);

                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) topLayerDeco.add(() -> BPConfiguredFeatures.ENREDE_KELP);

                    if (BPConfig.WORLDGEN.chorusLanternHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH);
                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDON);
                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDE_FAN);
                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.ENREDE_CORSASCILE);
                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_PURPLE);
                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_RED);
                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_GREEN);

                    if (BPConfig.WORLDGEN.endSpikeHighlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LAND_SPIKE_PATCH_HL);
                    if (BPConfig.WORLDGEN.endSpongeHighlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LAND_SPONGE_PATCH_HL);
                    //if (BPConfig.WORLDGEN.endSpongeHighlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LANDS_CAVERN);
                }

                if (WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.END_MIDLANDS) || WorldgenUtils.getBiomeFromEvent(event, WorldgenUtils.END_BARRENS)) {

                    //if (BPConfig.WORLDGEN.endSpongeMidlands.get()) liqCarver.add(() -> BPConfiguredLevelCarvers.END_SPRINGS_CARVER);

                    vegDeco.add(() -> BPConfiguredFeatures.CHORUS_MYCHRODEGIA);

                    if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) topLayerDeco.add(() -> BPConfiguredFeatures.ENREDE_KELP);

                    if (BPConfig.WORLDGEN.chorusLanternMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_LANTERN_MIDLANDS_PATCH);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDON);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.CHORUS_IDE_FAN);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.ENREDE_KELP);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.ENREDE_CORSASCILE);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_PURPLE);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_RED);
                    if (BPConfig.WORLDGEN.chorusVegetationMidlands.get()) vegDeco.add(() -> BPConfiguredFeatures.OCHAIM_GREEN);

                    if (BPConfig.WORLDGEN.endSpikeMidlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LAND_SPIKE_PATCH_ML);
                    if (BPConfig.WORLDGEN.endSpongeMidlands.get()) localDeco.add(() -> BPConfiguredFeatures.END_LAND_SPONGE_PATCH_ML);

                }
            }
        }
    }*/

    public static ImmutableList<Block> stoneBlocks() {
        return ImmutableList.of(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE);
    }

    public static ImmutableList<BlockState> stoneBlockstates() {
        return ImmutableList.of(Blocks.STONE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), Blocks.GRANITE.defaultBlockState());
    }

    public static Random seedFleignarChunk(int pChunkX, int pChunkZ, long pSeed, long l) {
        return new Random(pSeed + ((long) pChunkX * pChunkX * 4987142) + (pChunkX * 5947611L) + (long) pChunkZ * pChunkZ * 4392871L + (pChunkZ * 389711L) ^ l);
    }

    public static boolean isFleignariteChunk(BlockPos pos, WorldGenLevel seedReader) {
        ChunkPos chunkpos = new ChunkPos(pos);
        return seedFleignarChunk(chunkpos.x, chunkpos.z, seedReader.getSeed(), 987234911L).nextInt(20) == 0;
    }
}
