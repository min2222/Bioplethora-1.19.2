package io.github.bioplethora.registry;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.blocks.tile_entities.AlphanumNucleusTileEntity;
import io.github.bioplethora.blocks.tile_entities.BPSignTileEntity;
import io.github.bioplethora.blocks.tile_entities.FleignariteSplotchTileEntity;
import io.github.bioplethora.blocks.tile_entities.ReinforcingTableTileEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BPTileEntities {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Bioplethora.MOD_ID);

    public static final RegistryObject<BlockEntityType<BPSignTileEntity>> BP_SIGN = TILE_ENTITIES.register("bp_sign",
            () -> BlockEntityType.Builder.of(BPSignTileEntity::new,
                    BPBlocks.ENIVILE_SIGN.get(), BPBlocks.ENIVILE_WALL_SIGN.get(),
                    BPBlocks.CAERULWOOD_SIGN.get(), BPBlocks.CAERULWOOD_WALL_SIGN.get()
            ).build(null));

    public static final RegistryObject<BlockEntityType<ReinforcingTableTileEntity>> REINFORCING_TABLE = TILE_ENTITIES.register("reinforcing_block",
            () -> BlockEntityType.Builder.of(ReinforcingTableTileEntity::new, BPBlocks.REINFORCING_TABLE.get()).build(null));

    public static final RegistryObject<BlockEntityType<FleignariteSplotchTileEntity>> FLEIGNARITE_SPLOTCH = TILE_ENTITIES.register("fleignarite_splotch",
            () -> BlockEntityType.Builder.of(FleignariteSplotchTileEntity::new, BPBlocks.FLEIGNARITE_SPLOTCH.get()).build(null));

    public static final RegistryObject<BlockEntityType<AlphanumNucleusTileEntity>> ALPHANUM_NUCLEUS = TILE_ENTITIES.register("alphanum_nucleus",
            () -> BlockEntityType.Builder.of(AlphanumNucleusTileEntity::new, BPBlocks.ALPHANUM_NUCLEUS.get()).build(null));
}
