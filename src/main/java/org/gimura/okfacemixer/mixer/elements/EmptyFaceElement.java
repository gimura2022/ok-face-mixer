package org.gimura.okfacemixer.mixer.elements;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EmptyFaceElement implements IFaceElement {
    @Override
    public Image renderToImage(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }
}
