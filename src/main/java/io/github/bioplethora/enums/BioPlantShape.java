package io.github.bioplethora.enums;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public enum BioPlantShape {
    SIMPLE_PLANT(
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D)
    ),
    MINISHROOM(
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D)
    ),
    BIG_MUSHROOM(
            Shapes.or(
                    Block.box(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D),
                    Block.box(1.0D, 6.0D, 1.0D, 15.0D, 15.0D, 15.0D)
            )
    ),
    IDE_FAN(
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D)
    );

    private final VoxelShape shape;

    BioPlantShape(VoxelShape shape) {
        this.shape = shape;
    }

    public VoxelShape getShape() {
        return shape;
    }
}
