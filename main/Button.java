package main;

import javax.swing.JButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Button extends JButton implements ActionListener {

    // Hintergrundfarbe, Hoverfarbe
    private Color bgColor, hoverColor = null;

    // Methode, die beim Draufklicken ausgeführt wird
    private Runnable onClick = () -> { };

    public Button(String text, Runnable onClick) {
        this(text);
        setEventListener(onClick);
    }

    public Button(String text) {
        super(text);

        addMouseListener(new MouseAdapter() {
            // setzt die Hintergrundfarbe auf hoverColor
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

                if (hoverColor != null && bgColor != null) {
                    Button.this.setBackground(hoverColor);
                }
            }

            // setzt die Hintergrundfarbe auf bgColor
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);

                if (hoverColor != null && bgColor != null) {
                    Button.this.setBackground(bgColor);
                }
            }
        });

        setBorderPainted(false);
        addActionListener(this);
    }

    public void setHover(Color bg, Color hv) {
        setBackground(bgColor = bg);
        hoverColor = hv;
    }

    public void setEventListener(Runnable onClick) {
        this.onClick = onClick;
    }

    // führt onClick beim Draufklicken aus
    @Override
    public void actionPerformed(ActionEvent e) {
        onClick.run();
    }
}