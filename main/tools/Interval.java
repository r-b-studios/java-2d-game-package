package main.tools;

import main.app.GamePanel;
import main.app.Updater;

public abstract class Interval implements Updater {

    private final GamePanel panel;

    private float cooldown;

    public Interval(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void update() {
        if (cooldown > 0) {
            cooldown -= 1 / panel.fps;
        } else {
            cooldown = execute();
        }
    }

    public abstract float execute();
}