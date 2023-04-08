package main.app;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Fonts {

    public static final Font MINECRAFT = getFont("minecraft.ttf").deriveFont(20f);

    // lädt eine Schriftart (Font) aus einer Datei
    private static Font getFont(String fileName) {
        try {
            // lädt die Schriftart
            var file = new File("assets\\fonts\\" + fileName);
            var font = Font.createFont(Font.TRUETYPE_FONT, file);

            var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // registriert sie, falls noch nicht getan im LocalGraphicsEnvironment
            if (!Arrays.stream(ge.getAllFonts()).toList().contains(font)) {
                ge.registerFont(font);
            }

            return font;
        }
        catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}