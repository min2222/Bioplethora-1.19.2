package io.github.bioplethora.entity.others;

import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.IGrylynenTier;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import io.github.bioplethora.enums.BPGrylynenTier;
import io.github.bioplethora.event.helper.GrylynenSpawnHelper;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GrylynenCoreBombEntity extends Entity implements GeoEntity {

	private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("animation.grylynen_core_bomb.idle");
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public IGrylynenTier tier;
    public int birthTime = 0;
    public boolean hasSound;

    public GrylynenCoreBombEntity(EntityType<?> entityType, Level world) {
        super(entityType, world);
        this.hasSound = false;
    }

    @Override
    protected void defineSynchedData() {
    }

    private <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {

        event.getController().setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "grylynen_core_bomb_controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    public void tick() {
        super.tick();

        ++birthTime;
        this.level.addParticle(ParticleTypes.FLAME,
                getX() + (Math.random() / 2), getY() + (Math.random() / 2), getZ() + (Math.random() / 2),
                0.01D, 0.01D, 0.01D);

        if (!hasSound) {
            this.playSound(SoundEvents.BEACON_ACTIVATE, 1.0F, 1.5F);
            hasSound = true;
        }

        if (this.birthTime >= (BPConfig.IN_HELLMODE ? 40 : 60)) {

            if (!this.level.isClientSide) {
                ServerLevel serverworld = (ServerLevel) this.level;
                BlockPos blockpos = this.blockPosition();

                GrylynenEntity grylynen = BPEntities.WOODEN_GRYLYNEN.get().create(this.level);

                if (this.tier == BPGrylynenTier.WOODEN) {
                    grylynen = BPEntities.WOODEN_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.STONE) {
                    grylynen = BPEntities.STONE_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.GOLDEN) {
                    grylynen = BPEntities.GOLDEN_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.IRON) {
                    grylynen = BPEntities.IRON_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.DIAMOND) {
                    grylynen = BPEntities.DIAMOND_GRYLYNEN.get().create(this.level);
                } else if (this.tier == BPGrylynenTier.NETHERITE) {
                    grylynen = BPEntities.NETHERITE_GRYLYNEN.get().create(this.level);
                }

                grylynen.moveTo(blockpos, 0.0F, 0.0F);
                grylynen.finalizeSpawn(serverworld, level.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);

                if (!level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                    GrylynenSpawnHelper.breakSurroundingBlocks(serverworld, blockpos);
                }
                this.playSound(SoundEvents.SLIME_BLOCK_BREAK, 1.0F, 0.5F);
                if (!this.level.isClientSide()) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.FLAME, getX(), getY(), getZ(), 30, 0.75, 0.75, 0.75, 0.01);
                }
                serverworld.addFreshEntity(grylynen);

                this.discard();
            }
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        boolean flag = super.hurt(pSource, pAmount);
        this.discard();
        return flag;
    }

    @Override
    protected void markHurt() {
        super.markHurt();
        this.discard();
    }

    public void setTier(IGrylynenTier tier) {
        this.tier = tier;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_70037_1_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_213281_1_) {

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
