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
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

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

    @ModifyArgs(
            method = "renderItemHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
                    ordinal = 2))

    private void valescent$offHandLeftSpriteOffset(Args args) {
        if(!config.enableOffhandOffset.get()) {
            return;
        }
        int x = args.get(1);
        int y = args.get(2);

        args.set(1, x += config.offhandOffsetX.get());
        args.set(2, y += config.offhandOffsetY.get());
    }

    @ModifyArgs(
            method = "renderItemHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V",
                    ordinal = 1))

    private void valescent$offHandLeftItemOffset(Args args) {
        if(!config.enableOffhandOffset.get()) {
            return;
        }
        int x = args.get(1);
        int y = args.get(2);

        args.set(1, x += config.offhandOffsetX.get());
        args.set(2, y += config.offhandOffsetY.get());
    }

    @ModifyArgs(
            method = "renderItemHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V",
                    ordinal = 2))

    private void valescent$offHandRightItemOffset(Args args) {
        if(!config.enableOffhandOffset.get()) {
            return;
        }
        int x = args.get(1);
        int y = args.get(2);

        args.set(1, x -= config.offhandOffsetX.get());
        args.set(2, y += config.offhandOffsetY.get());
    }

    @ModifyArgs(
            method = "renderItemHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
                    ordinal = 3))

    private void valescent$offHandRightSpriteOffset(Args args) {
        if(!config.enableOffhandOffset.get()) {
            return;
        }
        int x = args.get(1);
        int y = args.get(2);

        args.set(1, x -= config.offhandOffsetX.get());
        args.set(2, y += config.offhandOffsetY.get());
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
