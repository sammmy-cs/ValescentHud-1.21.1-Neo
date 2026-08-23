package com.xnity.valescenthud.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class Yacl {

    public static Screen setConfigScreen(Screen parent) {
        return getConfiguration().build().generateScreen(parent);
    }

    private static YetAnotherConfigLib.Builder getConfiguration() {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.literal("ValescentHud"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.literal("Hotbar"))
                        .tooltip(Component.literal(
                                "Hotbar related configurations"
                        ))
                        .group(OptionGroup.createBuilder()
                                .name(Component.literal("Hotbar Underlay"))
                                .description(OptionDescription.of(Component.literal(
                                        "GUI rendered under the hotbar."
                                )))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.literal("Enabled"))
                                        .description(OptionDescription.of(Component.literal(
                                                "Enable the rendering of the hotbar underlay."
                                        )))
                                        .binding(ModConfig.enableUnderlay.getDefault(), ModConfig.enableUnderlay, newVal -> ModConfig.enableUnderlay.set(newVal))
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Width"))
                                        .description(OptionDescription.of(Component.literal(
                                                "Change the width of the hotbar underlay."
                                        )))
                                        .binding(ModConfig.underlaySizeX.getDefault(), ModConfig.underlaySizeX, newVal -> ModConfig.underlaySizeX.set(newVal))
                                        .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                                                .min(182).max(1024))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Height"))
                                        .description(OptionDescription.of(Component.literal(
                                                "Change the height of the hotbar underlay."
                                        )))
                                        .binding(ModConfig.underlaySizeY.getDefault(), ModConfig.underlaySizeY, newVal -> ModConfig.underlaySizeY.set(newVal))
                                        .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                                                .min(22).max(1024))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Offset X"))
                                        .description(OptionDescription.of(Component.literal(
                                                "Adjust the horizontal offset for the hotbar underlay."
                                        )))
                                        .binding(ModConfig.underlayOffsetX.getDefault(), ModConfig.underlayOffsetX, newVal -> ModConfig.underlayOffsetX.set(newVal))
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Offset Y"))
                                        .description(OptionDescription.of(Component.literal(
                                                "Adjust the vertical offset for the hotbar underlay."
                                        )))
                                        .binding(ModConfig.underlayOffsetY.getDefault(), ModConfig.underlayOffsetY, newVal -> ModConfig.underlayOffsetY.set(newVal))
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Component.literal("Hotbar Overlay"))
                                .description(OptionDescription.of(Component.literal(
                                        "GUI rendered over the hotbar."
                                )))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.literal("Enabled"))
                                        .description(OptionDescription.of(Component.literal(
                                                "Enable the rendering of the hotbar overlay."
                                        )))
                                        .binding(ModConfig.enableOverlay.getDefault(), ModConfig.enableOverlay, newVal -> ModConfig.enableOverlay.set(newVal))
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Width"))
                                        .description(OptionDescription.of(Component.literal(
                                                "Change the width of the hotbar overlay."
                                        )))
                                        .binding(ModConfig.overlaySizeX.getDefault(), ModConfig.overlaySizeX, newVal -> ModConfig.overlaySizeX.set(newVal))
                                        .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                                                .min(182).max(1024))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Height"))
                                        .description(OptionDescription.of(Component.literal(
                                                "Change the height of the hotbar overlay."
                                        )))
                                        .binding(ModConfig.overlaySizeY.getDefault(), ModConfig.overlaySizeY, newVal -> ModConfig.overlaySizeY.set(newVal))
                                        .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                                                .min(22).max(1024))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Offset X"))
                                        .description(OptionDescription.of(Component.literal(
                                                "Adjust the horizontal offset for the hotbar overlay."
                                        )))
                                        .binding(ModConfig.overlayOffsetX.getDefault(), ModConfig.overlayOffsetX, newVal -> ModConfig.overlayOffsetX.set(newVal))
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Offset Y"))
                                        .description(OptionDescription.of(Component.literal(
                                                "Adjust the vertical offset for the hotbar overlay."
                                        )))
                                        .binding(ModConfig.overlayOffsetY.getDefault(), ModConfig.overlayOffsetY, newVal -> ModConfig.overlayOffsetY.set(newVal))
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Component.literal("Offhand Offset"))
                                .description(OptionDescription.of(Component.literal("Adjust the offset for the offhand gui.")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.literal("Enabled"))
                                        .description(OptionDescription.of(Component.literal(
                                                "Enable offsetting the hotbar offhand."
                                        )))
                                        .binding(ModConfig.enableOffhandOffset.getDefault(), ModConfig.enableOffhandOffset, newVal -> ModConfig.enableOffhandOffset.set(newVal))
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Offset X"))
                                        .description(OptionDescription.of(Component.literal("Adjust the horizontal offset. Values are flipped with the opposite main hand.")))
                                        .binding(ModConfig.offhandOffsetX.getDefault(), ModConfig.offhandOffsetX, newVal -> ModConfig.offhandOffsetX.set(newVal))
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Offset Y"))
                                        .description(OptionDescription.of(Component.literal("Adjust the vertical offset.")))
                                        .binding(ModConfig.offhandOffsetY.getDefault(), ModConfig.offhandOffsetY, newVal -> ModConfig.offhandOffsetY.set(newVal))
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.literal("Hud"))
                        .tooltip(Component.literal("Extra hud configurations"))
                        .group(OptionGroup.createBuilder()
                                .name(Component.literal("Experience Bar"))
                                .description(OptionDescription.of(Component.literal("Adjust the Offset for the experience bar level")))
                                .option(Option.<Boolean>createBuilder()
                                        .name(Component.literal("Level Offset Enabled"))
                                        .description(OptionDescription.of(Component.literal("Enable offsetting the experience bar level")))
                                        .binding(ModConfig.enableLevelOffset.getDefault(), ModConfig.enableLevelOffset, newVal -> ModConfig.enableLevelOffset.set(newVal))
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Offset X"))
                                        .description(OptionDescription.of(Component.literal("Adjust the horizontal offset.")))
                                        .binding(ModConfig.levelOffsetX.getDefault(), ModConfig.levelOffsetX, newVal -> ModConfig.levelOffsetX.set(newVal))
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.literal("Offset Y"))
                                        .description(OptionDescription.of(Component.literal("Adjust the vertical offset.")))
                                        .binding(ModConfig.levelOffsetY.getDefault(), ModConfig.levelOffsetY, newVal -> ModConfig.levelOffsetY.set(newVal))
                                        .controller(IntegerFieldControllerBuilder::create)
                                        .build())
                                .build())
                        .build());
    }
}
