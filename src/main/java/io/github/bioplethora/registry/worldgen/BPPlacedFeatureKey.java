package io.github.bioplethora.registry.worldgen;

import io.github.bioplethora.Bioplethora;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BPPlacedFeatureKey {
	public static final ResourceKey<PlacedFeature> GLACYNTH = createKey("glacynth");
	public static final ResourceKey<PlacedFeature> PINK_TWI = createKey("pink_twi");
	public static final ResourceKey<PlacedFeature> RED_TWI = createKey("red_twi");
	public static final ResourceKey<PlacedFeature> SPINXELTHORN = createKey("spinxelthorn");
	public static final ResourceKey<PlacedFeature> SPIRIT_DANGLER = createKey("spirit_dangler");
	public static final ResourceKey<PlacedFeature> LAVA_SPIRE = createKey("lava_spire");
	public static final ResourceKey<PlacedFeature> FLEIGNARITE_REMAINS = createKey("fleignarite_remains");
	public static final ResourceKey<PlacedFeature> FLEIGNARITE_VINES = createKey("fleignarite_vines");
	public static final ResourceKey<PlacedFeature> CYRA_LAKE = createKey("cyra_lake");
	public static final ResourceKey<PlacedFeature> BYRSS_LANTERN_PLANT_PATCH = createKey("byrss_lantern_plant_patch");
	public static final ResourceKey<PlacedFeature> BYRSS_LANTERN_FOREST_PATCH = createKey("byrss_lantern_forest_patch");
	public static final ResourceKey<PlacedFeature> END_LAND_SPONGE_PATCH_ML = createKey("end_land_sponge_patch_ml");
	/*public static final ResourceKey<PlacedFeature> END_LAND_SPONGE_PATCH_HL = createKey("end_land_sponge_patch_hl");*/
	public static final ResourceKey<PlacedFeature> CAERI_CAVERN = createKey("caeri_cavern");
	public static final ResourceKey<PlacedFeature> CHORUS_MYCHRODEGIA = createKey("chorus_plant");
	public static final ResourceKey<PlacedFeature> END_LAND_SPIKE_PATCH_HL = createKey("end_land_spike_patch_hl");
	public static final ResourceKey<PlacedFeature> END_LAND_SPIKE_PATCH_ML = createKey("end_land_spike_patch_ml");
	public static final ResourceKey<PlacedFeature> CHORUS_LANTERN_HIGHLANDS_PATCH = createKey("chorus_lantern_highlands_patch");
	public static final ResourceKey<PlacedFeature> CHORUS_LANTERN_MIDLANDS_PATCH = createKey("chorus_lantern_midlands_patch");
	public static final ResourceKey<PlacedFeature> ENREDE_KELP = createKey("enrede_kelp");
	public static final ResourceKey<PlacedFeature> CELESTIA_BUD = createKey("celestia_bud");
	public static final ResourceKey<PlacedFeature> ENREDE_CORSASCILE = createKey("enrede_corsascile");
	public static final ResourceKey<PlacedFeature> OCHAIM_PURPLE = createKey("ochaim_purple");
	public static final ResourceKey<PlacedFeature> OCHAIM_RED = createKey("ochaim_red");
	public static final ResourceKey<PlacedFeature> OCHAIM_GREEN = createKey("ochaim_green");
	public static final ResourceKey<PlacedFeature> END_ISLANDS_ICICLE_PATCH = createKey("end_islands_icicle_patch");
	public static final ResourceKey<PlacedFeature> END_FROZEN_ISLAND_DECORATED = createKey("end_frozen_island_decorated");
	/*public static final ResourceKey<PlacedFeature> IRION_GRASS = createKey("irion_grass");*/
	public static final ResourceKey<PlacedFeature> IRION_TALL_GRASS = createKey("irion_tall_grass");
	public static final ResourceKey<PlacedFeature> ARTAIRIUS = createKey("artairius");
	public static final ResourceKey<PlacedFeature> FROSTEM = createKey("frostem");
	public static final ResourceKey<PlacedFeature> BASALT_SPELEOTHERM = createKey("basalt_speleotherm");
	public static final ResourceKey<PlacedFeature> THONTUS_THISTLE = createKey("thontus_thistle");
	public static final ResourceKey<PlacedFeature> TURQUOISE_PENDENT = createKey("turquoise_pendent");
	public static final ResourceKey<PlacedFeature> CERISE_IVY = createKey("cerise_ivy");
	public static final ResourceKey<PlacedFeature> SOUL_ETERN = createKey("soul_etern");
	public static final ResourceKey<PlacedFeature> CHORUS_IDON = createKey("chorus_idon");
	public static final ResourceKey<PlacedFeature> CHORUS_IDE_FAN = createKey("chorus_ide_fan");
	public static final ResourceKey<PlacedFeature> WARPED_DANCER = createKey("warped_dancer");
	public static final ResourceKey<PlacedFeature> SOUL_MINISHROOM = createKey("soul_minishroom");
	public static final ResourceKey<PlacedFeature> SOUL_BIGSHROOM = createKey("soul_bigshroom");
	public static final ResourceKey<PlacedFeature> SOUL_SPROUTS = createKey("soul_sprouts");
	public static final ResourceKey<PlacedFeature> SOUL_TALL_GRASS = createKey("soul_tall_grass");
	public static final ResourceKey<PlacedFeature> KYRIA = createKey("kyria");
	public static final ResourceKey<PlacedFeature> KYRIA_BELINE = createKey("kyria_beline");
	public static final ResourceKey<PlacedFeature> KYRIA_IDE_FAN = createKey("kyria_ide_fan");
	
    private static ResourceKey<PlacedFeature> createKey(String name) {
        ResourceLocation id = new ResourceLocation(Bioplethora.MOD_ID, name);
        ResourceKey<PlacedFeature> key = ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, id);
        return key;
    }
}
