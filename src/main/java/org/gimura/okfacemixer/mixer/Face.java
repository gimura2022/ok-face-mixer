package org.gimura.okfacemixer.mixer;

import org.gimura.okfacemixer.mixer.elements.IFaceElement;
import org.gimura.okfacemixer.mixer.utils.GradientGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Face {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;

    public static final Color START_COLOR = new Color(247, 226, 136);
    public static final Color END_COLOR = new Color(241, 126, 25);

    protected static Image backgroundImage = new GradientGenerator(START_COLOR, END_COLOR).renderToImage(WIDTH, HEIGHT);

    protected IFaceElement brows;
    protected IFaceElement eyes;
    protected IFaceElement mouth;

    public Face(IFaceElement brows, IFaceElement eyes, IFaceElement mouth) {
        this.brows = brows;
        this.eyes = eyes;
        this.mouth = mouth;
    }

    public IFaceElement getBrows() {
        return brows;
    }

    public IFaceElement getEyes() {
        return eyes;
    }

    public IFaceElement getMouth() {
        return mouth;
    }

    public Image renderToImage() {
        int width = backgroundImage.getWidth(null);
        int height = backgroundImage.getHeight(null);

        Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();

        graphics.drawImage(backgroundImage, 0, 0, null);

        graphics.drawImage(brows.renderToImage(width, height), 0, 0, null);
        graphics.drawImage(eyes.renderToImage(width, height), 0, 0, null);
        graphics.drawImage(mouth.renderToImage(width, height), 0, 0, null);

        return image;
    }

}