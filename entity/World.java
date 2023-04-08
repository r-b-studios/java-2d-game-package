package entity;

import entity.biome.Biome;
import entity.biome.Forest;
import main.app.GamePanel;
import main.app.Settings;
import main.entity.Button;
import main.entity.Entity;
import main.tools.InputHandler;
import main.tools.Interval;

import java.awt.*;

public class World extends Entity {
    // das aktuelle Biome
    private Biome currentBiome;

    // Die durchschnittliche Anzahl an neuen Bäumen, pro Aufruf von generateWorld()
    private final float TREE_PROBABILITY = 0.5f;

    // Die Geschwindigkeitserhöhung pro Sekunde des Spiels
    private final float SPEED_INCREASE = 0.1f;

    // solange bleibt ein Biome, bevor es durch ein anderes ersetzt wird
    private final float BIOME_DURATION = 30;

    private final Ground GROUND = new Ground(panel, inputHandler);
    private final Mualuenie MUA = new Mualuenie(panel, inputHandler);
    private final Button PAUSE_BUTTON = new Button(panel, inputHandler, "PAUSE") {

        // beim Klicken wird das Spiel pausiert und der PauseScreen instanziiert
        @Override
        protected void click() {
            Settings.paused = true;
            World.this.panel.entities.add(new PauseScreen(panel, inputHandler));
        }

        // Button darf nur betätigt werden, wenn das Spiel nicht pausiert ist
        @Override
        public void update() {
            if (!Settings.paused) {
                super.update();
            }
        }
    };

    private final Interval LOAD_BIOME_INTERVAL = new Interval(panel) {
        @Override
        public float execute() {
            if (currentBiome != null) {
                currentBiome.remove();
            }

            panel.entities.add(currentBiome = new Forest(panel, inputHandler) );

            return BIOME_DURATION;
        }
    };

    public World(GamePanel gp, InputHandler kh) {
        super(gp, kh);

        Settings.speed = Settings.DEFAULT_SPEED;
        Settings.paused = false;
        panel.setBackground(new Color(0x8EDCFF));

        PAUSE_BUTTON.x = Settings.X_TILES - 4;
        PAUSE_BUTTON.y = 0.5f;

        addInterval(LOAD_BIOME_INTERVAL);

        panel.entities.addAll(MUA, GROUND, PAUSE_BUTTON);
    }

    @Override
    public void update() {
        super.update();

        if (!Settings.paused) {
            if (Settings.speed < Settings.MAX_SPEED) {
                Settings.speed += SPEED_INCREASE / panel.fps;
            } else {
                Settings.speed = Settings.MAX_SPEED;
            }
        }
    }
}