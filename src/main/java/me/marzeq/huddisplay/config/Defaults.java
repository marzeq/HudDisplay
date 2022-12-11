package me.marzeq.huddisplay.config;

import me.marzeq.huddisplay.config.enums.Alignment;
import me.marzeq.huddisplay.config.enums.Line;
import me.marzeq.huddisplay.config.enums.Position;
import me.marzeq.huddisplay.config.enums.SystemTime;

public class Defaults {
    public static boolean defaultFps = false;
    // Lines
    public static boolean defaultPing = false;
    public static boolean defaultPlayerName = false;
    public static boolean defaultXYZ = false;
    public static boolean defaultBiome = false;
    public static boolean defaultLightLevel = false;
    public static boolean defaultSystemTime = false;
    public static SystemTime defaultSystemTimeFormat = SystemTime.TWENTY_FOUR;

    // Colors
    public static int defaultFpsColor = 0xffffff;
    public static int defaultPingColor = 0xffffff;
    public static int defaultPlayerNameColor = 0xffffff;
    public static int defaultXyzColor = 0xffffff;
    public static int defaultBiomeColor = 0xffffff;
    public static int defaultLightLevelColor = 0xffffff;
    public static int defaultSystemTimeColor = 0xffffff;

    // Order
    public static Line[] defaultOrder = new Line[]{Line.FPS, Line.XYZ, Line.BIOME, Line.PING, Line.PLAYER_NAME, Line.LIGHT_LEVEL, Line.SYSTEM_TIME};

    // Position
    public static Position defaultPosition = Position.TOP_LEFT;
    public static Alignment defaultAlignment = Alignment.LEFT;
}
