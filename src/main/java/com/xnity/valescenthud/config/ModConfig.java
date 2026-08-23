package com.xnity.valescenthud.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ModConfig {
    public static final ModConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;
    public static ModConfigSpec.IntValue underlaySizeX;
    public static ModConfigSpec.IntValue underlaySizeY;
    public static ModConfigSpec.IntValue overlaySizeX;
    public static ModConfigSpec.IntValue overlaySizeY;
    public static ModConfigSpec.BooleanValue enableUnderlay;
    public static ModConfigSpec.IntValue underlayOffsetX;
    public static ModConfigSpec.IntValue underlayOffsetY;
    public static ModConfigSpec.BooleanValue enableOverlay;
    public static ModConfigSpec.IntValue overlayOffsetX;
    public static ModConfigSpec.IntValue overlayOffsetY;
    public static ModConfigSpec.BooleanValue enableLevelOffset;
    public static ModConfigSpec.IntValue levelOffsetX;
    public static ModConfigSpec.IntValue levelOffsetY;
    public static ModConfigSpec.BooleanValue enableOffhandOffset;
    public static ModConfigSpec.IntValue offhandOffsetX;
    public static ModConfigSpec.IntValue offhandOffsetY;

    private ModConfig(ModConfigSpec.Builder builder) {
        enableUnderlay = builder
                .translation("valescenthud.config.enableUnderlay")
                .define("enableUnderlay", false);
        underlaySizeX = builder
                .comment("Set the width of the hotbar underlay.")
                .translation("valescenthud.config.underlaySizeX")
                .defineInRange("underlaySizeX", 256, 182, 1024);
        underlaySizeY = builder
                .comment("Set the height of the hotbar underlay.")
                .translation("valescenthud.config.underlaySizeY")
                .defineInRange("underlaySizeY", 96, 22, 1024);
        underlayOffsetX = builder
                .comment("Set the horizontal offset for the hotbar underlay.")
                .translation("valescenthud.config.underlayOffsetX")
                .defineInRange("underlayOffsetX", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        underlayOffsetY = builder
                .comment("Set the vertical offset for the hotbar underlay.")
                .translation("valescenthud.config.underlayOffsetY")
                .defineInRange("underlayOffsetY", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        enableOverlay = builder
                .translation("valescenthud.config.enableOverlay")
                .define("enableOverlay", false);
        overlaySizeX = builder
                .comment("Set the width of the hotbar overlay.")
                .translation("valescenthud.config.overlaySizeX")
                .defineInRange("overlaySizeX", 256, 182, 1024);
        overlaySizeY = builder
                .comment("Set the height of the hotbar overlay.")
                .translation("valescenthud.config.overlaySizeY")
                .defineInRange("overlaySizeY", 96, 22, 1024);
        overlayOffsetX = builder
                .comment("Set the horizontal offset for the hotbar overlay.")
                .translation("valescenthud.config.overlayOffsetX")
                .defineInRange("overlayOffsetX", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        overlayOffsetY = builder
                .comment("Set the vertical offset for the hotbar overlay.")
                .translation("valescenthud.config.overlayOffsetY")
                .defineInRange("overlayOffsetY", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        enableLevelOffset = builder
                .translation("valescenthud.config.enableLevelOffset")
                .define("enableLevelOffset", false);
        levelOffsetX = builder
                .comment("Set the horizontal offset for the level number.")
                .translation("valescenthud.config.levelOffsetX")
                .defineInRange("levelOffsetX", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        levelOffsetY = builder
                .comment("Set the vertical offset for the level number.")
                .translation("valescenthud.config.levelOffsetY")
                .defineInRange("levelOffsetY", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        enableOffhandOffset = builder
                .comment("Enable offsetting the offhand gui.")
                .translation("valescenthud.config.enableOffhandOffset")
                .define("enableOffhandOffset", false);
        offhandOffsetX = builder
                .comment("Set the horizontal offset for the offhand hud. Values are flipped with the opposite main hand.")
                .translation("valescenthud.config.offhandOffsetX")
                .defineInRange("offhandOffsetX", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        offhandOffsetY = builder
                .comment("Set the vertical offset for the offhand hud.")
                .translation("valescenthud.config.offhandOffsetY")
                .defineInRange("offhandOffsetY", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    static {
        Pair<ModConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(ModConfig::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
