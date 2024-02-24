package io.github.bioplethora.config;

import java.io.File;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraftforge.common.ForgeConfigSpec;

public class BPConfig {

    public static ForgeConfigSpec.Builder COMMON_BUILDER;
    public static ForgeConfigSpec COMMON_SPEC;
    public static BPCommonConfig COMMON;
    public static ForgeConfigSpec.Builder WORLDGEN_BUILDER;
    public static ForgeConfigSpec WORLDGEN_SPEC;
    public static BPWorldgenConfig WORLDGEN;
    
    static 
    {
    	COMMON_BUILDER = new ForgeConfigSpec.Builder();
    	COMMON = new BPCommonConfig(COMMON_BUILDER);
    	COMMON_SPEC = COMMON_BUILDER.build();
    	
    	WORLDGEN_BUILDER = new ForgeConfigSpec.Builder();
    	WORLDGEN = new BPWorldgenConfig(WORLDGEN_BUILDER);
    	WORLDGEN_SPEC = WORLDGEN_BUILDER.build();
    }
    
    public static void loadConfig(ForgeConfigSpec config, String path) 
    {
        CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }

    //FIXME crash on start
    public static final boolean IN_HELLMODE = false; //BPConfig.COMMON.hellMode.get();
}
