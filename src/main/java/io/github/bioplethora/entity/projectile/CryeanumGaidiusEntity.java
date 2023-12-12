package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.api.world.EffectUtils;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import io.github.bioplethora.registry.BPParticles;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class CryeanumGaidiusEntity extends GaidiusBaseEntity {

    public CryeanumGaidiusEntity(EntityType<? extends CryeanumGaidiusEntity> type, Level world) {
        super(type, world);
    }

    public CryeanumGaidiusEntity(Level world, LivingEntity entity) {
        super(BPEntities.CRYEANUM_GAIDIUS.get(), entity, world);
    }

    public CryeanumGaidiusEntity(Level world, double v, double v1, double v2) {
        super(BPEntities.CRYEANUM_GAIDIUS.get(), world, v, v1, v2);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        this.playSound(SoundEvents.GRASS_BREAK, 1.2F, 0.6F);
        EffectUtils.addCircleParticleForm(level, this, BPParticles.RED_ENIVILE_LEAF.get(), 7, 0.55, 0.0001);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
    }

    @Override
    public int getProjectileDamage(EntityHitResult result) {
        return isCrit() ? 12 : 7;
    }

    @Override
    protected Item getDefaultItem() {
        return BPItems.CRYEANUM_GAIDIUS.get();
    }
}
