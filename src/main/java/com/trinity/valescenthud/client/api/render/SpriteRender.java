package com.trinity.valescenthud.client.api.render;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public final class SpriteRender implements Renderer {

    private final ResourceLocation texture;

    public SpriteRender(ResourceLocation texture) {
        this.texture = texture;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        guiGraphics.blitSprite(texture, x, y, width, height);
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }
}
