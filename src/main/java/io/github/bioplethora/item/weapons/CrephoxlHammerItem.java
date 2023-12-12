package io.github.bioplethora.item.weapons;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.api.world.EffectUtils;
import io.github.bioplethora.api.world.EntityUtils;
import io.github.bioplethora.api.world.ItemUtils;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrephoxlHammerItem extends AxeItem {

    public CrephoxlHammerItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    /** <h2>Special Ability 1 of 2: Deathsweep</h2>
     *
     * Hitting an entity while crouching will deal 80% of this tool's base damage to nearby entities within
     a 2-block radius. 1.5 second cooldown.
     */
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        boolean retval = super.hurtEnemy(stack, entity, source);
        Level world = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();
        BlockPos pos = new BlockPos(x, y, z);
        Player player = (Player) source;

        if(player.isCrouching() && ItemUtils.checkCooldownUsable(entity, stack)) {
            ItemUtils.setStackOnCooldown(entity, stack, 30, true);

            entity.playSound(SoundEvents.ANVIL_PLACE, 1, 1);
            entity.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1, 1);

            if(!world.isClientSide) {
                world.addParticle(ParticleTypes.SWEEP_ATTACK, x, y, z, 0, 0, 0);
                for (LivingEntity entityIterator : EntityUtils.getEntitiesInArea(LivingEntity.class, world, pos, 5, 2)) {
                    if(Math.random() < 0.33) {
                        EffectUtils.addEffectNoIcon(entityIterator, MobEffects.MOVEMENT_SLOWDOWN, 60, 4);
                    }
                    if(entityIterator != entity) {
                        entityIterator.hurt(DamageSource.mobAttack(player), this.getAttackDamage() * 0.8F);
                    }
                }
            }
        }

        //Adds debuffs to target after hit.
        EffectUtils.addEffectNoIcon(entity, MobEffects.MOVEMENT_SLOWDOWN, 5, 2);
        EffectUtils.addEffectNoIcon(entity, MobEffects.WEAKNESS, 4, 1);
        EffectUtils.addEffectNoIcon(entity, MobEffects.DIG_SLOWDOWN, 3, 1);
        EffectUtils.addEffectNoIcon(entity, MobEffects.CONFUSION, 5, 1);

        //Deals more damage to Entities over 50 max health.
        if (entity.getMaxHealth() >= 50) {
            entity.hurt(DamageSource.mobAttack(entity), getAttackDamage() * 2);
        }

        return retval;
    }

    /** <h2>Special Ability 2 of 2: Aerial Shockwave</h2>
     *
     * Create a damaging shockwave on block right-click position, dealing 9 damage to
     nearby entities & sending them flying into the air. 3-second cooldown.
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player entity = context.getPlayer();
        InteractionHand hand = context.getHand();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();

        double x = pos.getX(), y = pos.getY(), z = pos.getZ();
        if(!entity.isInWater()) {
            ItemUtils.setStackOnCooldown(entity, stack, 60, true);
            world.playSound(entity, pos, SoundEvents.WITHER_BREAK_BLOCK, SoundSource.PLAYERS, 1, 1);
            entity.swing(hand);

            BPEffectEntity effect = BPEntities.BP_EFFECT.get().create(entity.level);
            effect.setEffectType(BPEffectTypes.AERIAL_SHOCKWAVE);
            effect.moveTo(entity.blockPosition(), entity.yBodyRot, 0.0F);
            entity.level.addFreshEntity(effect);
            BlockUtils.knockUpRandomNearbyBlocks(world, 0.35D, entity.blockPosition().below(), 4, 3, 4, false, true);

            if (!world.isClientSide()) {
                ((ServerLevel)world).sendParticles(ParticleTypes.CLOUD, x, y + 1.2, z, 50, 3, 0.2, 3, 0);
            }

            for (Entity entityIterator : world.getEntitiesOfClass(Entity.class, new AABB(x - (7 / 2d), y - (3 / 2d), z - (7 / 2d), x + (4 / 2d), y + (4 / 2d), z + (4 / 2d)), null)) {
                if (entityIterator instanceof LivingEntity && entityIterator != entity) {
                    entityIterator.hurt(DamageSource.mobAttack(entity), 9.0F);
                    entityIterator.setDeltaMovement((entity.getDeltaMovement().x()), 1, (entity.getDeltaMovement().z()));
                }
            }

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.FAIL;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.crephoxl_hammer.dysfunction.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.crephoxl_hammer.dysfunction.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(Component.translatable("item.bioplethora.crephoxl_hammer.deathsweep.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.crephoxl_hammer.deathsweep.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(Component.translatable("item.bioplethora.crephoxl_hammer.aerial_shockwave.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.crephoxl_hammer.aerial_shockwave.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
