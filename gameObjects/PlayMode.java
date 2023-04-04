package gameObjects;

import main.Entity;
import main.GameObject;
import main.Rect;

import java.awt.*;

public class PlayMode extends GameObject {

    public PlayMode() {
        setWindowBackground(new Color(0xA7DEFF));

        var ground = new Ground(1000);
        addChild(ground);

        var mua = new Mualuenie();
        addChild(mua);
    }
}