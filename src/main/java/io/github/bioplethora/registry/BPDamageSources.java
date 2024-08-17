package io.github.bioplethora.registry;

import javax.annotation.Nullable;

import io.github.bioplethora.Bioplethora;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BPDamageSources {
	
    public static final ResourceKey<DamageType> CASTRATION = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Bioplethora.MOD_ID, "castration"));
    public static final ResourceKey<DamageType> HELIO_SLASHED = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Bioplethora.MOD_ID, "helio_slashed"));
    public static final ResourceKey<DamageType> ANTI_BIO = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Bioplethora.MOD_ID, "anti_bio"));
    public static final ResourceKey<DamageType> ARMOR_PIERCING = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Bioplethora.MOD_ID, "armor_piercing"));
    public static final ResourceKey<DamageType> FLEIGNARITE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Bioplethora.MOD_ID, "fleignarite"));

    public static DamageSource indirectCastration(Entity entity, @Nullable Entity source) {
        return new BioplethoraIndirectDamageSource(entity.level.registryAccess().registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(CASTRATION), entity, source);
    }

    public static DamageSource helioSlashed(Entity entity, @Nullable Entity source) {
        return new BioplethoraIndirectDamageSource(entity.level.registryAccess().registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(HELIO_SLASHED), entity, source);
    }

    public static DamageSource antibio(Entity entity, @Nullable Entity source) {
        return new BioplethoraIndirectDamageSource(entity.level.registryAccess().registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(ANTI_BIO), entity, source);
    }

    public static DamageSource armorPiercingWeapon(Entity entity) {
        return new DamageSource(entity.level.registryAccess().registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(ARMOR_PIERCING), entity, entity);
    }

    public static DamageSource armorPiercingFleignarite(Entity entity, @Nullable Entity source) {
        return new BioplethoraIndirectDamageSource(entity.level.registryAccess().registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(FLEIGNARITE), entity, source);
    }

    static class BioplethoraIndirectDamageSource extends DamageSource {

		Entity indirectOwner;
		
        public BioplethoraIndirectDamageSource(Holder<DamageType> pType, Entity pDirectEntity, Entity pCausingEntity) {
			super(pType, pDirectEntity, pCausingEntity);
			this.indirectOwner = pDirectEntity;
		}

        @Override
        public Component getLocalizedDeathMessage(LivingEntity livingEntity) {
            LivingEntity killCredit = (LivingEntity) this.indirectOwner;
            String s = "death.attack." + this.getMsgId();
            int variant = livingEntity.getRandom().nextInt(2);
            String s1 = s + "." + variant;
            String s2 = s + ".indirect_" + variant;
            return killCredit != null ? Component.translatable(s2, livingEntity.getDisplayName(), killCredit.getDisplayName()) : Component.translatable(s1, livingEntity.getDisplayName());
        }
    }
}
