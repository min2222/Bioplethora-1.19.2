package io.github.bioplethora.particles;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPParticles;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BPParticleUtil {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerSpriteSetParticles(RegisterParticleProvidersEvent event) {
    	event.registerSpriteSet(BPParticles.WIND_POOF.get(), WindPoofParticle.Factory::new);
    	event.registerSpriteSet(BPParticles.NIGHT_GAZE.get(), NightGazeParticle.Factory::new);
    	event.registerSpriteSet(BPParticles.ANTIBIO_SPELL.get(), AntibioSpellParticle.Factory::new);
    	event.registerSpriteSet(BPParticles.TRUE_DEFENSE_CLASH.get(), TrueDefenseClashParticle.Factory::new);
        event.registerSpriteSet(BPParticles.KINGS_ROAR.get(), KingsRoarParticle.Factory::new);
        event.registerSpriteSet(BPParticles.FROSTBITE_EYE.get(), FrostbiteEyeParticle.Factory::new);
        event.registerSpriteSet(BPParticles.SHACHATH_CLASH_INNER.get(), ShachathClashInnerParticle.Factory::new);
        event.registerSpriteSet(BPParticles.SHACHATH_CLASH_OUTER.get(), ShachathClashOuterParticle.Factory::new);

        event.registerSpriteSet(BPParticles.CAERULWOOD_LEAF.get(), FallingLeavesParticle.Factory::new);
        event.registerSpriteSet(BPParticles.PINK_ENIVILE_LEAF.get(), FallingLeavesParticle.Factory::new);
        event.registerSpriteSet(BPParticles.RED_ENIVILE_LEAF.get(), FallingLeavesParticle.Factory::new);
    }
}
