package com.xnity.valescenthud.mixin;

import com.xnity.valescenthud.config.ModConfig;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public class ExperienceMixin {

    @Redirect(
            method = "renderExperienceLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"))

    private int valescent$experienceLevelOffset(GuiGraphics guiGraphics, Font font, String text, int x, int y, int color, boolean dropShadow) {
        if(!ModConfig.enableLevelOffset.get()) {
            return guiGraphics.drawString(font, text, x, y, color, dropShadow);
        }

        int xOffset = x + ModConfig.levelOffsetX.get();
        int yOffset = y - ModConfig.levelOffsetY.get();

        return guiGraphics.drawString(font, text, xOffset, yOffset, color, dropShadow);
    }
}
