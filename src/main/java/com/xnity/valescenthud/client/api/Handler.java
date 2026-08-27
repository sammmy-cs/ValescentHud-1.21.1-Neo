package com.xnity.valescenthud.client.api;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@EventBusSubscriber(modid = "valescenthud", value = Dist.CLIENT)
public final class Handler {

    private static final List<Widget> WIDGETS = new ArrayList<>();
    private static final List<Widget> READ_WIDGETS = Collections.unmodifiableList(WIDGETS);

    private static final Minecraft minecraft = Minecraft.getInstance();

    public static int guiWidth, guiHeight;

    public static List<Widget> getWidgets() { return READ_WIDGETS; }

    public static Widget addWidget(Widget widget) {
        WIDGETS.add(widget);
        return widget;
    }

    @SubscribeEvent
    public static void onGuiRender(RenderGuiEvent.Pre event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        int actualWidth = guiGraphics.guiWidth();
        int actualHeight = guiGraphics.guiHeight();

        if(guiWidth != actualWidth || guiHeight != actualHeight) {
            guiWidth = guiGraphics.guiWidth();
            guiHeight = guiGraphics.guiHeight();

            Editor.doAnchorUpdate = true;
            for(Widget widget: WIDGETS) {
                widget.shouldUpdate = true;
            }
        }
    }

    public static boolean removeWidget(Widget widget) {
        return WIDGETS.remove(widget);
    }

    public static void clear() {
        WIDGETS.clear();
    }

    public static boolean bringToFront(Widget widget) {
        if(!WIDGETS.remove(widget)) {
            return false;
        }
        WIDGETS.add(widget);
        return true;
    }

    public static boolean sendToBack(Widget widget) {
        if(!WIDGETS.remove(widget)) {
            return false;
        }
        WIDGETS.addFirst(widget);
        return true;
    }

    public static Optional<Widget> getWidget(ResourceLocation texture) {
        if(texture == null) {
            return Optional.empty();
        }
        for(Widget widget : WIDGETS) {
            if(texture.equals(widget.getTexture())) {
                return Optional.of(widget);
            }
        }
        return Optional.empty();
    }
}
