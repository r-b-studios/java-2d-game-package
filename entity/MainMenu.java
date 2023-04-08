package entity;

import main.app.Fonts;
import main.app.GamePanel;
import main.app.Settings;
import main.entity.Button;
import main.entity.Entity;
import main.entity.LargeButton;
import main.entity.Text;
import main.tools.InputHandler;
import main.tools.List;

public class MainMenu extends Entity {

    private final List<Entity> tiles = new List<>();

    private final Text TITLE = new Text(panel, inputHandler, "Die Legende von Mualuenie");

    private final Button PLAY_BUTTON = new LargeButton(panel, inputHandler, "SPIELEN") {
        @Override
        protected void click() {
            MainMenu.this.remove();
            panel.entities.add(new World(panel, inputHandler));
        }
    };

    private final Button SETTINGS_BUTTON = new LargeButton(panel, inputHandler, "EINSTELLUNGEN") {
        @Override
        protected void click() { }
    };

    public MainMenu(GamePanel gp, InputHandler ih) {
        super(gp, ih);

        for (int x = 0; x < Settings.X_TILES; x++) {
            for (int y = 0; y < Settings.Y_TILES; y++) {
                var tile = new Entity(gp, ih, "main_menu\\tile.png");
                tile.x = x;
                tile.y = y;
                tiles.add(tile);
                panel.entities.add(tile);
            }
        }

        TITLE.width = 10;
        TITLE.x = (Settings.X_TILES - TITLE.width) / 2f;
        TITLE.y = Settings.Y_TILES / 2f - 3;
        TITLE.font = Fonts.MINECRAFT.deriveFont(30f);
        panel.entities.add(TITLE);

        PLAY_BUTTON.x = (Settings.X_TILES - PLAY_BUTTON.width) / 2f;
        PLAY_BUTTON.y = Settings.Y_TILES / 2f - 1;
        panel.entities.add(PLAY_BUTTON);

        SETTINGS_BUTTON.x = (Settings.X_TILES - SETTINGS_BUTTON.width) / 2f;
        SETTINGS_BUTTON.y = Settings.Y_TILES / 2f;
        panel.entities.add(SETTINGS_BUTTON);
    }

    @Override
    public void remove() {
        super.remove();

        panel.entities.removeAll(PLAY_BUTTON, SETTINGS_BUTTON, TITLE);
        panel.entities.removeAll(tiles);
    }
}