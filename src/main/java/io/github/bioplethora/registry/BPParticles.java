package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.particles.WindPoofParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Bioplethora.MOD_ID);

    public static final RegistryObject<WindPoofParticleType> WIND_POOF = PARTICLES.register("wind_poof", WindPoofParticleType::new);
    public static final RegistryObject<SimpleParticleType> NIGHT_GAZE = PARTICLES.register("night_gaze", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ANTIBIO_SPELL = PARTICLES.register("antibio_spell", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> TRUE_DEFENSE_CLASH = PARTICLES.register("true_defense_clash", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> KINGS_ROAR = PARTICLES.register("kings_roar", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FROSTBITE_EYE = PARTICLES.register("frostbite_eye", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SHACHATH_CLASH_INNER = PARTICLES.register("shachath_clash_inner", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SHACHATH_CLASH_OUTER = PARTICLES.register("shachath_clash_outer", () -> new SimpleParticleType(true));

    // Falling Leaves
    public static final RegistryObject<SimpleParticleType> PINK_ENIVILE_LEAF = PARTICLES.register("pink_enivile_leaf", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> RED_ENIVILE_LEAF = PARTICLES.register("red_enivile_leaf", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> CAERULWOOD_LEAF = PARTICLES.register("caerulwood_leaf", () -> new SimpleParticleType(true));

}
