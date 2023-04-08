package main.tools;

public final class RandomInt {

    private final int min, max;

    public RandomInt(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int random() {
        return min + (int) (Math.random() * (max - min));
    }
}