package org.gimura.okfacemixer.mixer.elements;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FaceElement implements IFaceElement {
    protected Image image;
    protected String imagePath;
    protected int x, y;

    public FaceElement(Image image, String imagePath, int x, int y) {
        this.image = image;
        this.imagePath = imagePath;

        this.x = x;
        this.y = y;
    }

    @Override
    public Image renderToImage(int width, int height) {
        Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();

        graphics.drawImage(this.image, x, y, null);

        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getImagePath() {
        return imagePath;
    }
}
