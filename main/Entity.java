package main;

public class Entity extends GameObject{

    public String src;

    public Entity(String src, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.src = src;
    }
}