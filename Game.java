package engine;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class Game {

    static Settings settings;
    static Container scene;
    static final InputListener inputListener = new InputListener();
    static String defaultCursorSrc, hoverCursorSrc;
    private static final Map<String, Font> fonts = new HashMap<>();

    public static void run(Container scene, Settings settings) {
        Game.settings = settings;
        Game.scene = scene;
        new Window();
    }

    // diese Methode muss for run() ausgeführt werden
    public static void registerFont(String name, String fontSrc) {
        try {
            // lädt die Schriftart
            var file = new File("assets\\" + fontSrc);
            var font = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(18f);

            var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // registriert sie, falls noch nicht getan, im LocalGraphicsEnvironment
            if (!Arrays.stream(ge.getAllFonts()).toList().contains(font)) {
                ge.registerFont(font);
            }

            fonts.put(name, font);
        }
        catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public static Font getFont(String name) {
        return fonts.get(name);
    }

    // diese Methode muss for run() ausgeführt werden
    public static void setDefaultCursor(String src) {
        defaultCursorSrc = src;
    }

    // diese Methode muss for run() ausgeführt werden
    public static void setHoverCursor(String src) {
        hoverCursorSrc = src;
    }
}