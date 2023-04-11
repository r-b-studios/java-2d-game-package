package engine;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image extends GameObject {

    BufferedImage img;

    @Override
    void draw(Graphics2D g, int x, int y, int width, int height) {
        if (img != null) {
            g.drawImage(img, x, y, width, height, null);
        }
    }

    public void setImageSource(String fileName) {
        try {
            img = ImageIO.read(new File("assets\\" + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}