package entity;

import main.app.GamePanel;
import main.app.Settings;
import main.entity.Button;
import main.entity.Entity;
import main.entity.Rect;
import main.tools.InputHandler;

import java.awt.*;

public class PauseScreen extends Entity {

    private final Rect BACKGROUND = new Rect(panel, inputHandler);

    private final Button CONTINUE_BUTTON = new Button(panel, inputHandler, "WEITER") {
        @Override
        protected void click() {
            Settings.paused = false;
            PauseScreen.this.remove();
        }
    };

    private final Button SURRENDER_BUTTON = new Button(panel, inputHandler, "AUFGEBEN") {
        @Override
        protected void click() {
            panel.entities.add(new MainMenu(panel, inputHandler));
            PauseScreen.this.remove();
        }
    };

    public PauseScreen(GamePanel gp, InputHandler ih) {
        super(gp, ih);

        width = Settings.X_TILES;
        height = Settings.Y_TILES;

        BACKGROUND.width = Settings.X_TILES;
        BACKGROUND.height = Settings.Y_TILES;
        BACKGROUND.color = new Color(0x8B000000, true);
        panel.entities.add(BACKGROUND);

        CONTINUE_BUTTON.x = (Settings.X_TILES - CONTINUE_BUTTON.width) / 2f;
        CONTINUE_BUTTON.y = Settings.Y_TILES / 2f - 1;
        panel.entities.add(CONTINUE_BUTTON);

        SURRENDER_BUTTON.x = (Settings.X_TILES - SURRENDER_BUTTON.width) / 2f;
        SURRENDER_BUTTON.y = Settings.Y_TILES / 2f;
        panel.entities.add(SURRENDER_BUTTON);
    }

    @Override
    public void remove() {
        super.remove();
        panel.entities.removeAll(BACKGROUND, CONTINUE_BUTTON, SURRENDER_BUTTON);
    }
}