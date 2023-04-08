package entity.obstacle;

import entity.Mualuenie;
import main.app.GamePanel;
import main.app.Settings;
import main.tools.InputHandler;

public class Piston extends Obstacle {

    // gibt an, ob der Piston bereits nach oben geschossen ist
    private boolean executed;

    // die Distanz, ab der der Piston sich ausfährt
    private final float EXECUTE_DISTANCE = 5f;

    // die Geschwindigkeit mit der sich der Piston pro Sekunde nach oben bewegt
    private final float SPEED = 12f;

    private final float START_Y;

    public Piston(GamePanel gp, InputHandler ih) {
        super(gp, ih);
        loadImage("obstacle\\piston.png");

        START_Y = y = Settings.GROUND_LEVEL;
        height = 3;
    }

    @Override
    public void update() {
        super.update();

        if (!executed && !Settings.paused && x - Mualuenie.getInstance().x <= EXECUTE_DISTANCE) {
            if (y > START_Y - height) {
                y -= SPEED / panel.fps;
            } else {
                y = START_Y - height;
                executed = true;
            }
        }
    }
}