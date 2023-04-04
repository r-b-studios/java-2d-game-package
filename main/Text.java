package main;

import javax.swing.JTextPane;
import java.awt.Color;

public class Text extends JTextPane {

    public Text(String text) {
        setText(text);
        setForeground(Color.BLACK);
        setBackground(new Color(0, 0, 0, 0));

        setEditable(false);
        setHighlighter(null);
    }
}