package engine;

import java.awt.*;
import java.awt.image.Raster;

public class Button extends Image {

    private Label label;
    private Raster imgRaster;
    private boolean hovered, clicked;

    @Override
    public void load() {
        super.load();

        label = new Label() {
            @Override
            public void load() {
                super.load();
            }
        };

        addChild(label);

        getInput().onMouseDown.subscribe(() -> {
            if (getHover()) {
                click();
            }
        });
    }

    @Override
    public void update() {
        super.update();

        label.setSize(getSize().width, getSize().height);

        if (getHover()) {
            if (getInput().getMousePressed()) {
                if (!clicked) {
                    increaseBrightness(20);
                    clicked = true;
                }

                hovered = false;
            } else {
                if (!hovered) {
                    increaseBrightness(10);
                    hovered = true;
                    setHoverCursor();
                }

                clicked = false;
            }
        } else {
            if (clicked || hovered) {
                increaseBrightness(0);
                clicked = hovered = false;
                setDefaultCursor();
            }
        }
    }

    @Override
    public void setImageSource(String fileName) {
        super.setImageSource(fileName);
        imgRaster = img.getData();
    }

    public Label getLabel() {
        return label;
    }

    private void increaseBrightness(int increase) {
        for (int x = 0; x < imgRaster.getWidth(); x++) {
            for (int y = 0; y < imgRaster.getHeight(); y++) {
                // default Color
                var def = getColor(imgRaster.getPixel(x, y, new int[4]));

                var color = new Color(
                    Math.min(def.getRed() + increase, 255),
                    Math.min(def.getGreen() + increase, 255),
                    Math.min(def.getBlue() + increase, 255),
                    def.getAlpha()
                );

                img.getRaster().setPixel(x, y, new int[] {
                    color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()
                });
            }
        }
    }

    private boolean getHover() {
        var mPos = getMousePosition();
        var gPos = getGlobalPosition();

        return mPos != null && mPos.x >= gPos.x && mPos.x <= gPos.x + getSize().width && mPos.y >= gPos.y && mPos.y <= gPos.y + getSize().height;
    }

    private Color getColor(int[] rgba) {
        return new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
    }

    protected void click() { }
}