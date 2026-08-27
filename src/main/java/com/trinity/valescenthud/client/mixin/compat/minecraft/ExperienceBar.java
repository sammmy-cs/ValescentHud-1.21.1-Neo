package com.trinity.valescenthud.client.mixin.compat.minecraft;

import com.trinity.valescenthud.client.api.Anchor;
import com.trinity.valescenthud.client.api.Handler;
import com.trinity.valescenthud.client.api.Widget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(Gui.class)
public class ExperienceBar {

    @Shadow @Final
    private static ResourceLocation EXPERIENCE_BAR_BACKGROUND_SPRITE;

    @Shadow @Final
    private static ResourceLocation EXPERIENCE_BAR_PROGRESS_SPRITE;

    @Unique
    private static final Widget valescent$experienceBarBackground = Handler.addWidget(new Widget(
            EXPERIENCE_BAR_BACKGROUND_SPRITE, 0, -22, 182, 5, Anchor.BOTTOM
    ));

    @Unique
    private static Minecraft minecraft = Minecraft.getInstance();

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private static void valescent$renderExperienceBarWidget(GuiGraphics guiGraphics, int x, CallbackInfo ci) {

        Player player = minecraft.player;
        if(player == null) {
            return;
        }

        int nextExp = player.getXpNeededForNextLevel();
        if(nextExp > 0) {
            int k = (int) (player.experienceProgress * 183);
            valescent$experienceBarBackground.render(guiGraphics);

            Point barPos = valescent$experienceBarBackground.getPos();
            if(k > 0) {
                guiGraphics.blitSprite(EXPERIENCE_BAR_PROGRESS_SPRITE, 182, 5, 0, 0, barPos.x, barPos.y, k, 5);
            }
        }
        ci.cancel();
    }

}
