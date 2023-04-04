package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public final class Window extends JFrame implements KeyListener {

    // statische Instanz
    static Window instance;

    // der Wert von System.nanoTime() im letzten Frame
    private long nanoTime;

    // Frames pro Sekunde (FPS)
    float fps;

    // Attribut für Methode die jeden Tick ausgeführt wird
    Runnable onTick = () -> { };

    // ArrayList mit allen Objekten der Klasse GameObject dieser Scene
    final List<GameObject> objects = new List<>();

    JPanel contentPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Window.this.objects.forEach(obj -> {
                if (obj instanceof Entity i) {
                    try {
                        // liest die Datei
                        var img = ImageIO.read(new File("assets\\img\\" + i.src));

                        // malt das Bild
                        g.drawImage(img, obj.getRX(), obj.getRY(), obj.getRW(), obj.getRH(), null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (obj instanceof Rect r) {
                    g.setColor(r.color);
                    g.fillRect(obj.getRX(), obj.getRY(), obj.getRW(), obj.getRH());
                }
            });

            Window.this.tick();
            repaint();
        }
    };

    public Window() {
        // Festlegen des Fenstertitels
        super("Die Legende von Müaluenie");
        instance = this;
        var icon = new ImageIcon("assets\\img\\icon.png").getImage();
        nanoTime = System.nanoTime();

        // Attribute für das Window festlegen
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        setIconImage(icon);
        setLayout(null);
        secondScreen();
        setVisible(true);
        add(contentPanel);
        contentPanel.setSize(getSize());
        contentPanel.setBackground(Color.black);
        addKeyListener(this);
    }

    // fügt ein Objekt der Klasse GameObjekt der ArrayList objects hinzu und gibt es zurück
    // wenn es eine Instanz der Klasse Component ist, dann wird es dem contentPanel hinzugefügt
    public void add(GameObject object) {
        objects.add(object);

        if (object instanceof Component c) {
            contentPanel.add(c.component);
        }
    }

    // entfernt ein Objekt der Klasse GameObjekt aus der ArrayList objects
    // wenn es eine Instanz der Klasse Component ist, dann wird es aus dem contentPanel entfernt
    public void remove(GameObject object) {
        objects.remove(object);

        if (object instanceof Component c) {
            contentPanel.remove(c.component);
        }
    }

    private void tick() {
        // berechnet die Zeitdifferenz zwischen dem letzten und dem aktuellen Frame
        var timeDif = System.nanoTime() - nanoTime;
        nanoTime = System.nanoTime();

        // berechnete die Frames pro Sekunde
        fps = timeDif > 0 ? ((float) (Math.pow(10, 9) / timeDif)) : 0;

        // passt contentPane an die Fenstergröße an
        contentPanel.setSize(getSize());

        objects.forEach(obj -> {
            obj._tick();

            if (obj instanceof Component c) {
                c.resize();
            }
        });

        onTick.run();

        // Fokus auf diese Fenster, damit Eventlistener fehlerfrei funktionieren
        requestFocusInWindow();
    }

    // verschiebt Fenster auf 2. Monitor
    // Development Only
    private void secondScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        if(2 < gs.length )
        {
            gs[2].setFullScreenWindow( this );
        }
        else if( gs.length > 0 )
        {
            gs[0].setFullScreenWindow( this );
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    // sendet an jedes Objekt den Tasten-Code bei einer Eingabe
    @Override
    public void keyPressed(KeyEvent e) {
        objects.forEach(obj -> {
            obj.keyPressed(e.getKeyCode());
        });
    }

    // sendet an jedes Objekt den Tasten-Code bei einer Eingabe
    @Override
    public void keyReleased(KeyEvent e) {
        objects.forEach(obj -> {
            obj.keyReleased(e.getKeyCode());
        });
    }
}