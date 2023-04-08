package main.entity;

import main.app.GamePanel;
import main.app.Updater;
import main.tools.List;
import main.app.Settings;
import main.functional.ResultFunction;
import main.physics.Collider;
import main.tools.Animation;
import main.tools.InputHandler;
import main.tools.Interval;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Entity implements Updater {

    // Koordianten, Größe und Geschwindigkeit
    public float x = 0, y = 0;
    public int width = 1, height = 1;

    // Referenzen zum GamePanel und KeyHandler
    protected final GamePanel panel;
    protected final InputHandler inputHandler;

    // Optische Darstellung, des Objektes: Bild oder Animation
    private BufferedImage img;
    private Animation animation;

    // Collider-Refernz, muss nicht gesetzt sein
    public Collider collider;

    // gibt an in welcher Schicht sich das Entity befindet, d. h. ob es vor oder hinter anderen sich befindet
    public int layer;

    // Attribute zur Darstellung einer Animation
    private float animationCooldown;
    private int animationIndex;

    // Liste mit allen Intervallen
    private final List<Updater> components = new List<>();

    public Entity(GamePanel gp, InputHandler ih, String fileName) {
        this(gp, ih);
        loadImage(fileName);
    }

    public Entity(GamePanel gp, InputHandler ih, Animation animation) {
        this(gp, ih);
        this.animation = animation;
    }

    public Entity(GamePanel gp, InputHandler ih) {
        panel = gp;
        inputHandler = ih;
    }

    // verarbeitet Animation und updated andere Objekte, die Updater implementieren
    public void update() {
        components.forEach(Updater::update);

        if (collider != null) {
            collider.update();
        }

        if (animation != null && !animation.finished) {
            if (animationCooldown > 0) {
                animationCooldown -= 1 / animation.frames[animationIndex].duration() / panel.fps;
            } else {
                animationIndex++;

                if (animationIndex >= animation.frames.length) {
                    if (animation.LOOP) {
                        animationIndex = 0;
                    } else {
                        animation.finished = true;
                    }
                }

                if (!animation.finished) {
                    var frame = animation.frames[animationIndex];
                    animationCooldown = frame.duration();
                    loadImage(frame.imgSrc());
                }
            }
        }
    }

    public void addInterval(Interval interval) {
        components.add(interval);
    }

    // lädt die Bilddatei
    protected void loadImage(String src) {
        try {
            img = ImageIO.read(new File("assets\\img\\entity\\" + src));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected <T extends Entity> List<T> getEntitiesByType(Class<T> cls) {
        var result = new List<T>();

        panel.entities.forEach(e -> {
            if (cls.isInstance(e)) {
                result.add((T) e);
            }
        });

        return result;
    }

    public void setAnimation(Animation animation) {
        animationIndex = 0;
        animation.finished = false;
        this.animation = animation;
    }

    public void draw(Graphics2D g) {
        if (img != null) {
            final var x = Math.round(this.x * Settings.TILE_RESOLUTION) * (Settings.TILE_SIZE / Settings.TILE_RESOLUTION);
            final var y = Math.round(this.y * Settings.TILE_RESOLUTION) * (Settings.TILE_SIZE / Settings.TILE_RESOLUTION);
            final var width = this.width * Settings.TILE_SIZE;
            final var height = this.height * Settings.TILE_SIZE;

            g.drawImage(img, x, y, width, height, null);
        }
    }

    public void remove() {
        panel.entities.remove(this);
        components.clear();
    }
}