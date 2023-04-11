package engine;

import javax.swing.*;

class Window extends JFrame {

    public Window() {
        super("Die Legende Von Müaluenie");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        add(new GamePanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}