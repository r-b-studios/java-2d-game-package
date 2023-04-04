package gameObjects;

import main.Button;
import main.Component;
import main.Fonts;
import main.GameObject;
import java.awt.*;

public class MainMenu extends GameObject {
    public MainMenu() {
        setWindowBackground(new Color(0x3183FF));

        var btn = new Button("PLAY");
        btn.setHover(new Color(0x80E8FF), new Color(0xA6EEFF));
        btn.setEventListener(this::startGame);

        assert Fonts.COMIC_SANS_MS != null;
        btn.setFont(Fonts.COMIC_SANS_MS.deriveFont(16f).deriveFont(Font.BOLD));

        addChild(new Component<>(btn, 0, 0, 200, 50));
    }

    private void startGame() {
        add(new PlayMode());
        kill();
    }
}