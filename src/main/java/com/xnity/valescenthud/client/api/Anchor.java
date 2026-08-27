package com.xnity.valescenthud.client.api;

public enum Anchor {
    TOP_LEFT(0.0, 0.0, 0, 0),
    TOP(0.5, 0.0, 1, 0),
    TOP_RIGHT(1.0, 0.0, 2, 0),
    LEFT(0.0, 0.5, 0, 1),
    CENTER(0.5, 0.5, 1, 1),
    RIGHT(1.0, 0.5, 2, 1),
    BOTTOM_LEFT(0.0, 1.0, 0, 2),
    BOTTOM(0.5, 1.0, 1, 2),
    BOTTOM_RIGHT(1.0, 1.0, 2, 2);

    private static final Anchor[] VALUES = values();
    private final double anchorX;
    private final double anchorY;
    private final int column;
    private final int row;

    Anchor(double anchorX, double anchorY, int column, int row) {
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        this.column = column;
        this.row = row;
    }

    public double getX() {
        return Handler.guiWidth * anchorX;
    }
    public double getY() {
        return Handler.guiHeight * anchorY;
    }
    public double getOffsetX(int width) {
        return width * anchorX;
    }
    public double getOffsetY(int height) {
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
