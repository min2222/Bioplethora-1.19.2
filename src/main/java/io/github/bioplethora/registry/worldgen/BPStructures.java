package io.github.bioplethora.registry.worldgen;

import java.util.Map;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.structures.AlphanumMausoleumStructure;
import io.github.bioplethora.world.structures.AlphanumMausoleumStructureType;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BPStructures {

	public static final ResourceLocation ALPHANUM_MAUSOLEUM_LOCATION = new ResourceLocation(Bioplethora.MOD_ID, "alphanum_mausoleum");
	
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, Bioplethora.MOD_ID);
    public static final DeferredRegister<StructureSet> STRUCTURES_SET = DeferredRegister.create(Registries.STRUCTURE_SET, Bioplethora.MOD_ID);
    
    public static final TagKey<Structure> ALPHANUM_MAUSOLEUM_TAG = create("alphanum_mausoleum");
    public static final ResourceKey<StructureSet> ALPHANUM_MAUSOLEUM_TAG_SETS_KEY = ResourceKey.create(Registries.STRUCTURE_SET, ALPHANUM_MAUSOLEUM_LOCATION);
    public static final ResourceKey<Structure> ALPHANUM_MAUSOLEUM_TAG_KEY = ResourceKey.create(Registries.STRUCTURE, ALPHANUM_MAUSOLEUM_LOCATION);

    public static final RegistryObject<StructureType<?>> ALPHANUM_MAUSOLEUM_TYPE = STRUCTURES.register("alphanum_mausoleum", () -> new AlphanumMausoleumStructureType().TYPE);
    
	public static void bootstrapStructures(BootstapContext<Structure> context) {
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		context.register(ALPHANUM_MAUSOLEUM_TAG_KEY, new AlphanumMausoleumStructure(new StructureSettings(biomes.getOrThrow(tag("mausoleum")), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
	}
    
	public static void bootstrapSets(BootstapContext<StructureSet> context) {
		HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
		context.register(ALPHANUM_MAUSOLEUM_TAG_SETS_KEY, new StructureSet(structures.getOrThrow(ALPHANUM_MAUSOLEUM_TAG_KEY), new RandomSpreadStructurePlacement(230, 30, RandomSpreadType.LINEAR, 1234567890)));
	}
    
    private static TagKey<Biome> tag(String name)
    {
        return TagKey.create(Registries.BIOME, new ResourceLocation(Bioplethora.MOD_ID, name));
    }
    
    private static TagKey<Structure> create(String name) 
    {
        return TagKey.create(Registries.STRUCTURE, new ResourceLocation(Bioplethora.MOD_ID, name));
     }
}
