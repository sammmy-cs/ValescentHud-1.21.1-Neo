package com.xnity.valescenthud.client.api;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import java.awt.Point;

public final class Widget {

    private static final int HANDLE_SIZE = 5;
    //private static final int ANCHOR_SIZE = 4;

    private final ResourceLocation texture;
    private final Point position = new Point(0, 0);
    private int width;
    private int height;
    private double offsetX;
    private double offsetY;
    private Anchor anchor;
    public boolean shouldUpdate = true;
    private double dragX;
    private double dragY;
    private Anchor snapPreview;

    public Widget(ResourceLocation texture, int x, int y, int width, int height, Anchor anchor) {
        this.texture = texture;
        this.position.x = x;
        this.position.y = y;
        this.width = width;
        this.height = height;
        this.anchor = anchor;
    }

    public void render(GuiGraphics guiGraphics) {
        Point pos = getPos();
        guiGraphics.blit(texture, pos.x, pos.y, 0, 0, width, height, width, height);
    }

    public void renderSprite(GuiGraphics guiGraphics) {
        Point pos = getPos();
        guiGraphics.blitSprite(texture, pos.x, pos.y, width, height);
    }

    /*public void render(GuiGraphics guiGraphics, int customWidth, int customHeight) {
        Point pos = getPos();
        guiGraphics.blit(texture, pos.x, pos.y, 0, 0, customWidth, customHeight, width, height);
    }*/

    public Point getPos() {
        if(shouldUpdate) {
            updatePos();
        }
        return position;
    }

    public Anchor getSnap(double min) {
        if(min < 0.0) {
            return null;
        }

        Point pos = getPos();

        Anchor nearest = null;
        double nearDist = min * min;
        Anchor[] anchors = Anchor.getAnchors();

        for(Anchor anchor: anchors) {
            double widgetAnchorX = pos.x + anchor.getOffsetX(width);
            double widgetAnchorY = pos.y + anchor.getOffsetY(height);

            double guiAnchorX = anchor.getX();
            double guiAnchorY = anchor.getY();

            double dx = widgetAnchorX - guiAnchorX;
            double dy = widgetAnchorY - guiAnchorY;
            double dSq = dx * dx + dy * dy;

            if(dSq < nearDist) {
                nearDist = dSq;
                nearest = anchor;
            }
        }
        return nearest;
    }

    public void setAnchor(Anchor newAnchor) {
        if(newAnchor == anchor){
            return;
        }

        Point pos = getPos();
        anchor = newAnchor;

        setPos(pos.x, pos.y);
    }

    public void setSize(int width, int height) {
        if(this.width == width && this.height == height) {
            return;
        }
        this.width = width;
        this.height = height;
        shouldUpdate = true;
    }

    public void setOffset(double x, double y) {
        this.offsetX = x;
        this.offsetY = y;
        shouldUpdate = true;
    }

    private void updatePos() {
        position.x = (int) Math.round(anchor.getX() + offsetX - anchor.getOffsetX(width));
        position.y = (int) Math.round(anchor.getY() + offsetY - anchor.getOffsetY(height));
        shouldUpdate = false;
    }

    public void setPos(double x, double y) {
        offsetX = x - anchor.getX() + anchor.getOffsetX(width);
        offsetY = y - anchor.getY() + anchor.getOffsetY(height);
        shouldUpdate = true;
    }

    public void setNearest(double min) {
        Anchor nearest = getSnap(min);
        if(nearest == null || nearest == anchor) {
            return;
        }
        setAnchor(nearest);
    }

    public void updSnapPreview(double min) {
        snapPreview = getSnap(min);
    }

    public void highlight(GuiGraphics graphics, boolean hovered) {
        int color;

        if(Editor.getSelection().contains(this)) {
            color = 0xFF55FF55; //green
        } else if(hovered) {
            color = 0xFFFFFF55; //yellow
        } else {
            color = 0x80FFFFFF; //grey
        }

        Point pos = getPos();
        int x = pos.x;
        int y = pos.y;
        int right = x + width - 1;
        int bottom = y + height - 1;

        graphics.hLine(x, right, y, color);
        graphics.hLine(x, right, bottom, color);
        graphics.vLine(x, y, bottom, color);
        graphics.vLine(right, y, bottom, color);
        graphics.fill(x + width - HANDLE_SIZE, y + height - HANDLE_SIZE, x + width, y + height, color);
    }

    public boolean contains(double mouseX, double mouseY) {
        Point pos = getPos();
        return mouseX >= pos.x && mouseX < pos.x + width && mouseY >= pos.y && mouseY < pos.y + height;
    }

    public void onDrag(double mouseX, double mouseY) {
        double anchorX = anchor.getX();
        double anchorY = anchor.getY();
        dragX = mouseX - anchorX - offsetX;
        dragY = mouseY - anchorY - offsetY;
        snapPreview = null;
    }

    public void onDragEnd() {
        dragX = 0.0;
        dragY = 0.0;
        snapPreview = null;
    }

    public void setDrag(double mouseX, double mouseY) {
        double anchorX = anchor.getX();
        double anchorY = anchor.getY();
        offsetX = mouseX - anchorX - dragX;
        offsetY = mouseY - anchorY - dragY;
        shouldUpdate = true;
    }

    public void clamp(int guiWidth, int guiHeight) {
        Point pos = getPos();
        int x = pos.x;
        int y = pos.y;

        int clampedX = Math.clamp(x, 0, Math.max(0, guiWidth - width));
        int clampedY = Math.clamp(y, 0, Math.max(0, guiHeight - height));

        if(clampedX != x) {
            offsetX += clampedX - x;
            shouldUpdate = true;
        }
        if(clampedY != y) {
            offsetY += clampedY - y;
            shouldUpdate = true;
        }
    }

    public ResourceLocation getTexture() { return texture; }
    //public int getWidth() { return width; }
    //public int getHeight() { return height; }
    //public double getOffsetX() { return offsetX; }
    //public double getOffsetY() { return offsetY; }
    public Anchor getPreview() { return snapPreview; }
    public Anchor getAnchor() { return anchor; }
}
