package io.github.bioplethora.registry.worldgen;

import com.mojang.serialization.Codec;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.world.BPBiomeModifier;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPBiomeModifiers {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Bioplethora.MOD_ID);
	public static final RegistryObject<Codec<? extends BiomeModifier>> BP_MODIFIERS = BIOME_MODIFIERS.register("bp_modifiers", BPBiomeModifier::makeCodec);
	
	public static final ResourceKey<BiomeModifier> ADD_FLEIGNARITE_UNDERGROUND = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_fleignarite_underground"));
	public static final ResourceKey<BiomeModifier> ADD_FLEIGNARITE_VEGETAL = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_fleignarite_vegetal"));
	public static final ResourceKey<BiomeModifier> ADD_BASLAT_DELTAS = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_basalt_deltas"));
	public static final ResourceKey<BiomeModifier> ADD_NETHER_WASTES = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_nether_wastes"));
	public static final ResourceKey<BiomeModifier> ADD_WARPED_FOREST = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_warped_forest"));
	public static final ResourceKey<BiomeModifier> ADD_CRIMSON_FOREST = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_crimson_forest"));
	public static final ResourceKey<BiomeModifier> ADD_SOUL_SAND_VALLEY = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_soul_sand_valley"));
	public static final ResourceKey<BiomeModifier> ADD_CYRA_LAKE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_cyra_lake"));
	public static final ResourceKey<BiomeModifier> ADD_CHORUS_MYCHRODEGIA = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_chorus_mychrodegia"));
	public static final ResourceKey<BiomeModifier> ADD_ENDRE_KELP = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_endre_kelp"));
	public static final ResourceKey<BiomeModifier> ADD_HIGHLANDS = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_highlands"));
	public static final ResourceKey<BiomeModifier> ADD_LANTERN_HIGHLANDS = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_lantern_highlands"));
	public static final ResourceKey<BiomeModifier> ADD_END_SPONGE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_end_sponge"));
	public static final ResourceKey<BiomeModifier> ADD_END_SPIKE = ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Bioplethora.MOD_ID, "add_end_spike"));
	
	public static void bootstrap(BootstapContext<BiomeModifier> context) {
		HolderGetter<Biome> biome = context.lookup(Registries.BIOME);
		HolderGetter<PlacedFeature> placedFeature = context.lookup(Registries.PLACED_FEATURE);
		
		context.register(ADD_FLEIGNARITE_UNDERGROUND, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biome.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placedFeature.getOrThrow(BPPlacedFeatures.FLEIGNARITE_REMAINS), placedFeature.getOrThrow(BPPlacedFeatures.FLEIGNARITE_VINES)), Decoration.UNDERGROUND_DECORATION));
		context.register(ADD_FLEIGNARITE_VEGETAL, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biome.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placedFeature.getOrThrow(BPPlacedFeatures.FLEIGNARITE_REMAINS), placedFeature.getOrThrow(BPPlacedFeatures.FLEIGNARITE_VINES)), Decoration.VEGETAL_DECORATION));
		context.register(ADD_BASLAT_DELTAS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biome.getOrThrow(Biomes.BASALT_DELTAS)), HolderSet.direct(placedFeature.getOrThrow(BPPlacedFeatures.BASALT_SPELEOTHERM), placedFeature.getOrThrow(BPPlacedFeatures.LAVA_SPIRE)), Decoration.VEGETAL_DECORATION));
		context.register(ADD_NETHER_WASTES, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biome.getOrThrow(Biomes.NETHER_WASTES)), HolderSet.direct(placedFeature.getOrThrow(BPPlacedFeatures.THONTUS_THISTLE), placedFeature.getOrThrow(BPPlacedFeatures.LAVA_SPIRE)), Decoration.VEGETAL_DECORATION));
		context.register(ADD_WARPED_FOREST, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biome.getOrThrow(Biomes.WARPED_FOREST)), HolderSet.direct(placedFeature.getOrThrow(BPPlacedFeatures.WARPED_DANCER), placedFeature.getOrThrow(BPPlacedFeatures.TURQUOISE_PENDENT)), Decoration.VEGETAL_DECORATION));
		context.register(ADD_CRIMSON_FOREST, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biome.getOrThrow(Biomes.CRIMSON_FOREST)), HolderSet.direct(placedFeature.getOrThrow(BPPlacedFeatures.CERISE_IVY), placedFeature.getOrThrow(BPPlacedFeatures.LAVA_SPIRE)), Decoration.VEGETAL_DECORATION));
		context.register(ADD_SOUL_SAND_VALLEY, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biome.getOrThrow(Biomes.SOUL_SAND_VALLEY)), HolderSet.direct(
				placedFeature.getOrThrow(BPPlacedFeatures.SOUL_MINISHROOM), placedFeature.getOrThrow(BPPlacedFeatures.SOUL_BIGSHROOM),
				placedFeature.getOrThrow(BPPlacedFeatures.SOUL_SPROUTS), placedFeature.getOrThrow(BPPlacedFeatures.SOUL_TALL_GRASS),
				placedFeature.getOrThrow(BPPlacedFeatures.SPIRIT_DANGLER), placedFeature.getOrThrow(BPPlacedFeatures.SOUL_ETERN)), Decoration.VEGETAL_DECORATION));
		
		if (BPConfig.WORLDGEN.cyraLakesEnd.get()) context.register(ADD_CYRA_LAKE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biome.getOrThrow(BiomeTags.IS_END), HolderSet.direct(placedFeature.getOrThrow(BPPlacedFeatures.CYRA_LAKE)), Decoration.UNDERGROUND_DECORATION));
		
		if (!BPConfig.WORLDGEN.createNewSpongeBiome.get()) {
			context.register(ADD_CHORUS_MYCHRODEGIA, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biome.getOrThrow(Biomes.END_HIGHLANDS), biome.getOrThrow(Biomes.END_MIDLANDS), biome.getOrThrow(Biomes.END_BARRENS)), HolderSet.direct(placedFeature.getOrThrow(BPPlacedFeatures.CHORUS_MYCHRODEGIA)), Decoration.VEGETAL_DECORATION));
			
            if (BPConfig.WORLDGEN.chorusVegetationHighlands.get()) {
            	context.register(ADD_ENDRE_KELP, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biome.getOrThrow(Biomes.END_HIGHLANDS), biome.getOrThrow(Biomes.END_MIDLANDS), biome.getOrThrow(Biomes.END_BARRENS)), HolderSet.direct(placedFeature.getOrThrow(BPPlacedFeatures.ENREDE_KELP)), Decoration.TOP_LAYER_MODIFICATION));
            	context.register(ADD_HIGHLANDS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biome.getOrThrow(Biomes.END_HIGHLANDS), biome.getOrThrow(Biomes.END_MIDLANDS), biome.getOrThrow(Biomes.END_BARRENS)), HolderSet.direct(
            			placedFeature.getOrThrow(BPPlacedFeatures.CHORUS_IDON), placedFeature.getOrThrow(BPPlacedFeatures.CHORUS_IDE_FAN),
            			placedFeature.getOrThrow(BPPlacedFeatures.ENREDE_CORSASCILE), placedFeature.getOrThrow(BPPlacedFeatures.OCHAIM_PURPLE),
            			placedFeature.getOrThrow(BPPlacedFeatures.OCHAIM_RED), placedFeature.getOrThrow(BPPlacedFeatures.OCHAIM_GREEN)), Decoration.VEGETAL_DECORATION));
            }

            if (BPConfig.WORLDGEN.chorusLanternHighlands.get()) context.register(ADD_LANTERN_HIGHLANDS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biome.getOrThrow(Biomes.END_HIGHLANDS), biome.getOrThrow(Biomes.END_MIDLANDS), biome.getOrThrow(Biomes.END_BARRENS)), HolderSet.direct(placedFeature.getOrThrow(BPPlacedFeatures.CHORUS_LANTERN_HIGHLANDS_PATCH)), Decoration.VEGETAL_DECORATION));
        	
            if (BPConfig.WORLDGEN.endSpikeHighlands.get()) context.register(ADD_END_SPIKE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biome.getOrThrow(Biomes.END_HIGHLANDS), biome.getOrThrow(Biomes.END_MIDLANDS), biome.getOrThrow(Biomes.END_BARRENS)), HolderSet.direct(placedFeature.getOrThrow(BPPlacedFeatures.END_LAND_SPIKE_PATCH_HL)), Decoration.LOCAL_MODIFICATIONS));
            
            if (BPConfig.WORLDGEN.endSpongeHighlands.get()) context.register(ADD_END_SPONGE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct(biome.getOrThrow(Biomes.END_HIGHLANDS), biome.getOrThrow(Biomes.END_MIDLANDS), biome.getOrThrow(Biomes.END_BARRENS)), HolderSet.direct(placedFeature.getOrThrow(BPPlacedFeatures.END_LAND_SPONGE_PATCH_HL)), Decoration.LOCAL_MODIFICATIONS));
		}
	}
}
