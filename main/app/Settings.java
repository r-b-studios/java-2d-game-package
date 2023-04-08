package main.app;

public class Settings {
    public static final int TILE_SIZE = 48;
    public static final int TILE_RESOLUTION = 16;
    public static final int X_TILES = 20;
    public static final int Y_TILES = 12;
    public static final int GROUND_LEVEL = Y_TILES - 2;
    public static final float DEFAULT_SPEED = 4;
    public static final float MAX_SPEED = 15;

    public static String groundType = "grass";
    public static float speed = DEFAULT_SPEED;
    public static boolean paused = false;
}