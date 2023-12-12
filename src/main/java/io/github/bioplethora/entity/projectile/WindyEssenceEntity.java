package io.github.bioplethora.entity.projectile;

import io.github.bioplethora.registry.BPItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class WindyEssenceEntity extends EyeOfEnder {

    public WindyEssenceEntity(EntityType<? extends EyeOfEnder> type, Level world) {
        super(type, world);
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(BPItems.WINDY_ESSENCE.get()) : itemstack;
    }
}
