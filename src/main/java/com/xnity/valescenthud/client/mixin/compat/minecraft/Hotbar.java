package com.xnity.valescenthud.client.mixin.compat.minecraft;

import com.mojang.blaze3d.systems.RenderSystem;
import com.xnity.valescenthud.client.api.Anchor;
import com.xnity.valescenthud.client.api.Handler;
import com.xnity.valescenthud.client.api.Widget;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Gui.class)
public abstract class Hotbar {

    @Shadow @Final
    private static ResourceLocation HOTBAR_SPRITE;
    @Shadow @Final
    private static ResourceLocation HOTBAR_SELECTION_SPRITE;
    @Shadow @Final
    private static ResourceLocation HOTBAR_OFFHAND_LEFT_SPRITE;
    @Shadow @Final
    private static ResourceLocation HOTBAR_OFFHAND_RIGHT_SPRITE;

    private static final Widget valescent$hotbar = Handler.addWidget(new Widget(
            HOTBAR_SPRITE, 0, 0, 182, 22, Anchor.BOTTOM
    ));
    @Unique
    private static final Widget valescent$hotbarOffhandLeft = Handler.addWidget(new Widget(
            HOTBAR_OFFHAND_LEFT_SPRITE, 0, 0, 29, 24, Anchor.BOTTOM
    ));
    @Unique
    private static final Widget valescent$hotbarOffhandRight = Handler.addWidget(new Widget(
            HOTBAR_OFFHAND_RIGHT_SPRITE, 0, 0, 29, 24, Anchor.BOTTOM
    ));

    @Shadow
    protected abstract Player getCameraPlayer();
    @Shadow
    protected abstract void renderSlot(GuiGraphics guiGraphics, int x, int y, DeltaTracker deltaTracker, Player player, ItemStack stack, int seed);

    @Inject(method = "renderItemHotbar", at = @At("HEAD"), cancellable = true)
    private void valescent$addHotbarToWidgets(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        Player player = this.getCameraPlayer();
        ItemStack itemStack = player.getOffhandItem();
        HumanoidArm humanoidarm = player.getMainArm().getOpposite();

        RenderSystem.enableBlend();

        valescent$hotbar.renderSprite(guiGraphics);
        guiGraphics.blitSprite(HOTBAR_SELECTION_SPRITE, valescent$hotbar.getPos().x - 1 + player.getInventory().selected * 20, valescent$hotbar.getPos().y  - 1, 24, 23);

        int l = 1;
        for(int i1 = 0; i1 < 9; i1++) {
            int j1 = valescent$hotbar.getPos().x + i1 * 20 + 3;
            int k1 = valescent$hotbar.getPos().y + 3;
            renderSlot(guiGraphics, j1, k1, deltaTracker, player, player.getInventory().items.get(i1), l++);
        }

        if(!itemStack.isEmpty()) {
            if(humanoidarm == HumanoidArm.LEFT) {
                valescent$hotbarOffhandLeft.renderSprite(guiGraphics);
                renderSlot(guiGraphics, valescent$hotbarOffhandLeft.getPos().x + 3, valescent$hotbarOffhandLeft.getPos().y + 4, deltaTracker, player, itemStack, l++);
            } else {
                valescent$hotbarOffhandRight.renderSprite(guiGraphics);
                renderSlot(guiGraphics, valescent$hotbarOffhandRight.getPos().x + 10, valescent$hotbarOffhandRight.getPos().y + 4, deltaTracker, player, itemStack, l++);
            }
        }
        ci.cancel();
    }
}