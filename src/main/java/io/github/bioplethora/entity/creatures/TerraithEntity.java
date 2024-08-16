package io.github.bioplethora.entity.creatures;

import io.github.bioplethora.entity.FloatingMonsterEntity;
import io.github.bioplethora.entity.IBioClassification;
import io.github.bioplethora.entity.ai.gecko.GeckoMeleeGoal;
import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes;

public class TerraithEntity extends FloatingMonsterEntity implements IAnimatable, FlyingAnimal, IBioClassification {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public TerraithEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.moveControl = new MoveHelperController(this);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.ATTACK_SPEED, 10.5)
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ATTACK_KNOCKBACK, 0.4)
                .add(Attributes.FLYING_SPEED, 5)
                .add(Attributes.FOLLOW_RANGE, 32);
    }

    @Override
    public BPEntityClasses getBioplethoraClass() {
        return BPEntityClasses.DANGERUM;
    }

    @Override
    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.2));
        this.goalSelector.addGoal(4, new TerraithEntity.MoveRandomGoal());
        this.goalSelector.addGoal(3, new TerraithEntity.ChargeAttackGoal());
        this.goalSelector.addGoal(1, new GeckoMeleeGoal<>(this, 20, 0.6, 0.7));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, TerraithEntity.class)).setAlertOthers());
    }

    public void move(MoverType pType, Vec3 pPos) {
        super.move(pType, pPos);
        this.checkInsideBlocks();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "terraith_controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    private <E extends IAnimatable>PlayState predicate(AnimationState<E> event) {
        if(this.getAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.terraith.attack", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.terraith.move", EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.terraith.idle"));
        return PlayState.CONTINUE;
    }

	@Override
	public boolean isFlying() {
		return false;
	}
}
