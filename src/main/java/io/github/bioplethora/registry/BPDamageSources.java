package io.github.bioplethora.registry;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BPDamageSources {

    public static DamageSource indirectCastration(Entity entity, @Nullable Entity source) {
        return new BioplethoraIndirectDamageSource("indirectCastration", entity, source).bypassArmor().setMagic().setExplosion();
    }

    public static DamageSource helioSlashed(Entity entity, @Nullable Entity source) {
        return new BioplethoraIndirectDamageSource("helioSlashed", entity, source);
    }

    public static DamageSource antibio(Entity entity, @Nullable Entity source) {
        return new BioplethoraIndirectDamageSource("antibio", entity, source).setMagic();
    }

    public static DamageSource armorPiercingWeapon(Entity entity) {
        return new IndirectEntityDamageSource("armorPiercingWeapon", entity, entity).bypassArmor();
    }

    public static DamageSource armorPiercingFleignarite(Entity entity, @Nullable Entity source) {
        return new BioplethoraIndirectDamageSource("armorPiercingFleignarite", entity, source).bypassArmor();
    }

    static class BioplethoraIndirectDamageSource extends EntityDamageSource {

        Entity indirectOwner;
        public BioplethoraIndirectDamageSource(String name, Entity entity, @Nullable Entity source) {
            super(name, entity);
            indirectOwner = entity;
        }

        @Nullable
        public Entity getDirectEntity() {
            return this.entity;
        }

        public Component getLocalizedDeathMessage(LivingEntity livingEntity) {
            LivingEntity killCredit = (LivingEntity) indirectOwner;
            String s = "death.attack." + this.msgId;
            int variant = livingEntity.getRandom().nextInt(2);
            String s1 = s + "." + variant;
            String s2 = s + ".indirect_" + variant;
            return killCredit != null ? Component.translatable(s2, livingEntity.getDisplayName(), killCredit.getDisplayName()) : Component.translatable(s1, livingEntity.getDisplayName());
        }
    }
}
