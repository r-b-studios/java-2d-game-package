package main;

import javax.swing.*;

public class Component<T extends JComponent> extends GameObject{

    final JComponent component;

    public Component(JComponent component, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.component = component;
    }

    final void resize() {
        component.setLocation(getRX(), getRY());
        component.setSize(getRW(), getRH());
    }
}