package startup;

import entity.MainMenu;
import main.app.GamePanel;
import main.tools.InputHandler;
import main.tools.WaveFile;

public class Program {

    public static void start(GamePanel panel, InputHandler inputHandler) {
        panel.entities.add(new MainMenu(panel, inputHandler));

        var music = new WaveFile("music\\brick_it.wav");
        music.play(true);
    }
}