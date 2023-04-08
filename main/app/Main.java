package main.app;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Die Legende von Müaluenie");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        var icon = new ImageIcon("assets\\img\\app\\icon.png");
        window.setIconImage(icon.getImage());

        var panel = new GamePanel();
        window.add(panel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        panel.startThreads();
    }
}