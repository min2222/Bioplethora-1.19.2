package io.github.bioplethora.registry.worldgen;

import java.util.Map;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.structures.AlphanumMausoleumStructure;
import io.github.bioplethora.world.structures.AlphanumMausoleumStructureType;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
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
	
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, Bioplethora.MOD_ID);
    public static final DeferredRegister<StructureSet> STRUCTURES_SET = DeferredRegister.create(Registry.STRUCTURE_SET_REGISTRY, Bioplethora.MOD_ID);
    public static final TagKey<Structure> ALPHANUM_MAUSOLEUM_TAG = create("alphanum_mausoleum");
    
	public static final Holder<Structure> ALPHANUM_MAUSOLEUM = register(ResourceKey.create(Registry.STRUCTURE_REGISTRY, ALPHANUM_MAUSOLEUM_LOCATION), new AlphanumMausoleumStructure(new StructureSettings(BuiltinRegistries.BIOME.getOrCreateTag(tag("mausoleum")), Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.NONE)));
	public static final Holder<StructureSet> ALPHANUM_MAUSOLEUM_SETS = register(ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, ALPHANUM_MAUSOLEUM_LOCATION), new StructureSet(ALPHANUM_MAUSOLEUM, new RandomSpreadStructurePlacement(230, 30, RandomSpreadType.LINEAR, 1234567890)));

    public static final RegistryObject<StructureType<?>> ALPHANUM_MAUSOLEUM_TYPE = STRUCTURES.register("alphanum_mausoleum", () -> new AlphanumMausoleumStructureType().TYPE);
	
    private static Holder<Structure> register(ResourceKey<Structure> p_236534_, Structure p_236535_)
	{
		return BuiltinRegistries.register(BuiltinRegistries.STRUCTURES, p_236534_, p_236535_);
	}
	
    static Holder<StructureSet> register(ResourceKey<StructureSet> p_211129_, StructureSet p_211130_) 
	{
		return BuiltinRegistries.register(BuiltinRegistries.STRUCTURE_SETS, p_211129_, p_211130_);
	}
    
    private static TagKey<Biome> tag(String name)
    {
        return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Bioplethora.MOD_ID, name));
    }
    
    private static TagKey<Structure> create(String name) 
    {
        return TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(Bioplethora.MOD_ID, name));
     }
}
