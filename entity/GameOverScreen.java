package entity;

import main.app.GamePanel;
import main.app.Settings;
import main.entity.Button;
import main.entity.Entity;
import main.entity.Rect;
import main.tools.InputHandler;

import java.awt.*;

public class GameOverScreen extends Entity {

    private final Rect BACKGROUND = new Rect(panel, inputHandler);

    private final Button TRY_AGAIN_BUTTON = new Button(panel, inputHandler, "NOCHMAL") {
        @Override
        protected void click() {
            // lädt die Welt neu
            panel.entities.clear();
            panel.entities.add(new World(panel, inputHandler));
        }
    };

    private final Button MAIN_MENU_BUTTON = new Button(panel, inputHandler, "HAUPTMENU") {
        @Override
        protected void click() {
            panel.entities.clear();
            panel.entities.add(new MainMenu(panel, inputHandler));
        }
    };

    public GameOverScreen(GamePanel gp, InputHandler ih) {
        super(gp, ih);

        width = Settings.X_TILES;
        height = Settings.Y_TILES;

        BACKGROUND.width = Settings.X_TILES;
        BACKGROUND.height = Settings.Y_TILES;
        BACKGROUND.layer = 10;
        BACKGROUND.color = new Color(0x8B000000, true);
        panel.entities.add(BACKGROUND);

        TRY_AGAIN_BUTTON.x = (Settings.X_TILES - TRY_AGAIN_BUTTON.width) / 2f;
        TRY_AGAIN_BUTTON.y = Settings.Y_TILES / 2f - 1;
        TRY_AGAIN_BUTTON.layer = 10;
        panel.entities.add(TRY_AGAIN_BUTTON);

        MAIN_MENU_BUTTON.x = (Settings.X_TILES - MAIN_MENU_BUTTON.width) / 2f;
        MAIN_MENU_BUTTON.y = Settings.Y_TILES / 2f;
        MAIN_MENU_BUTTON.layer = 10;
        panel.entities.add(MAIN_MENU_BUTTON);
    }

    @Override
    public void remove() {
        super.remove();
        panel.entities.removeAll(BACKGROUND, TRY_AGAIN_BUTTON, MAIN_MENU_BUTTON);
    }
}