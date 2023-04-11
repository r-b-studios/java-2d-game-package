package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputListener implements KeyListener, MouseListener {

    private boolean mousePressed;
    public final VoidEvent onMouseDown = new VoidEvent();
    public final Event<Integer> onKeyDown = new Event<>();
    public final Event<Integer> onKeyPressed = new Event<>();
    private boolean keyPressed;

    public boolean getMousePressed() {
        return mousePressed;
    }

    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!keyPressed) {
            onKeyDown.fire(e.getKeyCode());
        }

        onKeyPressed.fire(e.getKeyCode());

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
        if (!mousePressed) {
            onMouseDown.fire();
        }

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