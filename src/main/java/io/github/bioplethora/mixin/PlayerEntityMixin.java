package io.github.bioplethora.mixin;

import io.github.bioplethora.api.mixin.IPlayerEntityMixin;
import io.github.bioplethora.item.weapons.BellophiteShieldItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.HandSide;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IPlayerEntityMixin {

    //===========================================
    //             VARIABLES
    //===========================================

    @Shadow @Final public PlayerInventory inventory;
    @Shadow @Final protected static final DataParameter<Byte> DATA_PLAYER_MAIN_HAND = EntityDataManager.defineId(PlayerEntity.class, DataSerializers.BYTE);

    @Shadow public abstract CooldownTracker getCooldowns();

    private static final DataParameter<Boolean> ALPHANUM_CURSE = EntityDataManager.defineId(PlayerEntity.class, DataSerializers.BOOLEAN);

    //===========================================
    //          DUMMY CONSTRUCTOR
    //===========================================

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    //===========================================
    //           MIXIN SECTION
    //===========================================

    @Inject(at = @At("TAIL"), method = ("Lnet/minecraft/entity/player/PlayerEntity;defineSynchedData()V"))
    protected void defineSynchedData(CallbackInfo cbi) {
        this.entityData.define(ALPHANUM_CURSE, false);
    }

    @Inject(at = @At("TAIL"), method = ("disableShield"), cancellable = true)
    public void disableShield(boolean b, CallbackInfo ci) {
        if (this.getUseItem().getItem() instanceof BellophiteShieldItem) {
            this.level.broadcastEntityEvent(this, (byte) 30);
            ci.cancel();
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

    //===============================================
    //          DUMMY INTERFACE METHODS
    //===============================================

    public Iterable<ItemStack> getArmorSlots() {
        return this.inventory.armor;
    }

    public ItemStack getItemBySlot(EquipmentSlotType pSlot) {
        if (pSlot == EquipmentSlotType.MAINHAND) {
            return this.inventory.getSelected();
        } else if (pSlot == EquipmentSlotType.OFFHAND) {
            return this.inventory.offhand.get(0);
        } else {
            return pSlot.getType() == EquipmentSlotType.Group.ARMOR ? this.inventory.armor.get(pSlot.getIndex()) : ItemStack.EMPTY;
        }
    }

    public void setItemSlot(EquipmentSlotType pSlot, ItemStack pStack) {
        if (pSlot == EquipmentSlotType.MAINHAND) {
            this.playEquipSound(pStack);
            this.inventory.items.set(this.inventory.selected, pStack);
        } else if (pSlot == EquipmentSlotType.OFFHAND) {
            this.playEquipSound(pStack);
            this.inventory.offhand.set(0, pStack);
        } else if (pSlot.getType() == EquipmentSlotType.Group.ARMOR) {
            this.playEquipSound(pStack);
            this.inventory.armor.set(pSlot.getIndex(), pStack);
        }
    }

    public HandSide getMainArm() {
        return this.entityData.get(DATA_PLAYER_MAIN_HAND) == 0 ? HandSide.LEFT : HandSide.RIGHT;
    }
}
