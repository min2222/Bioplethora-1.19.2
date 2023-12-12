package io.github.bioplethora.particles;

import com.mojang.serialization.Codec;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @Credit MaxBogomol
 */
public class WindPoofParticleType extends ParticleType<WindPoofParticleData> implements ParticleOptions {

    private static final boolean ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER = false;

    public WindPoofParticleType() {
        super(ALWAYS_SHOW_REGARDLESS_OF_DISTANCE_FROM_PLAYER, WindPoofParticleData.DESERIALIZER);
    }

    public Codec<WindPoofParticleData> codec() {
        return WindPoofParticleData.CODEC;
    }

    @Override
    public ParticleType<?> getType() {
        return this;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {

    }

    @Override
    public String writeToString() {
        return ForgeRegistries.PARTICLE_TYPES.getKey(this).toString();
    }
}
