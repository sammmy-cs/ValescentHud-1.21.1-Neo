package com.trinity.valescenthud.client.api.render;

import net.minecraft.client.gui.GuiGraphics;

public interface Renderer {
    void render(GuiGraphics guiGraphics, int x, int y, int width, int height);
}
