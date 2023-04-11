package engine;

import java.awt.*;

public class Label extends GameObject{

    private Color color = Color.black;
    private String text = "";
    private Font font = GamePanel.INSTANCE.getFont();

    @Override
    void draw(Graphics2D g, int x, int y, int width, int height) {
        var metrics = g.getFontMetrics(font);

        var textX  = x + width / 2 - metrics.stringWidth(text) / 2;
        var textY = x + height / 2 - metrics.getHeight() / 2 + metrics.getAscent() * 1.11f;

        g.setFont(font);
        g.setColor(color);
        g.drawString(text, textX, textY);
        g.setFont(GamePanel.INSTANCE.getFont());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}