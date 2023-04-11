package engine;

import javax.swing.*;
import java.awt.*;

class GamePanel extends JPanel implements Runnable {

    static GamePanel INSTANCE;
    Cursor defaultCursor, hoverCursor;
    float fps;

    private long lastFrame = System.nanoTime();
    private String fpsDisplay;
    private List<GameObject> sortedByLayer = new List<>();

    public GamePanel() {
        INSTANCE = this;

        var width = Game.settings.pixelSize() * Game.settings.tileSize() * Game.settings.xTiles();
        var height = Game.settings.pixelSize() * Game.settings.tileSize() * Game.settings.yTiles();

        setBackground(Color.BLACK);
        setPreferredSize(new Size(width, height).toIntDimension());
        setDoubleBuffered(true);
        setFocusable(true);

        Game.scene.load();
        Game.scene.setSize(width, height);

        addKeyListener(Game.inputListener);
        addMouseListener(Game.inputListener);
        startThreads();

        defaultCursor = getCustomCursor(Game.defaultCursorSrc, Cursor.DEFAULT_CURSOR);
        hoverCursor = getCustomCursor(Game.hoverCursorSrc, Cursor.HAND_CURSOR);
        setCursor(defaultCursor);
    }

    // die run()-Methode des gameThreads; durch startThreads() ausgeführt
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 / Game.settings.maxFPS());
                update();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startThreads() {
        var loopThread = new Thread(this);
        loopThread.start();

        new Thread(() -> {
            while (true) {
                try {
                    fpsDisplay = (int) fps + "/" + Game.settings.maxFPS() + " FPS";
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void update() {
        // berechnen der Frames pro Sekunde
        fps = 1_000_000_000f / (System.nanoTime() - lastFrame);
        lastFrame = System.nanoTime();

        var all = new List<>(Game.scene.getAllChildren());

        // Bubble-Sort: Sortieren nach Attribut layer
        for (int k = 0; k < all.size(); k++) {
            for (int i = 0; i < all.size() - 1; i++) {
                if (all.get(i).getLayer() < all.get(i + 1).getLayer()) {
                    var tmp = all.get(i);
                    all.set(i, all.get(i + 1));
                    all.set(i + 1, tmp);
                }
            }
        }

        sortedByLayer = all;
        sortedByLayer.forEach(GameObject::update);

        repaint();
    }

    private Cursor getCustomCursor(String src, int type) {
        if (src != null) {
            var toolkit = Toolkit.getDefaultToolkit();
            var img = toolkit.getImage("assets\\" + src);
            return toolkit.createCustomCursor(img, new java.awt.Point(getX(), getY()), "");
        } else {
            return Cursor.getPredefinedCursor(type);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var g2d = (Graphics2D) g;

        sortedByLayer.forEach(obj -> {
            var x = (int) obj.getGlobalPosition().x * Game.settings.pixelSize();
            var y = (int) obj.getGlobalPosition().y * Game.settings.pixelSize();
            var width = (int) obj.getSize().width * Game.settings.pixelSize();
            var height = (int) obj.getSize().height * Game.settings.pixelSize();

            obj.draw(g2d, x, y, width, height);
        });

        g2d.setColor(new Color(0x4B4B4B));
        g2d.drawString(fpsDisplay, 1, 12);

        g2d.dispose();
    }
}