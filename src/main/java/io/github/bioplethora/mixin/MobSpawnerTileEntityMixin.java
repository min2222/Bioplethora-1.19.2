package io.github.bioplethora.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(SpawnerBlockEntity.class)
public abstract class MobSpawnerTileEntityMixin extends BlockEntity {

    @Shadow @Final private BaseSpawner spawner;

    public MobSpawnerTileEntityMixin(BlockEntityType<?> p_i48289_1_, BlockPos pos, BlockState state) {
        super(p_i48289_1_, pos, state);
    }

    //TODO
    /*@Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (this.spawner.getSpawnerEntity() instanceof AlphemEntity) {
            int areaint = 32;
            AABB aabb = new AABB(
                    getBlockPos().getX() - areaint, getBlockPos().getY() - areaint, getBlockPos().getZ() - areaint,
                    getBlockPos().getX() + areaint, getBlockPos().getY() + areaint, getBlockPos().getZ() + areaint
            );

            if (level.getEntitiesOfClass(AlphemKingEntity.class, aabb).size() > 0) {
                level.destroyBlock(getBlockPos(), false);
            }
        }
    }*/
}
