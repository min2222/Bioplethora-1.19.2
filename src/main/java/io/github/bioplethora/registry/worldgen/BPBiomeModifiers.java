package io.github.bioplethora.registry.worldgen;

import com.mojang.serialization.Codec;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.world.BPBiomeModifier;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPBiomeModifiers {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Bioplethora.MOD_ID);
	public static final RegistryObject<Codec<? extends BiomeModifier>> BP_MODIFIERS = BIOME_MODIFIERS.register("bp_modifiers", BPBiomeModifier::makeCodec);
}
