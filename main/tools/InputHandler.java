package main.tools;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class InputHandler implements KeyListener, MouseListener {

    public boolean mousePressed;
    public final Event<Integer> onKeyUp = new Event<>();

    private boolean keyPressed;

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!keyPressed) {
            onKeyUp.fire(e.getKeyCode());
        }

        keyPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}