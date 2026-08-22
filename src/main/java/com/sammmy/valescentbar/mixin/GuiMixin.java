package com.sammmy.valescentbar.mixin;

import com.sammmy.valescentbar.Config;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    private static final Config config = Config.CONFIG;
    private static final ResourceLocation UNDERLAY = ResourceLocation.fromNamespaceAndPath("valescentbar", "textures/gui/underlay.png");
    private static final ResourceLocation OVERLAY = ResourceLocation.fromNamespaceAndPath("valescentbar", "textures/gui/overlay.png");

    @Inject(method = "renderItemHotbar", at = @At("HEAD"))
    private void renderUnderlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!config.enableUnderlay.get()) {
            return;
        }

        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();
        int centerX = (width / 2) - 91 - 37 + config.underlayOffsetX.get();
        int hotbarY = height - 22 - 37 + config.underlayOffsetY.get();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, -90.0F);
        guiGraphics.blit(UNDERLAY, centerX, hotbarY, 0, 0, 256, 96, 256, 96);
        guiGraphics.pose().popPose();
    }

    @Inject(method = "renderItemHotbar", at = @At("TAIL"))
    private void renderOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!config.enableOverlay.get()) {
            return;
        }

        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();
        int centerX = (width / 2) - 91 - 37 + config.overlayOffsetX.get();
        int hotbarY = height - 22 - 37 + config.overlayOffsetY.get();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, -90.0F);
        guiGraphics.blit(OVERLAY, centerX, hotbarY, 0, 0, 256, 96, 256, 96);
        guiGraphics.pose().popPose();
    }

    @Redirect(
            method = "renderExperienceLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;IIIZ)I"))

    private int redirectDrawString(GuiGraphics guiGraphics, Font font, String text, int x, int y, int color, boolean dropShadow) {
        if(!config.enableLevelOffset.get()) {
            return guiGraphics.drawString(font, text, x, y, color, dropShadow);
        }

        return guiGraphics.drawString(font, text, x + config.levelOffsetX.get(), y + config.levelOffsetY.get(), color, dropShadow);
    }
}
