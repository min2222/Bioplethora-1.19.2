package io.github.bioplethora.item.weapons;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.entity.others.FrostbiteMetalShieldWaveEntity;
import io.github.bioplethora.registry.BPEntities;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class FrostbiteMetalShieldItem extends ShieldItem {

    public boolean isCharged;
    public int corePoints;

    public FrostbiteMetalShieldItem(Properties properties) {
        super(properties);
        this.corePoints = 0;
        this.isCharged = false;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
    	return toolAction == ToolActions.SHIELD_BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 125000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.BLOCK;
    }

    public void executeSkill(ItemStack stack, LivingEntity player, Level world) {

        double x = player.getX(), y = player.getY(), z = player.getZ();
        BlockPos pos = new BlockPos(x, y + 1, z);

        if (!((Player) player).getCooldowns().isOnCooldown(stack.getItem())) {

            if (!this.getIsCharged()) {
                if (!world.isClientSide) {
                    ((ServerLevel) world).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), x, y + 1.3, z, 25, 1, 1, 1, 0.1);
                }
                world.playSound(null, pos, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1, 1);
                this.addCorePoints(1);
            }

            if (this.getCorePoints() == 4) {
                this.addCorePoints(1);
                this.setIsCharged(true);
            }

            if (this.getIsCharged()) {
                world.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1, 1);

                FrostbiteMetalShieldWaveEntity shieldWave = BPEntities.BELLOPHITE_SHIELD_WAVE.get().create(player.level);
                shieldWave.setOwner(player);
                shieldWave.moveTo(pos, 0.0F, 0.0F);

                player.level.addFreshEntity(shieldWave);

                this.clearCorePoints();
                this.setIsCharged(false);

            }
        }
    }

    public void addCorePoints(int value) {
        this.corePoints = this.corePoints + value;
    }

    public void clearCorePoints() {
        this.corePoints = 0;
    }

    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 5, 2));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 5, 1));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.frostbite_metal_shield.recovery_bulwark.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.frostbite_metal_shield.recovery_bulwark.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }

        tooltip.add(Component.translatable("item.bioplethora.frostbite_metal_shield.core_impulse.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.frostbite_metal_shield.core_impulse.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }

    public boolean getIsCharged() {
        return this.isCharged;
    }

    public void setIsCharged(boolean value) {
        this.isCharged = value;
    }

    public int getCorePoints() {
        return this.corePoints;
    }

    public void setCorePoints(int value) {
        this.corePoints = value;
    }
}