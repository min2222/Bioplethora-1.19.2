package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.registry.BPDamageSources;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CryoblazeEntity extends AbstractHurtingProjectile implements GeoEntity {

	private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("animation.cryoblaze.main");
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public int lifespan = 0;

    public CryoblazeEntity(EntityType<? extends AbstractHurtingProjectile> type, Level world) {
        super(type, world);
    }

    @OnlyIn(Dist.CLIENT)
    public CryoblazeEntity(Level world, double p_i1768_2_, double p_i1768_4_, double p_i1768_6_, double p_i1768_8_, double p_i1768_10_, double p_i1768_12_) {
        super(BPEntities.CRYOBLAZE.get(), p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_, world);
    }

    public CryoblazeEntity(Level world, LivingEntity entity, double v, double v1, double v2) {
        super(BPEntities.CRYOBLAZE.get(), entity, v, v1, v2, world);
    }

    @Override
    public void tick() {
        super.tick();
        double x = this.getX(), y = this.getY(), z = this.getZ();
        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.CLOUD, x, y, z, 3, 0.2, 0.2, 0.2, 0.1);
        }

        ++lifespan;
        if (lifespan == 100) {
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        Entity entity = entityHitResult.getEntity();

        if(this.getOwner() != null) {
            if (entity instanceof Projectile) {
                if (!(((Projectile) entity).getOwner() == this.getOwner())) {
                    this.hitAndExplode();
                }
            } else {
                if (entity != this.getOwner()) {
                    this.hitAndExplode();
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult p_230299_1_) {
        super.onHitBlock(p_230299_1_);
        if(this.getOwner() != null) {
        	this.hitAndExplode();
        }
    }

    public void hitAndExplode() {
        double x = this.getX(), y = this.getY(), z = this.getZ();
        BlockPos blockpos = new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ());
        DamageSource castration = BPDamageSources.indirectCastration(this.getOwner(), this.getOwner());

        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.CLOUD, x, y, z, 20, 0.75, 0.75, 0.75, 0.01);
        }

        this.level.playSound(null, blockpos, SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, (float) 1, (float) 1);
        this.level.playSound(null, blockpos, SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, (float) 1, (float) 1);

        if (this.level instanceof ServerLevel && !(this.getOwner() == null)) {

            for (LivingEntity entityArea : this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(5, 2, 5))) {
                if (entityArea != null && entityArea != this.getOwner()) {

                    if (BPConfig.IN_HELLMODE) {
                        entityArea.hurt(castration, (float) 9);
                        entityArea.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 2));
                        entityArea.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 80, 1));
                        entityArea.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 80, 1));
                    } else {
                        entityArea.hurt(castration, (float) 6);
                        entityArea.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                        entityArea.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 1));
                        entityArea.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40));
                    }

                    if (entityArea instanceof Player) {
                        Player player = (Player) entityArea;
                        player.disableShield(true);
                    }
                }
            }
        }

        if (!this.level.isClientSide && this.getOwner() != null) {
            if (((LivingEntity) this.getOwner()).getHealth() <= 100 && BPConfig.COMMON.hellMode.get()) {
                this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 3F, EntityUtils.getMobGriefingEvent(this.level, this));
            } else {
                this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.5F, EntityUtils.getMobGriefingEvent(this.level, this));
            }
            this.discard();
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return false;
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
        event.getController().setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "cryoblaze_controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }
}
