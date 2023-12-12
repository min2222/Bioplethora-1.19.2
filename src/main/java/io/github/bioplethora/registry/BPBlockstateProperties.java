package io.github.bioplethora.registry;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class BPBlockstateProperties {
    public static final IntegerProperty MINISHROOMS = IntegerProperty.create("minishrooms", 1, 3);
    public static final BooleanProperty BUDDED = BooleanProperty.create("budded");
    public static final BooleanProperty NUCLEUS_ACTIVATED = BooleanProperty.create("nucleus_activated");
}
