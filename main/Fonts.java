package main;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Fonts {
    public static final Font COMIC_SANS_MS = getFont("comic_sans_ms.ttf");

    private static Font getFont(String path) {
        try {
            var font = Font.createFont(Font.TRUETYPE_FONT, new File("assets\\fonts\\" + path));
            var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            if (!Arrays.stream(ge.getAllFonts()).toList().contains(font)) {
                ge.registerFont(COMIC_SANS_MS);
            }

            return font;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}