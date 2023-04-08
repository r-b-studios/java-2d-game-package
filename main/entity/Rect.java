package main.entity;

import main.app.GamePanel;
import main.app.Settings;
import main.tools.InputHandler;

import java.awt.*;

public class Rect extends Entity {

    public Color color = Color.BLUE;

    public Rect(GamePanel gp, InputHandler ih) {
        super(gp, ih);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);

        g.setColor(color);
        g.fillRect((int) x * Settings.TILE_SIZE, (int) y * Settings.TILE_SIZE, width * Settings.TILE_SIZE, height * Settings.TILE_SIZE);
    }
}