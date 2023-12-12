package io.github.bioplethora.item.weapons;

import java.util.List;

import javax.annotation.Nullable;

import io.github.bioplethora.api.BPItemSettings;
import io.github.bioplethora.api.world.EntityUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GrylynenShieldRedItem extends GrylynenShieldBaseItem {

    public GrylynenShieldRedItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getCooldownReduction() {
        return 20;
    }

    @Override
    public int getArmorBonus() {
        return 3;
    }

    @Override
    public void blockingSkill(ItemStack stack, LivingEntity user, Entity attacker, Level world) {
        if (EntityUtils.isLiving(attacker)) {
            EntityUtils.knockbackAwayFromUser(0.6F, user, (LivingEntity) attacker);
        }
        if (Math.random() <= 0.75) {
            user.heal(2F + user.getRandom().nextInt(1));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        BPItemSettings.sacredLevelText(tooltip);

        tooltip.add(Component.translatable("item.bioplethora.red_grylynen_shield.red_crystal_energy.skill").withStyle(BPItemSettings.SKILL_NAME_COLOR));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(Component.translatable("item.bioplethora.red_grylynen_shield.red_crystal_energy.desc").withStyle(BPItemSettings.SKILL_DESC_COLOR));
        }
    }
}
