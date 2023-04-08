package entity;

import main.app.GamePanel;
import main.app.Settings;
import main.entity.Entity;
import main.physics.Collider;
import main.physics.Collision;
import main.tools.Animation;
import main.tools.InputHandler;
import main.tools.Padding;
import main.tools.WaveFile;
import java.awt.event.KeyEvent;

public class Mualuenie extends Entity {

    private static Mualuenie instance;

    private final Animation MOVE_ANIMATION = new Animation(true,
            new Animation.Frame("mualuenie\\run\\mua1.png", 0.4f),
            new Animation.Frame("mualuenie\\run\\mua2.png", 0.4f),
            new Animation.Frame("mualuenie\\run\\mua3.png", 0.4f),
            new Animation.Frame("mualuenie\\run\\mua4.png", 0.4f),
            new Animation.Frame("mualuenie\\run\\mua5.png", 0.4f),
            new Animation.Frame("mualuenie\\run\\mua6.png", 0.4f)
    );

    private final Animation JUMP_ANIMATION = new Animation(false,
            new Animation.Frame("mualuenie\\jump\\mua1.png", 0.25f),
            new Animation.Frame("mualuenie\\jump\\mua2.png", 0.25f),
            new Animation.Frame("mualuenie\\jump\\mua3.png", 0.25f),
            new Animation.Frame("mualuenie\\jump\\mua4.png", 0.25f)
    );

    private final Collider COLLIDER = new Collider(this, panel) {
        public int x;

        @Override
        protected void onCollide(Entity other, Collision type) {
            switch (type) {
                case VERTICALLY -> {
                    // überprüft, ob Müa von oben auftrifft und ob er fällt
                    if (Mualuenie.this.y < other.y && currentJumpSpeed <= 0) {
                        Mualuenie.this.y = other.y - Mualuenie.this.height;
                        Mualuenie.this.inAir = false;
                    }
                }
                case HORIZONTALLY -> {

                    Settings.paused = true;
                    panel.entities.add(new GameOverScreen(panel, inputHandler));
                }
            }
        }

        @Override
        protected Padding getPadding() {
            return new Padding(0, 0, 5f / 32f, 0);
        }
    };

    private final float JUMP_SPEED = 16f;
    private final float GRAVITY = 0.35f;
    private final float START_Y = Settings.Y_TILES - 4;

    // Die Wahrscheinlichkeit, dass Müaluenie "Yihaa!" bei einem Sprung sagt
    private final float YIHAA_PROBABILITY = 0.3f;

    private boolean jumpRequested;

    // gibt an ob Müa sich auf keinen anderen Entity mit Collider befindet
    private boolean inAir;
    private float currentJumpSpeed;

    public Mualuenie(GamePanel gp, InputHandler kh) {
        super(gp, kh);
        instance = this;

        setAnimation(MOVE_ANIMATION);
        collider = COLLIDER;

        width = height = 2;
        x = 1;
        y = START_Y;

        inputHandler.onKeyUp.subscribe(code -> {
            if (code == KeyEvent.VK_SPACE) {
                jumpRequested = true;
            }
        });
    }

    public static Mualuenie getInstance() {
        return instance;
    }

    @Override
    public void update() {
        if (!Settings.paused) {
            inAir = true;

            // update führt den Collider aus, der inAir auf false setzten kann
            // deshalb davor inAir = true
            super.update();

            if (!inAir && jumpRequested) {
                currentJumpSpeed = JUMP_SPEED;
                inAir = true;
                setAnimation(JUMP_ANIMATION);

                // führt mit einer bestimmten Wahrscheinlichkeit "Yihaa!" aus
                if (Math.random() < YIHAA_PROBABILITY) {
                    new WaveFile("entity\\mualuenie\\yihaa.wav").play(false);
                }

                jumpRequested = false;
            }

            if (inAir) {
                currentJumpSpeed -= GRAVITY;
                this.y -= currentJumpSpeed / panel.fps;
            } else {
                if (currentJumpSpeed != 0) {
                    currentJumpSpeed = 0;
                    setAnimation(MOVE_ANIMATION);
                }
            }
        }
    }
}