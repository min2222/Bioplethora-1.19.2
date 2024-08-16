package io.github.bioplethora.blocks.tile_entities;

import io.github.bioplethora.registry.BPTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class FleignariteSplotchTileEntity extends BlockEntity implements GeoBlockEntity {

	private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenPlay("animation.fleignarite_splotch.idle");
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public FleignariteSplotchTileEntity(BlockEntityType<?> type, BlockPos pPos, BlockState pBlockState) {
        super(type, pPos, pBlockState);
    }

    public FleignariteSplotchTileEntity(BlockPos pPos, BlockState pBlockState) {
        this(BPTileEntities.FLEIGNARITE_SPLOTCH.get(), pPos, pBlockState);
    }

    private <E extends GeoBlockEntity> PlayState predicate(AnimationState<E> event) {
        event.getController().setAnimation(IDLE_ANIM);
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "fleignarite_splotch_controller", 0, this::predicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }
}
