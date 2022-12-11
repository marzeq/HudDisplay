package me.marzeq.huddisplay;

import me.marzeq.huddisplay.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class HudDisplay implements ClientModInitializer {
    public static final String MOD_ID = "huddisplay";
    public static final Config config = Config.load();

    @Override
    public void onInitializeClient() {
        Config.load();
        System.out.println("Hud Display initialized!");
    }
}
