package entity.obstacle;

import main.app.GamePanel;
import main.app.Settings;
import main.tools.InputHandler;

public class Tree extends Obstacle {

    public Tree(GamePanel gp, InputHandler ih, String type) {
        super(gp, ih);

        width = 2;
        height = 3;

        y = Settings.GROUND_LEVEL - height;
        loadImage("obstacle\\tree\\" + type + ".png");
    }
}