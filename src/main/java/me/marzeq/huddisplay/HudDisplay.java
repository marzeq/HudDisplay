package me.marzeq.huddisplay;

import me.marzeq.huddisplay.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

@Environment(EnvType.CLIENT)
public class HudDisplay implements ModInitializer {
    public static final String MOD_ID = "huddisplay";
    public static final Config config = Config.load();

    @Override
    public void onInitialize() {
        Config.load();
        System.out.println("Hud Display initialized!");
    }
}
