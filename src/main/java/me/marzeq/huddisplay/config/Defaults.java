package me.marzeq.huddisplay.config;

public class Defaults {
    public static boolean defaultFps = false;
    // Lines
    public static boolean defaultPing = false;
    public static boolean defaultPlayerName = false;
    public static boolean defaultXYZ = false;
    public static boolean defaultBiome = false;
    public static boolean defaultLightLevel = false;
    public static boolean defaultSystemTime = false;
    public static Config.SystemTime defaultSystemTimeFormat = Config.SystemTime.TWENTY_FOUR;

    // Colors
    public static int defaultFpsColor = 0xffffff;
    public static int defaultPingColor = 0xffffff;
    public static int defaultPlayerNameColor = 0xffffff;
    public static int defaultXyzColor = 0xffffff;
    public static int defaultBiomeColor = 0xffffff;
    public static int defaultLightLevelColor = 0xffffff;
    public static int defaultSystemTimeColor = 0xffffff;

    // Order
    public static Config.Line[] defaultOrder = new Config.Line[]{Config.Line.FPS, Config.Line.XYZ, Config.Line.BIOME, Config.Line.PING, Config.Line.PLAYER_NAME, Config.Line.LIGHT_LEVEL, Config.Line.SYSTEM_TIME};

    // Position
    public static Config.Position defaultPosition = Config.Position.TOP_LEFT;
}
