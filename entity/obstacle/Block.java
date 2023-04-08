package entity.obstacle;

import main.app.GamePanel;
import main.tools.InputHandler;

public class Block extends Obstacle {

    public Block(GamePanel gp, InputHandler ih, String type) {
        super(gp, ih);
        loadImage("obstacle\\block\\" + type + ".png");
    }
}
