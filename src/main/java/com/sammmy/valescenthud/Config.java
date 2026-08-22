package com.sammmy.valescenthud;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final Config CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;
    public final ModConfigSpec.BooleanValue enableUnderlay;
    public final ModConfigSpec.IntValue underlayOffsetX;
    public final ModConfigSpec.IntValue underlayOffsetY;
    public final ModConfigSpec.BooleanValue enableOverlay;
    public final ModConfigSpec.IntValue overlayOffsetX;
    public final ModConfigSpec.IntValue overlayOffsetY;
    public final ModConfigSpec.BooleanValue enableLevelOffset;
    public final ModConfigSpec.IntValue levelOffsetX;
    public final ModConfigSpec.IntValue levelOffsetY;
    public final ModConfigSpec.BooleanValue enableOffhandOffset;
    public final ModConfigSpec.IntValue offhandOffsetX;
    public final ModConfigSpec.IntValue offhandOffsetY;

    private Config(ModConfigSpec.Builder builder) {
        enableUnderlay = builder
                .comment("Enable rendering for the hotbar underlay")
                .translation("valescenthud.config.enableUnderlay")
                .define("enableUnderlay", false);
        underlayOffsetX = builder
                .comment("Set the horizontal offset for the hotbar underlay")
                .translation("valescenthud.config.underlayOffsetX")
                .defineInRange("underlayOffsetX", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        underlayOffsetY = builder
                .comment("Set the vertical offset for the hotbar underlay")
                .translation("valescenthud.config.underlayOffsetY")
                .defineInRange("underlayOffsetY", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        enableOverlay = builder
                .comment("Enable rendering for the hotbar overlay")
                .translation("valescenthud.config.enableOverlay")
                .define("enableOverlay", false);
        overlayOffsetX = builder
                .comment("Set the horizontal offset for the hotbar overlay")
                .translation("valescenthud.config.overlayOffsetX")
                .defineInRange("overlayOffsetX", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        overlayOffsetY = builder
                .comment("Set the vertical offset for the hotbar overlay")
                .translation("valescenthud.config.overlayOffsetY")
                .defineInRange("overlayOffsetY", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        enableLevelOffset = builder
                .comment("Enable offsetting the experience level number")
                .translation("valescenthud.config.enableLevelOffset")
                .define("enableLevelOffset", false);
        levelOffsetX = builder
                .comment("Set the horizontal offset for the level number")
                .translation("valescenthud.config.levelOffsetX")
                .defineInRange("levelOffsetX", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        levelOffsetY = builder
                .comment("Set the vertical offset for the level number")
                .translation("valescenthud.config.levelOffsetY")
                .defineInRange("levelOffsetY", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        enableOffhandOffset = builder
                .comment("Enable offsetting the offhand hud")
                .translation("valescenthud.config.enableOffhandOffset")
                .define("enableOffhandOffset", false);
        offhandOffsetX = builder
                .comment("Set the horizontal offset for the offhand hud")
                .translation("valescenthud.config.offhandOffsetX")
                .defineInRange("offhandOffsetX", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        offhandOffsetY = builder
                .comment("Set the vertical offset for the offhand hud")
                .translation("valescenthud.config.offhandOffsetY")
                .defineInRange("offhandOffsetY", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    static {
        Pair<Config, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(Config::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
