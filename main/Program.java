package main;

import gameObjects.MainMenu;

public class Program {

    public static void main(String[] args) {
        var window = new Window();
        window.add(new MainMenu());
    }
}