package gameObjects;

import main.Rect;

import java.awt.*;

public class Ground extends Rect {

    // gibt an, ob dieder Ground sich bereits dupliziert hat
    private boolean duplicated;

    public Ground(float x) {
        super(new Color(0x55403C), x, -350, 2000, 300);
    }

    @Override
    public void tick() {
        if (duplicated) {
            if (x <= -2000) {
                kill();
            }
        }
        else {
            if (x <= 0) {
                var newGround = new Ground(x + w);
                setParent(getParent());
                add(newGround);
            }
        }

        x -= 3000 / getFPS();
    }
}