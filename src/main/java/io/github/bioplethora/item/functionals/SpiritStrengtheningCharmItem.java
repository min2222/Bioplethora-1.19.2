package io.github.bioplethora.item.functionals;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.registry.BPEffects;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpiritStrengtheningCharmItem extends ActivatableItem {

    public SpiritStrengtheningCharmItem(Properties properties) {
        super(properties, true);
    }

    @Override
    public void activatedTick(ItemStack pStack, Level pLevel, Entity pEntity) {
        super.activatedTick(pStack, pLevel, pEntity);

        if (pEntity instanceof Player) {
            ((Player) pEntity).addEffect(new MobEffectInstance(BPEffects.SPIRIT_STRENGTHENING.get(), 10, 0, false, false));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.spirit_strengthening_charm.spirit_strengthening.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.spirit_strengthening_charm.spirit_strengthening.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
