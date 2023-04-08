package main.entity;

import main.app.GamePanel;
import main.tools.InputHandler;

public abstract class LargeButton extends Button {

    public LargeButton(GamePanel gp, InputHandler ih, String text) {
        super(gp, ih, text);

        width = 5;
        height = 1;
        rootPath = "button\\large\\";

        loadImage(rootPath + "default.png");
    }
}