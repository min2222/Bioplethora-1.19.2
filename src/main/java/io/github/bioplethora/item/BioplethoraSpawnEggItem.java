package io.github.bioplethora.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.RegistryObject;

public class BioplethoraSpawnEggItem extends ForgeSpawnEggItem {

    protected static final List<BioplethoraSpawnEggItem> UNADDED_EGGS = new ArrayList<>();
    private final Lazy<? extends EntityType<?>> entityTypeSupplier;
    private final BPEntityClasses entityClass;

    public BioplethoraSpawnEggItem(final RegistryObject<? extends EntityType<? extends Mob>> entityTypeSupplier, BPEntityClasses entityClass, Properties properties) {
        super(entityTypeSupplier, 0xFFFFFFF, 0xFFFFFFF, properties);
        this.entityTypeSupplier = Lazy.of(entityTypeSupplier);
        this.entityClass = entityClass;
        UNADDED_EGGS.add(this);
    }

    public static void initUnaddedEggs() {
        final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "f_43201_");
        DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior() {

            @Override
            public ItemStack execute(BlockSource source, ItemStack stack) {
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                entitytype.spawn(source.getLevel(), stack, null, source.getPos().relative(direction),
                		MobSpawnType.DISPENSER, direction != Direction.UP, false);
                stack.shrink(1);
                return stack;
            }
        };
        for (final SpawnEggItem egg : UNADDED_EGGS) {
            EGGS.put(egg.getType(null), egg);
            DispenserBlock.registerBehavior(egg, defaultDispenseItemBehavior);
        }
        UNADDED_EGGS.clear();
    }

    @Override
    public EntityType<?> getType(@Nullable final CompoundTag p_208076_1_) {
        return entityTypeSupplier.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("item.bioplethora." + bpEntityClass() + "_spawn_egg.desc").withStyle(bpClassColor()));
    }

    public String bpEntityClass() {
        if (getEntityClass(BPEntityClasses.ECOHARMLESS)) {
            return "ecoharmless";
        } else if (getEntityClass(BPEntityClasses.PLETHONEUTRAL)) {
            return "plethoneutral";
        } else if (getEntityClass(BPEntityClasses.DANGERUM)) {
            return "dangerum";
        } else if (getEntityClass(BPEntityClasses.HELLSENT)) {
            return "hellsent";
        } else if (getEntityClass(BPEntityClasses.ELDERIA)) {
            return "elderia";
        } else {
            return "none";
        }
    }

    public ChatFormatting bpClassColor() {
        if (getEntityClass(BPEntityClasses.ECOHARMLESS)) {
            return ChatFormatting.GREEN;
        } else if (getEntityClass(BPEntityClasses.PLETHONEUTRAL)) {
            return ChatFormatting.YELLOW;
        } else if (getEntityClass(BPEntityClasses.DANGERUM)) {
            return ChatFormatting.RED;
        } else if (getEntityClass(BPEntityClasses.HELLSENT)) {
            return ChatFormatting.LIGHT_PURPLE;
        } else if (getEntityClass(BPEntityClasses.ELDERIA)) {
            return ChatFormatting.AQUA;
        } else {
            return ChatFormatting.WHITE;
        }
    }

    public boolean getEntityClass(BPEntityClasses entityClass) {
        return this.entityClass == entityClass;
    }
}
