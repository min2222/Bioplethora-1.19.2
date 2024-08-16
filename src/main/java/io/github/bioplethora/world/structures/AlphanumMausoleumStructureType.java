package io.github.bioplethora.world.structures;

import com.mojang.serialization.Codec;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class AlphanumMausoleumStructureType implements StructureType<AlphanumMausoleumStructure>
{
	public StructureType<AlphanumMausoleumStructure> TYPE = register("bioplethora:alphanum_mausoleum", AlphanumMausoleumStructure.CODEC);
	
	private static <S extends Structure> StructureType<S> register(String p_226882_, Codec<S> p_226883_) {
		return Registry.register(BuiltInRegistries.STRUCTURE_TYPE, p_226882_, () -> {
			return p_226883_;
		});
	}
	   
	@Override
	public Codec<AlphanumMausoleumStructure> codec()
	{
		return AlphanumMausoleumStructure.CODEC;
	}
}
