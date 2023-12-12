package io.github.bioplethora.item.weapons;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.IReachWeapon;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class StellarScytheItem extends SwordItem implements IReachWeapon {

    public StellarScytheItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties properties) {
        super(tier, attackDamageIn, attackSpeedIn, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        boolean retval = super.hurtEnemy(stack, entity, source);

        Level world = entity.level;
        double x = entity.getX(), y = entity.getY(), z = entity.getZ();
        BlockPos pos = new BlockPos(x, y, z);
        Player player = (Player) source;

        if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
            player.getCooldowns().addCooldown(stack.getItem(), 20);
            world.playSound(null, pos, SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1, 1);
            if(!world.isClientSide) {
                double d0 = -Mth.sin(player.yRot * ((float)Math.PI / 180F));
                double d1 = Mth.cos(player.yRot * ((float)Math.PI / 180F));
                ((ServerLevel)world).sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
            }
            if(world instanceof ServerLevel) {
                for (Entity entityIterator : world.getEntitiesOfClass(Entity.class, player.getBoundingBox().inflate(2D, 1D, 2D))) {
                    if (entityIterator instanceof LivingEntity && entityIterator != player) {
                        if(entityIterator != entity) {
                            entityIterator.hurt(DamageSource.mobAttack(player), (this.getDamage() * 0.8F) * EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, source));
                        }
                    }
                }
            }
        }
        return retval;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.stellar_scythe.radius_slash.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.stellar_scythe.radius_slash.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }

    @Override
    public double getReachDistance() {
        return 7.5;
    }
}
