package entity.biome;

import entity.obstacle.Block;
import entity.obstacle.Piston;
import entity.obstacle.Tree;
import main.app.GamePanel;
import main.app.Settings;
import main.tools.InputHandler;
import main.tools.RandomFloat;

public class Forest extends Biome {

    private final String[] TREE_TYPES = new String[] {
        "oak", "spruce", "birch", "darkoak", "fir"
    };

    private final String[] BLOCK_TYPES = new String[] {
        "cobblestone", "dirt", "stone"
    };

    public Forest(GamePanel gp, InputHandler ih) {
        super(gp, ih, new RandomFloat(3, 5), 0.1f);

        Settings.groundType = "grass";

        // Tree-Generator
        addGenerator(0.5f, () -> {
            var type = TREE_TYPES[(int) (TREE_TYPES.length * Math.random())];
            return new Tree(panel, inputHandler, type);
        });

        // Block-Generator
        addGenerator(0.2f, () -> {
            var type = BLOCK_TYPES[(int) (BLOCK_TYPES.length * Math.random())];
            return new Block(panel, inputHandler, type);
        });

        // Piston-Generator
        addGenerator(0.3f, () -> new Piston(panel, inputHandler));
    }
}