package com.xnity.valescenthud.mixin;

import com.xnity.valescenthud.config.ModConfig;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Gui.class)
public class HotbarMixin {

    @Unique
    private static final ResourceLocation valescent$UNDERLAY = ResourceLocation.fromNamespaceAndPath("valescenthud", "textures/gui/underlay.png");
    @Unique
    private static final ResourceLocation valescent$OVERLAY = ResourceLocation.fromNamespaceAndPath("valescenthud", "textures/gui/overlay.png");

    @Inject(method = "renderItemHotbar", at = @At("HEAD"))
    private void valescent$renderUnderlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!ModConfig.enableUnderlay.get()) {
            return;
        }

        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();

        int underlaySizeX = ModConfig.underlaySizeX.get();
        int underlaySizeY = ModConfig.underlaySizeY.get();

        int centerX = (width / 2) - (underlaySizeX / 2) + ModConfig.underlayOffsetX.get();
        int hotbarY = height - 22 - (underlaySizeY - 22) / 2 + ModConfig.underlayOffsetY.get();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, -90.0F);
        guiGraphics.blit(valescent$UNDERLAY, centerX, hotbarY, 0, 0, underlaySizeX, underlaySizeY, underlaySizeX, underlaySizeY);
        guiGraphics.pose().popPose();
    }

    @Inject(method = "renderItemHotbar", at = @At("TAIL"))
    private void valescent$renderOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!ModConfig.enableOverlay.get()) {
            return;
        }

        int width = guiGraphics.guiWidth();
        int height = guiGraphics.guiHeight();

        int overlaySizeX = ModConfig.overlaySizeX.get();
        int overlaySizeY = ModConfig.overlaySizeY.get();

        int centerX = (width / 2) - (overlaySizeX / 2) + ModConfig.overlayOffsetX.get();
        int hotbarY = height - 22 - (overlaySizeY - 22) / 2 + ModConfig.overlayOffsetY.get();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, -90.0F);
        guiGraphics.blit(valescent$OVERLAY, centerX, hotbarY, 0, 0, overlaySizeX, overlaySizeY, overlaySizeX, overlaySizeY);
        guiGraphics.pose().popPose();
    }

    @ModifyArgs(
            method = "renderItemHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
                    ordinal = 2))

    private void valescent$offhandLeftSpriteOffset(Args args) {
        if(!ModConfig.enableOffhandOffset.get()) {
            return;
        }

        int x = args.get(1);
        int y = args.get(2);

        int xOffset = x + ModConfig.offhandOffsetX.get();
        int yOffset = y - ModConfig.offhandOffsetY.get();

        args.set(1, xOffset);
        args.set(2, yOffset);
    }

    @ModifyArgs(
            method = "renderItemHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V",
                    ordinal = 1))

    private void valescent$offhandLeftItemOffset(Args args) {
        if(!ModConfig.enableOffhandOffset.get()) {
            return;
        }

        int x = args.get(1);
        int y = args.get(2);

        int xOffset = x + ModConfig.offhandOffsetX.get();
        int yOffset = y - ModConfig.offhandOffsetY.get();

        args.set(1, xOffset);
        args.set(2, yOffset);
    }

    @ModifyArgs(
            method = "renderItemHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/client/DeltaTracker;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;I)V",
                    ordinal = 2))

    private void valescent$offhandRightItemOffset(Args args) {
        if(!ModConfig.enableOffhandOffset.get()) {
            return;
        }
        int x = args.get(1);
        int y = args.get(2);

        int xOffset = x - ModConfig.offhandOffsetX.get();
        int yOffset = y - ModConfig.offhandOffsetY.get();

        args.set(1, xOffset);
        args.set(2, yOffset);
    }

    @ModifyArgs(
            method = "renderItemHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V",
                    ordinal = 3))

    private void valescent$offhandRightSpriteOffset(Args args) {
        if(!ModConfig.enableOffhandOffset.get()) {
            return;
        }

        int x = args.get(1);
        int y = args.get(2);
        int xOffset = x - ModConfig.offhandOffsetX.get();
        int yOffset = y - ModConfig.offhandOffsetY.get();

        args.set(1, xOffset);
        args.set(2, yOffset);
    }
}
