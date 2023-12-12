package io.github.bioplethora.blocks.tile_entities;

import io.github.bioplethora.api.mixin.IPlayerEntityMixin;
import io.github.bioplethora.api.world.BlockUtils;
import io.github.bioplethora.entity.creatures.AlphemKingEntity;
import io.github.bioplethora.entity.others.BPEffectEntity;
import io.github.bioplethora.enums.BPEffectTypes;
import io.github.bioplethora.network.BPNetwork;
import io.github.bioplethora.network.functions.NucleusActivatePacket;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPParticles;
import io.github.bioplethora.registry.BPTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AlphanumNucleusTileEntity extends BlockEntity {
    public int activeTicks;
    public int summonCounter;
    
    public AlphanumNucleusTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public AlphanumNucleusTileEntity(BlockPos pos, BlockState state) {
        this(BPTileEntities.ALPHANUM_NUCLEUS.get(), pos, state);
    }

    public static void tick(Level p_155014_, BlockPos p_155015_, BlockState p_155016_, AlphanumNucleusTileEntity p_155017_) {
        if (p_155016_.getValue(AlphanumNucleusBlock.ACTIVATED)) {
            ++p_155017_.activeTicks;
            BPNetwork.CHANNEL.sendToServer(new NucleusActivatePacket(p_155015_.getX(), p_155015_.getY(), p_155015_.getZ()));

            int areaint = 15;
            AABB aabb = new AABB(p_155015_.getX() - areaint, p_155015_.getY() - areaint, p_155015_.getZ() - areaint, p_155015_.getX() + areaint, p_155015_.getY() + areaint, p_155015_.getZ() + areaint);
            for (Player areaEnt : p_155014_.getEntitiesOfClass(Player.class, aabb)) {
                ((IPlayerEntityMixin) areaEnt).setScreenShaking(5);
            }

            if (!p_155014_.isClientSide()) {
                ((ServerLevel) p_155014_).sendParticles(ParticleTypes.SOUL_FIRE_FLAME, p_155015_.getX(), p_155015_.getY() + 0.5D, p_155015_.getZ(), 7, 3.5, 3.5, 3.5, 0.1);

                Vec3 from = new Vec3(p_155015_.getX(), p_155015_.getY(), p_155015_.getZ());
                Vec3 to = new Vec3(p_155015_.getX() + (-40 + p_155014_.getRandom().nextInt(80)), 255, p_155015_.getZ() + (-40 + p_155014_.getRandom().nextInt(80)));
                Vec3 per = to.subtract(from).normalize();
                Vec3 current = from.add(0, 0, 0);
                double distance = from.distanceTo(to);
                for (double i = 0; i < distance; i++) {
                    ((ServerLevel) p_155014_).sendParticles(ParticleTypes.POOF, current.x(), current.y(), current.z(), 1, 0, 0, 0, 0);
                    current = current.add(per);
                }
            }

            if (p_155017_.activeTicks >= 20) {
                ++p_155017_.summonCounter;
                if (!p_155014_.isClientSide()) {
                    ((ServerLevel) p_155014_).sendParticles(BPParticles.KINGS_ROAR.get(), p_155015_.getX(), p_155015_.getY() + 0.5D, p_155015_.getZ(), 1, 0, 0, 0, 0);
                }
                p_155017_.activeTicks = 0;
            }

            if (p_155017_.summonCounter >= 5) {
                AlphemKingEntity king = new AlphemKingEntity(BPEntities.ALPHEM_KING.get(), p_155014_);
                king.moveTo(p_155015_, 30.0F, 30.0F);
                king.setBarriered(true);
                if (!p_155014_.isClientSide()) {
                    ((ServerLevel) p_155014_).sendParticles(ParticleTypes.EXPLOSION, p_155015_.getX(), p_155015_.getY() + 0.5D, p_155015_.getZ(), 15, 1.25, 1.25, 1.25, 0.1);
                    king.finalizeSpawn((ServerLevel) p_155014_, p_155014_.getCurrentDifficultyAt(p_155015_), MobSpawnType.MOB_SUMMONED, null, null);
                    BlockUtils.destroyAllNearbyBlocks(p_155014_, p_155015_, 3, 2, 3, false);
                    p_155017_.constructArena();
                }
                p_155014_.addFreshEntity(king);

                BPEffectEntity effect = BPEntities.BP_EFFECT.get().create(p_155014_);
                effect.setEffectType(BPEffectTypes.ALPHANUM_FIRE);
                effect.setOwner(king);
                effect.moveTo(p_155015_.getX(), p_155015_.getY(), p_155015_.getZ());
                p_155014_.addFreshEntity(effect);

                king.playSound(SoundEvents.FIREWORK_ROCKET_LARGE_BLAST, 1.0F, 1.0F);
            }
        }
    }

    public void constructArena() {
        int genRad = 32;
        this.createPart(genRad, -1, BPBlocks.ALPHANUM.get());
        this.createPart(genRad + 2, 0, BPBlocks.ALPHANUM.get());
        for (int i = 1; i < 16; i++) {
            if (i == 13 || i == 15) {
                this.createPart((genRad + 2) + i, i, Blocks.GREEN_STAINED_GLASS);
            } else if (i == 14) {
                this.createPart((genRad + 2) + i, i, Blocks.GLOWSTONE);
            } else {
                this.createPart((genRad + 2) + i, i, BPBlocks.ALPHANUM_BRICKS.get());
            }
            this.createPart((genRad + 1) + i, i, Blocks.AIR);
        }
        createColumnGroup(24);
    }

    public void createColumnGroup(int spreadValue) {
        createColumn((spreadValue - 8), 0);
        createColumn(-(spreadValue - 8), 0);
        createColumn(0, (spreadValue - 8));
        createColumn(0, -(spreadValue - 8));

        createColumn(spreadValue, -spreadValue);
        createColumn(-spreadValue, spreadValue);
        createColumn(-spreadValue, spreadValue);
        createColumn(spreadValue, -spreadValue);
        createColumn(spreadValue, spreadValue);
        createColumn(-spreadValue, -spreadValue);
    }

    public void createColumn(int xOff, int zOff) {
        for (int sy = 1; sy <= 24; sy++) {
            BlockPos.MutableBlockPos newPos = getBlockPos().offset(xOff, sy - 4, zOff).mutable();
            if (sy == 6 || sy == 8) {
                getLevel().setBlock(newPos, Blocks.GREEN_STAINED_GLASS.defaultBlockState(), 2);
            } else if (sy == 7) {
                getLevel().setBlock(newPos,  Blocks.GLOWSTONE.defaultBlockState(), 2);
            } else {
                getLevel().setBlock(newPos, BPBlocks.ALPHANUM_PILLAR.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y), 2);
            }
        }
    }

    public void createPart(int rad, int yOffset, Block block) {
        for (int sx = -rad; sx <= rad; sx++) {
            for (int sz = -rad; sz <= rad; sz++) {
                if (sx * sx + sz * sz <= rad * rad) {
                    BlockPos.MutableBlockPos newPos = getBlockPos().offset(sx, yOffset - 4, sz).mutable();
                    getLevel().setBlock(newPos, block.defaultBlockState(), 2);
                }
            }
        }
    }
}
