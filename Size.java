package engine;

import java.awt.Dimension;

public class Size {

    public float width, height;

    public Size(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public Dimension toIntDimension() {
        return new Dimension((int) width, (int) height);
    }
}