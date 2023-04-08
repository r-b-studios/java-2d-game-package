package main.tools;

// eine Klasse die zwei Werte annimmt und einen zufälligen Wert zwischen diesen beiden zurückgibt
public final class RandomFloat {

    private final float min, max;

    public RandomFloat(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public float random() {
        return min + (float) Math.random() * (max - min);
    }
}