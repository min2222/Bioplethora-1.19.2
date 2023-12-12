package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.api.events.BPHooks;
import io.github.bioplethora.api.world.EffectUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Bioplethora.MOD_ID);

    public static final RegistryObject<Attribute> TRUE_DEFENSE = ATTRIBUTES.register("bioplethora.true_defense", () -> new RangedAttribute("attribute.name.bioplethora.true_defense", 0.0D, 0.0D, 1024.0D).setSyncable(true));

    @SubscribeEvent
    public static void useTrueDefenseAttribute(LivingHurtEvent event) {

        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity victim = (LivingEntity) event.getEntity();

            if (victim.getAttribute(TRUE_DEFENSE.get()) != null) {

                if (BPHooks.onTrueDefenseHurt(victim, event.getSource())) return;

                victim.playSound(BPSoundEvents.TRUE_DEFENSE_CLASH.get(), 1.0F, 0.5F + victim.getRandom().nextFloat());
                if (victim.level instanceof ServerLevel) {
                    ((ServerLevel) victim.level).sendParticles(BPParticles.TRUE_DEFENSE_CLASH.get(),
                            victim.getX(), victim.getY() + 2, victim.getZ(), 1,
                            victim.getRandom().nextDouble() / 2.0, victim.getRandom().nextDouble() / 2.0, victim.getRandom().nextDouble() / 2.0, 0.02);

                    EffectUtils.addCircleParticleForm(victim.level, victim, ParticleTypes.CRIT, 75, 0.55, 0.01);
                }

                event.setAmount(event.getAmount() - (float) victim.getAttributeValue(TRUE_DEFENSE.get()));
            }
        }
    }
}
