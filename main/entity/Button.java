package main.entity;

import main.app.GamePanel;
import main.app.Settings;
import main.tools.InputHandler;
import main.tools.List;

public abstract class Button extends Text {

    private final float PADDING_TOP = 2 / 16f;
    private final float PADDING_BOTTOM = 2 / 16f;
    private final float CURSOR_SIZE = 20;
    private final List<Entity> tiles = new List<>();
    private boolean hover, clicked;
    String rootPath = "button\\default\\";

    public Button(GamePanel gp, InputHandler kh, String text) {
        super(gp, kh, text);

        width = 3;
        height = 1;

        loadImage(rootPath + "default.png");
    }

    @Override
    public void update() {
        var mousePos = panel.getMousePosition();

        if (mousePos != null) {
            if (
                mousePos.getX() + CURSOR_SIZE / 2 > x * Settings.TILE_SIZE &&
                mousePos.getX() + CURSOR_SIZE / 2 < (x + width) * Settings.TILE_SIZE &&
                mousePos.getY() + CURSOR_SIZE / 2 > (y + PADDING_TOP) * Settings.TILE_SIZE &&
                mousePos.getY() + CURSOR_SIZE / 2 < (y + height - PADDING_BOTTOM) * Settings.TILE_SIZE
            ) {
                if (inputHandler.mousePressed) {
                    if (!clicked) {
                        loadImage(rootPath + "click.png");
                        click();
                        clicked = true;
                    }

                    hover = false;
                } else {
                    if (!hover) {
                        loadImage(rootPath + "hover.png");
                        hover = true;
                    }

                    clicked = false;
                }
            } else {
                if (clicked || hover) {
                    loadImage(rootPath + "default.png");
                    clicked = hover = false;
                }
            }
        }
    }

    protected abstract void click();
}