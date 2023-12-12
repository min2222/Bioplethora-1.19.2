package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.potion.SpiritFissionEffect;
import io.github.bioplethora.potion.SpiritManipulationEffect;
import io.github.bioplethora.potion.SpiritStrengtheningEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Bioplethora.MOD_ID);

    public static final RegistryObject<MobEffect> SPIRIT_FISSION = EFFECTS.register("spirit_fission", () -> new SpiritFissionEffect(MobEffectCategory.BENEFICIAL, -3355393));
    public static final RegistryObject<MobEffect> SPIRIT_MANIPULATION = EFFECTS.register("spirit_manipulation", () -> new SpiritManipulationEffect(MobEffectCategory.BENEFICIAL, -3355393));
    public static final RegistryObject<MobEffect> SPIRIT_STRENGTHENING = EFFECTS.register("spirit_strengthening", () -> new SpiritStrengtheningEffect(MobEffectCategory.BENEFICIAL, -3355393));
}
