package main.app;

import main.entity.Entity;
import main.tools.InputHandler;
import main.tools.List;
import startup.Program;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GamePanel extends JPanel implements Runnable {

    private final int SCREEN_WIDTH = Settings.TILE_SIZE * Settings.X_TILES;
    private final int SCREEN_HEIGHT = Settings.TILE_SIZE * Settings.Y_TILES;
    private final int MAX_FPS = 60;

    // Frames pro Sekunde
    public float fps = MAX_FPS;
    private long nanoTime;
    // anzuzeigende FPS
    private int displayFPS = MAX_FPS;

    private final InputHandler inputHandler = new InputHandler();

    private Thread gameThread;

    public final List<Entity> entities = new List<>() {
        @Override
        public void removeAll(Entity... elements) {
        super.removeAll(elements);

        var list = new List<Entity>();
        list.addAll(Arrays.stream(elements).toList());
        list.forEach(Entity::remove);
        }
    };

    public GamePanel() {
        nanoTime = System.nanoTime();

        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.black);
        setDoubleBuffered(true);
        addKeyListener(inputHandler);
        addMouseListener(inputHandler);
        setFocusable(true);
        setCustomCursor();

        Program.start(this, inputHandler);
    }

    public void startThreads() {
        gameThread = new Thread(this);
        gameThread.start();

        // aktualisiert die anzuzeigenden FPS jede Sekunde, bzw alle 1000ms
        new Thread(() -> {
            while (true) {
                try {
                    displayFPS = Math.round(fps);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void run() {
        var drawInterval = 1_000_000_000 / MAX_FPS;
        var nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                var remainingTime = (nextDrawTime - System.nanoTime()) / 1_000_000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            manageFPS();
        }
    }

    private void manageFPS() {
        fps = 1_000_000_000f / (System.nanoTime() - nanoTime);
        nanoTime = System.nanoTime();
    }

    public void update() {
        entities.forEach(Updater::update);

        var smallestLayer = 0;
        var highestLayer = 0;

        for (var e : entities) {
            if (e.layer > highestLayer) {
                highestLayer = e.layer;
            } else if (e.layer < smallestLayer) {
                smallestLayer = e.layer;
            }
        }

        var _entities = new List<Entity>();

        for (var l = smallestLayer; l <= highestLayer; l++) {
            var layer = l;

            entities.forEach(e -> {
                if (e.layer == layer) {
                    _entities.add(e);
                }
            });
        }

        entities.clear();
        entities.addAll(_entities);
    }

    private void setCustomCursor() {
        var toolkit = Toolkit.getDefaultToolkit();
        var img = toolkit.getImage("assets\\img\\cursor\\default.png");
        var cursor = toolkit.createCustomCursor(img, new Point(getX(), getY()), "");
        setCursor(cursor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;

        entities.forEach(e -> {
            e.draw(g2d);
        });

        g2d.setColor(new Color(0x404040));
        g2d.setFont(Fonts.MINECRAFT.deriveFont(14f));
        g2d.drawString(displayFPS + "/" + MAX_FPS + " FPS", 2, 15);

        g2d.dispose();
    }
}