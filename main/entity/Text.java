package main.entity;

import main.app.Fonts;
import main.app.GamePanel;
import main.app.Settings;
import main.tools.InputHandler;

import java.awt.*;

public class Text extends Entity {

    public String text;
    public Color color = new Color(0x2A2A2A);
    public Font font = Fonts.MINECRAFT;

    public Text(GamePanel gp, InputHandler kh, String text) {
        super(gp, kh);
        this.text = text;
        width = 2;
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        var metrics = g.getFontMetrics(font);

        final var x = this.x * Settings.TILE_SIZE + width * Settings.TILE_SIZE / 2 - metrics.stringWidth(text) / 2;
        final var y = this.y * Settings.TILE_SIZE + height * Settings.TILE_SIZE / 2 - metrics.getHeight() / 2 + metrics.getAscent() * 1.11f;

        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }
}