package main;

import java.awt.*;

// Klasse für ein Rechteck
public class Rect extends GameObject{

    // die Farbe des Rechtecks
    public Color color;

    public Rect(Color color, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.color = color;
    }
}