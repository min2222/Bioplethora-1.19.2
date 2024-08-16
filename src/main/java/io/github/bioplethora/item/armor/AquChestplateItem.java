package io.github.bioplethora.item.armor;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import io.github.bioplethora.api.world.EffectUtils;
import io.github.bioplethora.client.armor.render.AquChestplateRender;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AquChestplateItem extends ArmorItem implements GeoItem {

	private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("animation.aqu_chestplate.idle");
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public AquChestplateItem(ArmorMaterial materialIn, ArmorItem.Type slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {

        EffectUtils.addEffectNoIcon((LivingEntity) pEntity, MobEffects.MOVEMENT_SPEED, 10, 9);
        EffectUtils.addEffectNoIcon((LivingEntity) pEntity, MobEffects.JUMP, 10, 9);
        EffectUtils.addEffectNoIcon((LivingEntity) pEntity, MobEffects.SATURATION, 10, 254);

        super.inventoryTick(pStack, pLevel, pEntity, pItemSlot, pIsSelected);
    }

    private <P extends GeoItem> PlayState predicate(AnimationState<P> event) {
        event.getController().setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "controller", 20, this::predicate));
    }
    
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    	consumer.accept(new IClientItemExtensions() {
    		private GeoArmorRenderer<?> renderer;

			@Override
			public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				if (this.renderer == null)
					this.renderer = new AquChestplateRender();

				// This prepares our GeoArmorRenderer for the current render frame.
				// These parameters may be null however, so we don't do anything further with them
				this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

				return this.renderer;
			}
		});
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }
}
