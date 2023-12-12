package io.github.bioplethora.blocks.specific;

import io.github.bioplethora.entity.others.AltyrusSummoningEntity;
import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FrostbiteMetalCoreBlock extends Block {


    public FrostbiteMetalCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 15;
    }

    @Override
    public InteractionResult use(BlockState blockstate, Level world, BlockPos pos, Player entity, InteractionHand hand, BlockHitResult hit) {

        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        //center blocks
        BlockPos c1 = new BlockPos(x, y + 1, z);
        BlockPos c2 = new BlockPos(x, y - 1, z);
        //top layer
        BlockPos t1 = new BlockPos(x + 1, y + 1, z);
        BlockPos t2 = new BlockPos(x - 1, y + 1, z);
        BlockPos t3 = new BlockPos(x, y + 1, z + 1);
        BlockPos t4 = new BlockPos(x, y + 1, z - 1);
        BlockPos t5 = new BlockPos(x + 2, y + 1, z);
        BlockPos t6 = new BlockPos(x - 2, y + 1, z);
        BlockPos t7 = new BlockPos(x, y + 1, z + 2);
        BlockPos t8 = new BlockPos(x, y + 1, z - 2);
        BlockPos t9 = new BlockPos(x + 3, y + 1, z);
        BlockPos t10 = new BlockPos(x - 3, y + 1, z);
        BlockPos t11 = new BlockPos(x, y + 1, z + 3);
        BlockPos t12 = new BlockPos(x, y + 1, z - 3);
        //mid layer
        BlockPos m1 = new BlockPos(x + 3, y, z);
        BlockPos m2 = new BlockPos(x - 3, y, z);
        BlockPos m3 = new BlockPos(x, y, z + 3);
        BlockPos m4 = new BlockPos(x, y, z - 3);
        //bottom layer
        BlockPos b1 = new BlockPos(x + 3, y - 1, z);
        BlockPos b2 = new BlockPos(x - 3, y - 1, z);
        BlockPos b3 = new BlockPos(x, y - 1, z + 3);
        BlockPos b4 = new BlockPos(x, y - 1, z - 3);

        if (entity.getMainHandItem().getItem() == BPItems.BELLOPHITE.get()) {

            if (((world.getBlockState(c1)).getBlock() == BPBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(c2)).getBlock() == BPBlocks.BELLOPHITE_BLOCK.get()) &&
                    //top layer
                    ((world.getBlockState(t1)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t2)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t3)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t4)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t5)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t6)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t7)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t8)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(t9)).getBlock() == BPBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(t10)).getBlock() == BPBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(t11)).getBlock() == BPBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(t12)).getBlock() == BPBlocks.BELLOPHITE_BLOCK.get()) &&
                    //mid layer
                    ((world.getBlockState(m1)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(m2)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(m3)).getBlock() == Blocks.CHAIN) &&
                    ((world.getBlockState(m4)).getBlock() == Blocks.CHAIN) &&
                    //bottom layer
                    ((world.getBlockState(b1)).getBlock() == BPBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(b2)).getBlock() == BPBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(b3)).getBlock() == BPBlocks.BELLOPHITE_BLOCK.get()) &&
                    ((world.getBlockState(b4)).getBlock() == BPBlocks.BELLOPHITE_BLOCK.get())) {

                if (!entity.isCreative()) {
                    entity.getMainHandItem().shrink(1);
                }

                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos(x, y, z), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.NEUTRAL, (float) 1, (float) 1);
                    world.playSound(null, new BlockPos(x, y, z), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.NEUTRAL, (float) 1, (float) 1);
                    //main center
                    bDesBl(world, new BlockPos(x, y, z));
                    bDesBl(world, c1);bDesBl(world, c2);
                    //top layer
                    bDesBl(world, t1);bDesBl(world, t2);bDesBl(world, t3);
                    bDesBl(world, t4);bDesBl(world, t5);bDesBl(world, t6);
                    bDesBl(world, t7);bDesBl(world, t8);bDesBl(world, t9);
                    bDesBl(world, t10);bDesBl(world, t11);bDesBl(world, t12);
                    //mid layer
                    bDesBl(world, m1);bDesBl(world, m2);bDesBl(world, m3);bDesBl(world, m4);
                    //bottom layer
                    bDesBl(world, b1);bDesBl(world, b2);bDesBl(world, b3);bDesBl(world, b4);

                    long time = world.getLevelData().getDayTime();
                    ((ServerLevel) world).setWeatherParameters(0, (int) time, true, true);

                    ((ServerLevel) world).sendParticles(ParticleTypes.POOF, x, y, z, 40, 0.4, 0.4, 0.4, 0.1);
                    ServerLevel serverworld = (ServerLevel) world;
                    BlockPos blockpos = (new BlockPos(x, y, z));
                    AltyrusSummoningEntity altyrusSummoningEntity = BPEntities.ALTYRUS_SUMMONING.get().create(world);
                    altyrusSummoningEntity.moveTo(blockpos, 0.0F, 0.0F);

                    serverworld.addFreshEntity(altyrusSummoningEntity);

                    if (!entity.level.isClientSide()) {
                        entity.displayClientMessage(Component.literal("Summon successful!"), (false));
                    }
                }

            } else if (!entity.level.isClientSide()) {
                entity.displayClientMessage(Component.literal("Invalid structure, use the Biopedia to find the correct structure."), (false));
            }
        } else {
            if (!entity.level.isClientSide()) {
                entity.displayClientMessage(Component.literal("Invalid item for the altar, requires: FrostbiteMetal."), (false));
            }
        }

        return InteractionResult.SUCCESS;
    }
    
    public void bDesBl(Level world, BlockPos pos) {
        world.destroyBlock(pos, false);
    }
    
    public Block getBlockAt(Level world, BlockPos pos) {
        return world.getBlockState(pos).getBlock();
    }
}
