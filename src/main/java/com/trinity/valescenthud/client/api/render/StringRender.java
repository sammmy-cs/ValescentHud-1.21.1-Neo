package com.trinity.valescenthud.client.api.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class StringRender implements Renderer {

    private static final Minecraft minecraft = Minecraft.getInstance();

    private String text;
    private int color;
    private boolean dropShadow;

    public StringRender(String text, int color, boolean dropShadow) {
        this.text = text;
        this.color = color;
        this.dropShadow = dropShadow;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        Font font = minecraft.font;
        if(font == null) {
            return;
        }
        int centerX = x - font.width(text) / 2 + width / 2;
        guiGraphics.drawString(font, text, centerX, y, color, dropShadow);
    }

    public void setText(String text) {
        this.text = text;
    }
}
