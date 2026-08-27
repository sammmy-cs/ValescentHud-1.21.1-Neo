package com.trinity.valescenthud.client.mixin.compat.minecraft;

import com.trinity.valescenthud.client.api.Anchor;
import com.trinity.valescenthud.client.api.Handler;
import com.trinity.valescenthud.client.api.Widget;
import com.trinity.valescenthud.client.api.render.SpriteRender;
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
    @Shadow @Final
    private Minecraft minecraft;

    @Unique
    private static final Widget valescent$experienceBarBackground = Handler.addWidget(new Widget(
            new SpriteRender(EXPERIENCE_BAR_BACKGROUND_SPRITE), 0, -22, 182, 5, Anchor.BOTTOM
    ));


    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void valescent$renderExperienceBarWidget(GuiGraphics guiGraphics, int x, CallbackInfo ci) {
        Player player = this.minecraft.player;
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
