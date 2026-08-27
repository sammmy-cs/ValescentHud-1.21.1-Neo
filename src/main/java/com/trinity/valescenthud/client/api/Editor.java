package com.trinity.valescenthud.client.api;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;

public final class Editor extends Screen {
    private final Screen parent;

    private static final double SNAP_DISTANCE = 64.0;

    private static final SequencedSet<Widget<?>> selection = new LinkedHashSet<>(); //convert to array?
    private static Widget<?> hoveredWidget;
    private boolean dragging;

    private final double[] anchorX = new double[Anchor.getAnchors().length];
    private final double[] anchorY = new double[Anchor.getAnchors().length];

    public static boolean doAnchorUpdate = true;

    public Editor(Screen parent) {
        super(Component.literal("Editor"));
        this.parent = parent;
    }

    public static Set<Widget<?>> getSelection() {
        return selection;
    }

    private void updateAnchors() {
        if(!doAnchorUpdate) {
            return;
        }
        Anchor[] anchors = Anchor.getAnchors();

        for(int i = 0; i < anchors.length; i++) {
            Anchor anchor = anchors[i];
            anchorX[i] = anchor.getX();
            anchorY[i] = anchor.getY();
        }
        doAnchorUpdate = false;
    }

    private static final Map<String, Integer> fontSizes = new HashMap<>();

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        updateAnchors();

        if(minecraft.level == null) {
            renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        }
        hoveredWidget = getHovered(mouseX, mouseY);

        List<Widget<?>> widgets = Handler.getWidgets();

        drawAnchors(guiGraphics);

        for(Widget<?> widget: widgets) {
            if(!selection.isEmpty() && selection.getLast() == widget) {
                continue;
            }
            widget.render(guiGraphics);
            widget.highlight(guiGraphics, widget == hoveredWidget);
        }

        if (!selection.isEmpty()) {
            Widget<?> widget = selection.getLast();
            Point pos = widget.getPos();
            String id = widget.getId();

            widget.render(guiGraphics);
            widget.highlight(guiGraphics, widget == hoveredWidget);

            final float scale = 0.45f;
            final float dist = 4.0f;
            int width = fontSizes.computeIfAbsent(id, font::width);
            float height = font.lineHeight * scale;
            float half = width * scale * 0.5f;
            float x = pos.x + widget.getWidth() * 0.5f;
            float y = pos.y - height - dist;

            if(y < 0) {
                y = pos.y + widget.getHeight() + dist;
            }
            x = Math.clamp(x, half, guiGraphics.guiWidth() - half);

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(x, y, 0);
            guiGraphics.pose().scale(scale, scale, 1.0f);
            guiGraphics.drawString(font, id, -width / 2, 0, 0xFFFFFFFF);
            guiGraphics.pose().popPose();
        }

    }

    private void drawAnchors(GuiGraphics guiGraphics) {
        Anchor[] anchors = Anchor.getAnchors();

        Set<Anchor> selectedAnchors = EnumSet.noneOf(Anchor.class);
        Set<Anchor> previewAnchors = EnumSet.noneOf(Anchor.class);

        for(Widget<?> widget: selection) {
            selectedAnchors.add(widget.getAnchor());
            if(dragging) {
                Anchor preview = widget.getPreview();
                if(preview != null) {
                    previewAnchors.add(preview);
                }
            }
        }

        int cellWidth = width / 3;
        int cellHeight = height / 3;

        final int anchorSize = 6;
        final int edgeDist = 12;

        for(Anchor anchor: anchors) {
            int column = anchor.getColumn();
            int row = anchor.getRow();

            int x1 = column * cellWidth;
            int y1 = row * cellHeight;

            int x2 = width;
            int y2 = height;

            if(column != 2) {
                x2 = x1 + cellWidth;
            }
            if(row != 2) {
                y2 = y1 + cellHeight;
            }

            boolean selected = selectedAnchors.contains(anchor);
            boolean preview = dragging && previewAnchors.contains(anchor);

            if(selected || preview) {
                int color = 0x4455FF55;
                if(preview) {
                    color = 0x66FFAA00;
                }
                guiGraphics.fill(x1, y1, x2, y2, color);
            }

            int anchorX = switch(column) {
                case 0 -> x1 + edgeDist;
                case 1 -> (x1 + x2) / 2;
                default -> x2 - edgeDist;
            };

            int anchorY = switch(row) {
                case 0 -> y1 + edgeDist;
                case 1 -> (y1 + y2) / 2;
                default -> y2 - edgeDist;
            };

            int markerColor = preview ? 0xFFFFAA00 : selected ? 0xFF55FF55 : 0x66FFFFFF;

            guiGraphics.fill(anchorX - anchorSize, anchorY - anchorSize, anchorX + anchorSize + 1, anchorY + anchorSize + 1, markerColor);
        }
    }

    private Widget<?> getHovered(double mouseX, double mouseY) {
        List<Widget<?>> widgets = Handler.getWidgets();

        for(int i = widgets.size() - 1; i >= 0; i--) {
            Widget<?> widget = widgets.get(i);

            if(widget.contains(mouseX, mouseY)) {
                return widget;
            }
        }
        return null;
    }

    private Anchor getAnchor(double mouseX, double mouseY) {
        final double hitRadius = 6.0;
        final double hitRadiusSquared = hitRadius * hitRadius;
        Anchor closest = null;

        double closestDistanceSquared = hitRadiusSquared;
        Anchor[] anchors = Anchor.getAnchors();

        for(Anchor anchor : anchors) {
            double anchorX = getAnchorX(anchor);
            double anchorY = getAnchorY(anchor);
            double dx = mouseX - anchorX;
            double dy = mouseY - anchorY;
            double distanceSquared = dx * dx + dy * dy;

            if(distanceSquared < closestDistanceSquared) {
                closestDistanceSquared = distanceSquared;
                closest = anchor;
            }
        }
        return closest;
    }

    private void selectAnchor(Anchor anchor) {
        for(Widget<?> widget: selection) {
            widget.setAnchor(anchor);
        }
    }

    private void selectOnly(Widget<?> widget) {
        selection.clear();
        selection.add(widget);
    }

    private void toggleSelection(Widget<?> widget) {
        if(!selection.add(widget)) {
            selection.remove(widget);
        }
    }

    private double getAnchorX(Anchor anchor) {
        return anchorX[anchor.ordinal()];
    }
    private double getAnchorY(Anchor anchor) {
        return anchorY[anchor.ordinal()];
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(button == 0) {
            dragging = false;

            if(!selection.isEmpty()) {
                Anchor hoveredAnchor = getAnchor(mouseX, mouseY);
                if(hoveredAnchor != null) {
                    selectAnchor(hoveredAnchor);
                    return true;
                }
            }
            if(hoveredWidget == null) {
                selection.clear();
                return true;
            }
            if(hasShiftDown()) {
                if(!selection.add(hoveredWidget)) {
                    selection.remove(hoveredWidget);
                }

                if(!selection.contains(hoveredWidget)) {
                    return true;
                }
            } else {
                if(!selection.contains(hoveredWidget)) {
                    selectOnly(hoveredWidget);
                }
            }

            for(Widget<?> widget: selection) {
                widget.onDrag(mouseX, mouseY);
            }
            dragging = true;

            return true;
        } else if(button == 1) {

            //draw options

            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if(button != 0 || !dragging) {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }

        for(Widget<?> widget : selection) {
            widget.setDrag(mouseX, mouseY);
            widget.clamp(width, height);
            widget.updSnapPreview(SNAP_DISTANCE);
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(button != 0 || !dragging) {
            return super.mouseReleased(mouseX, mouseY, button);
        }
        dragging = false;

        for(Widget<?> widget : selection) {
            widget.clamp(width, height);
            widget.setNearest(SNAP_DISTANCE);
            widget.onDragEnd();
        }
        return true;
    }

    @Override
    protected void init() {
        super.init();
        doAnchorUpdate = true;
    }

    @Override
    public void onClose() {
        for(Widget<?> widget : selection) {
            widget.onDragEnd();
        }
        dragging = false;

        selection.clear();
        hoveredWidget = null;

        minecraft.setScreen(parent);
    }
}
