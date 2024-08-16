package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.api.world.EffectUtils;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class NetheriteObsidianGaidiusEntity extends GaidiusBaseEntity {

    public NetheriteObsidianGaidiusEntity(EntityType<? extends NetheriteObsidianGaidiusEntity> type, Level world) {
        super(type, world);
    }

    public NetheriteObsidianGaidiusEntity(Level world, LivingEntity entity) {
        super(BPEntities.NETHERITE_OBSIDIAN_GAIDIUS.get(), entity, world);
    }

    public NetheriteObsidianGaidiusEntity(Level world, double v, double v1, double v2) {
        super(BPEntities.NETHERITE_OBSIDIAN_GAIDIUS.get(), world, v, v1, v2);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        this.playSound(SoundEvents.ANVIL_PLACE, 0.65F, 0.8F);
        EffectUtils.addCircleParticleForm(level, this, ParticleTypes.POOF, 7, 0.55, 0.0001);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        if (result.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) result.getEntity();
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 3));
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (level.getBlockState(pResult.getBlockPos()) == Material.GLASS) {
            level.destroyBlock(pResult.getBlockPos(), false);
        } else {
            this.playSound(SoundEvents.WITHER_BREAK_BLOCK, 0.65F, 0.65F);
            BlockUtils.knockUpRandomNearbyBlocks(level, 0.25D, pResult.getBlockPos(), 2, 2, 2, true, true);
            this.discard();
        }
    }

    @Override
    public int getProjectileDamage(EntityHitResult result) {
        return isCrit() ? 24 : 16;
    }

    @Override
    protected Item getDefaultItem() {
        return BPItems.NETHERITE_OBSIDIAN_GAIDIUS.get();
    }
}
