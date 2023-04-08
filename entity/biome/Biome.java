package entity.biome;

import entity.obstacle.Obstacle;
import main.app.GamePanel;
import main.entity.Entity;
import main.functional.ResultFunction;
import main.tools.InputHandler;
import main.tools.Interval;
import main.tools.RandomFloat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Biome extends Entity {

    // beinhaltet alle zu generierenden Obstacles mit der zugehörigen Generationswahrscheinlichkeit
    private final Map<Obstacle, Float> obstacles = new HashMap<>();

    // Die Wahrscheinlichkeit, dass generate() kein Obstacle generiert
    private final float ZERO_PROBABILITY;

    public Biome(GamePanel gp, InputHandler ih, RandomFloat generationLoopInterval, float zeroProbability) {
        super(gp, ih);

        ZERO_PROBABILITY = zeroProbability;

        addInterval(new Interval(panel) {
            @Override
            public float execute() {
                Biome.this.generate();
                return generationLoopInterval.random();
            }
        });
    }

    private void generate() {
        if (Math.random() >= ZERO_PROBABILITY) {
            // Die Summe aller Wahrscheinlichkeiten aus obstacles
            var probSum = 0f;

            for (var p : obstacles.values()) {
                probSum += p;
            }

            var _obstacles = new HashMap<Obstacle, Float>();
            var lastProb = -1f;

            for (var key : obstacles.keySet()) {
                var prob = obstacles.get(key);

                if (lastProb == -1f) {
                    prob = lastProb = 0f;
                }
                _obstacles.put(key, lastProb + prob);
                lastProb = prob;
            }

            var rand = (float) (Math.random() * probSum);
            var obst = new AtomicReference<Obstacle>();

            _obstacles.forEach((o, p) -> {
                if (p < rand) {
                    obst.set(o);
                }
            });

            panel.entities.add(obst.get());
        }
    }

    public void addGenerator(float probability, ResultFunction<Obstacle> obstacle) {
        obstacles.put(obstacle.execute(), probability);
    }
}