package engine;

import java.awt.*;

public class Rect extends GameObject {

    private Color color = new Color(0, 0, 0, 0);

    @Override
    void draw(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}