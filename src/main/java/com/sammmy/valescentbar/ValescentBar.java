package com.sammmy.valescentbar;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(ValescentBar.MODID)
public class ValescentBar {
    public static final String MODID = "valescentbar";

    public ValescentBar(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, Config.CONFIG_SPEC);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
