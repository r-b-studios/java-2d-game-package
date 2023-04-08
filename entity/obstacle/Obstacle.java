package entity.obstacle;

import entity.Ground;
import main.app.GamePanel;
import main.app.Settings;
import main.entity.Entity;
import main.physics.Collider;
import main.physics.Collision;
import main.tools.InputHandler;

public abstract class Obstacle extends Entity {

    private final Collider COLLIDER = new Collider(this, panel) {
        @Override
        protected void onCollide(Entity other, Collision type) { }
    };

    public Obstacle(GamePanel gp, InputHandler ih) {
        super(gp, ih);
        collider = COLLIDER;

        y = Settings.GROUND_LEVEL - height;
        x = Settings.X_TILES + 1 + Ground.getDistanceToNextTile();
    }

    @Override
    public void update() {
        super.update();

        if (!Settings.paused) {
            if (x > -width) {
                x -= Settings.speed / panel.fps;
            } else {
                remove();
            }
        }
    }
}
