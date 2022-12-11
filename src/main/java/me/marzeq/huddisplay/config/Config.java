package me.marzeq.huddisplay.config;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import me.marzeq.huddisplay.HudDisplay;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;

public class Config {
    public boolean showFps = Defaults.defaultFps;

    // Lines
    public boolean showPing = Defaults.defaultPing;
    public boolean showPlayerName = Defaults.defaultPlayerName;
    public boolean showXYZ = Defaults.defaultXYZ;
    public boolean showLightLevel = Defaults.defaultLightLevel;
    public boolean systemTime = Defaults.defaultSystemTime;
    public SystemTime systemTimeFormat = Defaults.defaultSystemTimeFormat;

    // Colors
    public int fpsColor = Defaults.defaultFpsColor;
    public int pingColor = Defaults.defaultPingColor;
    public int playerNameColor = Defaults.defaultPlayerNameColor;
    public int xyzColor = Defaults.defaultXyzColor;
    public int lightLevelColor = Defaults.defaultLightLevelColor;
    public int systemTimeColor = Defaults.defaultSystemTimeColor;

    // Order
    public Line[] order = Defaults.defaultOrder;


    // Position
    public Position defaultPosition = Position.TOP_LEFT;
    public Position position = Defaults.defaultPosition;


    private transient File file;


    private Config() {
    }

    public static Config load() {
        File file = new File(
                FabricLoader.getInstance().getConfigDir().toString(),
                HudDisplay.MOD_ID + ".toml"
        );

        Config config;
        if (file.exists()) {
            Toml configTOML = new Toml().read(file);
            config = configTOML.to(Config.class);
            config.file = file;
        } else {
            config = new Config();
            config.file = file;
            config.save();
        }
        return config;
    }

    public void save() {
        TomlWriter writer = new TomlWriter();
        try {
            writer.write(this, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        showFps = Defaults.defaultFps;
        showPing = Defaults.defaultPing;
        showPlayerName = Defaults.defaultPlayerName;
        showXYZ = Defaults.defaultXYZ;
        showLightLevel = Defaults.defaultLightLevel;
        systemTime = Defaults.defaultSystemTime;
        systemTimeFormat = Defaults.defaultSystemTimeFormat;
        fpsColor = Defaults.defaultFpsColor;
        pingColor = Defaults.defaultPingColor;
        playerNameColor = Defaults.defaultPlayerNameColor;
        xyzColor = Defaults.defaultXyzColor;
        lightLevelColor = Defaults.defaultLightLevelColor;
        systemTimeColor = Defaults.defaultSystemTimeColor;
        order = Defaults.defaultOrder;
        position = Defaults.defaultPosition;
        save();
    }

    public enum Line {
        FPS,
        XYZ,
        PING,
        PLAYER_NAME,
        LIGHT_LEVEL,
        SYSTEM_TIME
    }

    public enum Position {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    public enum SystemTime {
        TWENTY_FOUR,
        TWELVE
    }
}