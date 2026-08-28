package com.trinity.valescenthud.client.api;

import com.mojang.blaze3d.platform.Window;
import com.trinity.valescenthud.client.api.render.Renderer;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderFrameEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@EventBusSubscriber(modid = "valescenthud", value = Dist.CLIENT)
public final class Handler {

    private static final List<Widget<?>> WIDGETS = new ArrayList<>();
    private static final List<Widget<?>> READ_WIDGETS = Collections.unmodifiableList(WIDGETS);

    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final Window window = minecraft.getWindow();

    public static int guiWidth, guiHeight;

    public static List<Widget<?>> getWidgets() { return READ_WIDGETS; }

    public static <T extends Renderer> Widget<T> addWidget(Widget<T> widget) {
        WIDGETS.add(widget);
        return widget;
    }

    @SubscribeEvent
    public static void onFrameRender(RenderFrameEvent.Pre event) {
        int actualWidth = window.getGuiScaledWidth();
        int actualHeight = window.getGuiScaledHeight();

        if(guiWidth != actualWidth || guiHeight != actualHeight) {
            guiWidth = actualWidth;
            guiHeight = actualHeight;

            Editor.doAnchorUpdate = true;
            for(Widget<?> widget: WIDGETS) {
                widget.shouldUpdate = true;
            }
        }
    }

    public static boolean removeWidget(Widget<?> widget) {
        return WIDGETS.remove(widget);
    }

    public static void clear() {
        WIDGETS.clear();
    }

    public static boolean bringToFront(Widget<?> widget) {
        if(!WIDGETS.remove(widget)) {
            return false;
        }
        WIDGETS.add(widget);
        return true;
    }

    public static boolean sendToBack(Widget<?> widget) {
        if(!WIDGETS.remove(widget)) {
            return false;
        }
        WIDGETS.addFirst(widget);
        return true;
    }
}
