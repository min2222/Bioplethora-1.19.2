package io.github.bioplethora.entity.others;

import io.github.bioplethora.api.advancements.AdvancementUtils;
import io.github.bioplethora.entity.creatures.AltyrusEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class AltyrusSummoningEntity extends Entity implements IAnimatable {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public int birthTime = 0;

    public AltyrusSummoningEntity(EntityType<?> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void defineSynchedData() {
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.altyrus_summoning.summoning", EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "altyrussummoningcontroller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public void tick() {
        super.tick();

        ++birthTime;

        this.move(MoverType.SELF, new Vec3(0.0D, 0.15F, 0.0D));

        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(ParticleTypes.POOF, (this.getX()), (this.getY()), (this.getZ()), 5, 1, 1, 1, 0.1);
        }

        if (this.birthTime >= 100) {

            if (!this.level.isClientSide) {
                this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 5F, Explosion.BlockInteraction.BREAK);

                ServerLevel serverworld = (ServerLevel)this.level;
                BlockPos blockpos = (new BlockPos(this.getX(), this.getY(), this.getZ()));

                AltyrusEntity altyrusEntity = BPEntities.ALTYRUS.get().create(this.level);
                altyrusEntity.moveTo(blockpos, 0.0F, 0.0F);
                altyrusEntity.finalizeSpawn(serverworld, this.level.getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);

                serverworld.addFreshEntity(altyrusEntity);

                this.discard();
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_70037_1_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_213281_1_) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void grantBirthAdvancement(int radius) {
        Entity entity = this;
        Level world = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();
        AABB area = new AABB(x - (radius / 2d), y, z - (radius / 2d), x + (radius / 2d), y + (radius / 2d), z + (radius / 2d));

        //Grant Advancement to all nearby players
        for (LivingEntity entityIterator : world.getEntitiesOfClass(LivingEntity.class, area)) {
            if (entityIterator != null) {
                AdvancementUtils.grantBioAdvancement(entityIterator, "bioplethora:altyrus_summoning");
            }
        }
    }
}
