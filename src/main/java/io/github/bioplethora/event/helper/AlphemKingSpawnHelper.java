package io.github.bioplethora.event.helper;

import java.util.List;

import io.github.bioplethora.blocks.tile_entities.AlphanumNucleusBlock;
import io.github.bioplethora.config.BPConfig;
import io.github.bioplethora.entity.projectile.WindArrowEntity;
import io.github.bioplethora.registry.BPBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;

public class AlphemKingSpawnHelper {

    public static void onProjectileImpact(ProjectileImpactEvent event) {
        Entity projectile = event.getEntity();
        Level world = projectile.level;
        HitResult result = event.getRayTraceResult();

        if (projectile instanceof WindArrowEntity) {
            WindArrowEntity windArrow = (WindArrowEntity) projectile;
            if (result instanceof BlockHitResult) {
                BlockHitResult blockResult = (BlockHitResult) result;
                BlockPos pos = blockResult.getBlockPos();

                if (world.getBlockState(blockResult.getBlockPos()).getBlock() == BPBlocks.ALPHANUM_NUCLEUS.get()) {

                    if (windArrow.getOwner() instanceof ServerPlayer) {
                        world.setBlock(pos, world.getBlockState(blockResult.getBlockPos()).setValue(AlphanumNucleusBlock.ACTIVATED, true), 2);

                        if (!world.isClientSide()) {
                            ((ServerLevel) world).sendParticles(ParticleTypes.CLOUD, windArrow.getX(), windArrow.getY(), windArrow.getZ(),
                                    30, 1.2, 1.2, 1.2, 0.1);

                            if (BPConfig.COMMON.announceAlphemKing.get()) {
                                List<ServerPlayer> list = ((ServerLevel) world).getPlayers((playerEntity) -> true);
                                for (ServerPlayer serverplayerentity : list) {
                                    serverplayerentity.displayClientMessage(Component.translatable("message.bioplethora.alphem_king.summon",
                                                    windArrow.getOwner().getDisplayName(), (float) windArrow.getX(), (float) windArrow.getY(), (float) windArrow.getZ())
                                                    .withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC),
                                            false);
                                }
                            }
                        }
                    }

                    windArrow.discard();
                }
            }
        }
    }
}
