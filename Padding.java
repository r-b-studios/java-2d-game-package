package engine;

public record Padding(float left, float top, float right, float bottom) {
    public static Padding ZERO = new Padding(0, 0, 0, 0);
}
