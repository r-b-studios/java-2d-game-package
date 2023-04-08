package entity;

import main.entity.Entity;
import main.app.GamePanel;
import main.physics.Collider;
import main.tools.InputHandler;
import main.app.Settings;
import java.util.HashMap;
import java.util.Map;

public class Ground extends Entity {

    private static float distanceToNextTile;

    private final Collider COLLIDER = new Collider(this, panel) { };
    private final float START_X = 0;


    // beinhaltet die Tile-Entities und deren zugehörige Start-X-Werte
    private final Map<Entity, Integer> tiles = new HashMap<>();

    // gibt an ob dieses Ground-Objekt bereits ein weiters Objekt der Klasse Ground erstellt hat
    private boolean duplicated;

    public Ground(GamePanel gp, InputHandler ih) {
        super(gp, ih);

        x = START_X;
        y = Settings.GROUND_LEVEL;

        width = Settings.X_TILES;
        height = 2;

        collider = COLLIDER;

        var textures = new String[] {
            "top.png", "bottom.png"
        };

        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                var tile = new Entity(panel, inputHandler, "ground\\" + Settings.groundType + '\\' + textures[y]);
                tile.x = x;
                tile.y = this.y + y;
                tile.layer = 1;
                tiles.put(tile, x);
                panel.entities.add(tile);
            }
        }
    }

    // gibt x % 1 zurück, damit andere Objekte wissen mit welchen x-Wert sie den gleichen wie irgendein Tile des Grounds hätten
    public static float getDistanceToNextTile() {
        return distanceToNextTile;
    }

    @Override
    public void update() {
        super.update();

        distanceToNextTile = x % 1;

        if (!Settings.paused) {
            x -= Settings.speed / panel.fps;

            tiles.forEach((tile, startX) -> {
                tile.x = this.x + startX;
            });

            if (x <= START_X && !duplicated) {
                var newGround = new Ground(panel, inputHandler);
                newGround.x = x + width;
                panel.entities.add(newGround);

                duplicated = true;
            }

            if (x <= START_X - width) {
                remove();
            }
        }
    }

    @Override
    public void remove() {
        super.remove();

        tiles.forEach((tile, startX) -> {
            tile.remove();
        });
    }
}