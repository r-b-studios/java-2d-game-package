package gameObjects;

import main.Entity;
import main.GameObject;

import java.awt.event.KeyEvent;

// Klasse für das Müaluenie
public class Mualuenie extends Entity {


    // gibt an ob Müaluenie gerade springt
    private boolean isJumping;

    // die aktuelle Geschwindigkeit während dem Springen in y-Richtung
    private float jumpSpeed;

    public Mualuenie() {
        super("mua.png", -500, -140, 29 * 8, 15 * 8);
    }

    @Override
    public void keyPressed(int code) {
        switch (code) {
            case KeyEvent.VK_W -> y += 2000 / getFPS();
            case KeyEvent.VK_S -> y -= 2000 / getFPS();
            case KeyEvent.VK_A -> x -= 2000 / getFPS();
            case KeyEvent.VK_D -> x += 2000 / getFPS();
        }
    }

    @Override
    public void collide(GameObject other) {
        if (other instanceof Ground g) {
            System.out.println("Mua berührt den Boden!");
        }
    }
}