package com.xnity.valescenthud;

import com.xnity.valescenthud.config.ModConfig;
import com.xnity.valescenthud.config.Yacl;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.function.Supplier;

@Mod(ValescentHud.MODID)
public class ValescentHud {
    public static final String MODID = "valescenthud";

    public ValescentHud(ModContainer container) {

        container.registerConfig(net.neoforged.fml.config.ModConfig.Type.CLIENT, ModConfig.CONFIG_SPEC);

        if(ModList.get().isLoaded("yet_another_config_lib_v3")) {
            container.registerExtensionPoint(
                    IConfigScreenFactory.class,
                    (Supplier<IConfigScreenFactory>) () -> (client, parent) -> Yacl.setConfigScreen(parent)
            );
        } else {
            container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        }
    }
}