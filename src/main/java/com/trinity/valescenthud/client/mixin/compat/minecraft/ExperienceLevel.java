package com.trinity.valescenthud.client.mixin.compat.minecraft;

import com.trinity.valescenthud.client.api.Anchor;
import com.trinity.valescenthud.client.api.Handler;
import com.trinity.valescenthud.client.api.Widget;
import com.trinity.valescenthud.client.api.render.StringRender;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Gui.class)
public abstract class ExperienceLevel {

    @Shadow @Final
    private Minecraft minecraft;

    @Unique
    private final Widget<StringRender> valescent$experienceLevel = Handler.addWidget(new Widget<>(
            "minecraft:experience_level", new StringRender("0", 8453920, true), 0, -20, 10, 10, Anchor.BOTTOM
    ));

    @Shadow
    protected abstract boolean isExperienceBarVisible();

    @Inject(method = "renderExperienceLevel", at = @At("HEAD"), cancellable = true)
    private void valescent$renderExperienceLevelWidget(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        int level = this.minecraft.player.experienceLevel;
        if(isExperienceBarVisible() && level > 0) {
            valescent$experienceLevel.getRenderer().setText(String.valueOf(level));
            valescent$experienceLevel.render(guiGraphics);
        }
        ci.cancel();
    }
}
