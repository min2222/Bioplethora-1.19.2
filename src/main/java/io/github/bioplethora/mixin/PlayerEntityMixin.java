package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.bioplethora.api.mixin.IPlayerEntityMixin;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IPlayerEntityMixin {

    //===========================================
    //             VARIABLES
    //===========================================

    @Shadow @Final public Inventory inventory;
    @Shadow @Final protected static final EntityDataAccessor<Byte> DATA_PLAYER_MAIN_HAND = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BYTE);

    @Shadow public abstract ItemCooldowns getCooldowns();

    private static final EntityDataAccessor<Boolean> ALPHANUM_CURSE = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SCREEN_SHAKING = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);

    //===========================================
    //          DUMMY CONSTRUCTOR
    //===========================================

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, Level world) {
        super(type, world);
    }

    //===========================================
    //           MIXIN SECTION
    //===========================================

    @Inject(at = @At("TAIL"), method = ("Lnet/minecraft/world/entity/player/Player;defineSynchedData()V"))
    protected void defineSynchedData(CallbackInfo cbi) {
        this.entityData.define(ALPHANUM_CURSE, false);
        this.entityData.define(SCREEN_SHAKING, 0);
    }

    @Inject(at = @At("TAIL"), method = ("tick"))
    public void tick(CallbackInfo cbi) {
        if (!this.level.isClientSide()) {
            if (getScreenShaking() > 0) {
                setScreenShaking(getScreenShaking() - 1);
            }
        }
    }

    @Override
    public boolean hasAlphanumCurse() {
        return this.entityData.get(ALPHANUM_CURSE);
    }

    @Override
    public void setAlphanumCurse(boolean value) {
        this.entityData.set(ALPHANUM_CURSE, value);
    }

    @Override
    public int getScreenShaking() {
        return this.entityData.get(SCREEN_SHAKING);
    }

    @Override
    public void setScreenShaking(int value) {
        this.entityData.set(SCREEN_SHAKING, value);
    }

    //===============================================
    //          DUMMY INTERFACE METHODS
    //===============================================

    public Iterable<ItemStack> getArmorSlots() {
        return this.inventory.armor;
    }

    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        if (pSlot == EquipmentSlot.MAINHAND) {
            return this.inventory.getSelected();
        } else if (pSlot == EquipmentSlot.OFFHAND) {
            return this.inventory.offhand.get(0);
        } else {
            return pSlot.getType() == EquipmentSlot.Type.ARMOR ? this.inventory.armor.get(pSlot.getIndex()) : ItemStack.EMPTY;
        }
    }

    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
        if (pSlot == EquipmentSlot.MAINHAND) {
            this.playEquipSound(pStack);
            this.inventory.items.set(this.inventory.selected, pStack);
        } else if (pSlot == EquipmentSlot.OFFHAND) {
            this.playEquipSound(pStack);
            this.inventory.offhand.set(0, pStack);
        } else if (pSlot.getType() == EquipmentSlot.Type.ARMOR) {
            this.playEquipSound(pStack);
            this.inventory.armor.set(pSlot.getIndex(), pStack);
        }
    }
    
    protected void playEquipSound(ItemStack p_217042_) 
    {
    	if (!p_217042_.isEmpty() && !this.isSpectator() && Equipable.get(p_217042_) != null) {
    		Equipable equipable = Equipable.get(p_217042_);
    		SoundEvent soundevent = equipable.getEquipSound();
    		if (soundevent != null) {
    			this.playSound(soundevent, 1.0F, 1.0F);
    		}
        }
    }

    public HumanoidArm getMainArm() {
        return this.entityData.get(DATA_PLAYER_MAIN_HAND) == 0 ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
    }
}
