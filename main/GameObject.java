package main;

import java.awt.*;

public abstract class GameObject {

    // x-, y-Position, Breite, Höhe
    public float x, y, w, h;

    // x-, y-Position, Breite, Höhe - relativ zum Mittelpunkt des Windows, passt sich an Größe des Windows an
    private int rx, ry, rw, rh;

    // das GameObject ist relativ zu parent
    private GameObject parent;


    // beinhaltet die kill()-Methode aller Objekte der Klasse GameObjekt, für die parent = this gilt
    List<Runnable> onKill = new List<>();

    public GameObject() {
        this(0, 0, 0, 0);
    }

    public GameObject(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    final void _tick() {
        var sceneHeight = getWindow().getHeight();
        var sceneWidth = getWindow().getWidth();

        // Berechnet die Größe relativ zur Größe des Fensters
        rw = (int) (sceneHeight * w / 1000);
        rh = (int) (sceneHeight * h / 1000);

        // Berechnet die Kooardinaten relativ zum Mittelpunkt
        rx = (int) (sceneHeight * (x + (parent == null ? 0 : parent.x)) / 500 - rw + sceneWidth) / 2;
        ry = (int) (sceneHeight * (-y - (parent == null ? 0 : parent.y)) / 500 - rh + sceneHeight) / 2;

        // PHYSICS:


        // x-Koordinaten des linken bzw. rechten Rand des Colliders
        var left = rx;
        var right = rx + rw;

        // y-Koordinaten des oberen bzw. unteren Rand des Colliders
        var top = ry;
        var bottom = ry + rh;

        getWindow().objects.forEach(obj -> {
            if (obj != this) {
                // x-Koordinaten des linken bzw. rechten Rand des anderen Colliders
                var left2 = obj.rx;
                var right2 = obj.rx + obj.rw;

                // y-Koordinaten des oberen bzw. unteren Rand des anderen Colliders
                var top2 = obj.ry;
                var bottom2 = obj.ry + obj.rh;

                // gibt an ob die Objekte unabhängig von ihrer y-Koordinate zusammenstoßen würden
                // h steht für horizontal
                var hCollides = false;

                // gibt an ob die Objekte unabhängig von ihrer x-Koordinate zusammenstoßen würden
                // v steht für vertical
                var vCollides = false;

                // überprüft im Folgenden ob die beiden Objekte sich berühren
                if (rx > obj.rx) {
                    if (left - right2 <= 0) {
                        hCollides = true;
                    }
                }
                else if (rx < obj.rx) {
                    if (left2 - right <= 0) {
                        hCollides = true;
                    }
                }
                else {
                    hCollides = true;
                }

                if (ry > obj.ry) {
                    if (top - bottom2 <= 0) {
                        vCollides = true;
                    }
                }
                else if (ry < obj.ry) {
                    if (top2 - bottom  <= 0) {
                        vCollides = true;
                    }
                }
                else {
                    vCollides = true;
                }

                // löst bei einer Berührung collide() bei beiden Objekten aus
                if (vCollides && hCollides) {
                    collide(obj);
                    obj.collide(this);
                }
            }
        });

        tick();
    }

    private Window getWindow() {
        return Window.instance;
    }

    // überschreibbare Methode, die jeden Frame ausgeführt wird
    public void tick() { }

    public void collide(GameObject obj) { }

    // überschreibbare Methode, die beim Gedrückthalten einer beliebigen Taste ausgeführt wird
    public void keyPressed(int code) { }

    // überschreibbare Methode, die beim Loslassen einer beliebigen Taste ausgeführt wird
    public void keyReleased(int code) { }

    // führt add() von der Klasse Window aus
    protected void add(GameObject object) {
        getWindow().add(object);
    }

    protected void remove(GameObject object) {
        object.onKill.forEach(Runnable::run);
        getWindow().remove(object);
    }

    protected void setWindowBackground(Color bg) {
        getWindow().contentPanel.setBackground(bg);
    }

    // führt add() von der Klasse Window aus
    // dieses Objekt ist das parent-Objekt für den Parameter object
    public void addChild(GameObject object) {
        object.parent = this;
        onKill.add(object::kill);
        add(object);
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
        parent.onKill.add(this::kill);
    }

    public GameObject getParent() {
        return parent;
    }

    public void kill() {
        remove(this);
    }

    int getRX() { return rx; }
    int getRY() { return ry; }
    int getRW() { return rw; }
    int getRH() { return rh; }

    // Frames pro Sekunde (FPS)
    protected final float getFPS() {
        return getWindow().fps;
    }
}