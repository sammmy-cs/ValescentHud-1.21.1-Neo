package com.trinity.valescenthud.client.api;

public enum Anchor {
    TOP_LEFT(0.0f, 0.0f, 0, 0),
    TOP(0.5f, 0.0f, 1, 0),
    TOP_RIGHT(1.0f, 0.0f, 2, 0),
    LEFT(0.0f, 0.5f, 0, 1),
    CENTER(0.5f, 0.5f, 1, 1),
    RIGHT(1.0f, 0.5f, 2, 1),
    BOTTOM_LEFT(0.0f, 1.0f, 0, 2),
    BOTTOM(0.5f, 1.0f, 1, 2),
    BOTTOM_RIGHT(1.0f, 1.0f, 2, 2);

    private static final Anchor[] VALUES = values();
    private final float anchorX;
    private final float anchorY;
    private final int column;
    private final int row;

    Anchor(float anchorX, float anchorY, int column, int row) {
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        this.column = column;
        this.row = row;
    }

    public float getX() {
        return Handler.guiWidth * anchorX;
    }
    public float getY() {
        return Handler.guiHeight * anchorY;
    }
    public float getOffsetX(int width) {
        return width * anchorX;
    }
    public float getOffsetY(int height) {
        return height * anchorY;
    }
    public int getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }
    public static Anchor[] getAnchors() {
        return VALUES;
    }
}