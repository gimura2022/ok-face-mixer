package org.gimura.okfacemixer.mixer.utils;

import org.gimura.okfacemixer.mixer.IRender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GradientGenerator implements IRender {
    protected Color start, end;

    public GradientGenerator(Color start, Color end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Image renderToImage(int width, int height) {
        Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();

        for (int i = 0; i < height; i++) {
            graphics.setColor(mixColors((float) i / (float) height));
            graphics.drawLine(0, i, width, i);
        }

        return image;
    }

    @Contract("_ -> new")
    private @NotNull Color mixColors(float pos) {
        if (pos > 1f) pos = 1f;
        else if (pos < 0f) pos = 0f;
        float iRatio = 1.0f - pos;

        int i1 = start.getRGB();
        int i2 = end.getRGB();

        int a1 = (i1 >> 24 & 0xff);
        int r1 = ((i1 & 0xff0000) >> 16);
        int g1 = ((i1 & 0xff00) >> 8);
        int b1 = (i1 & 0xff);

        int a2 = (i2 >> 24 & 0xff);
        int r2 = ((i2 & 0xff0000) >> 16);
        int g2 = ((i2 & 0xff00) >> 8);
        int b2 = (i2 & 0xff);

        int a = (int)((a1 * iRatio) + (a2 * pos));
        int r = (int)((r1 * iRatio) + (r2 * pos));
        int g = (int)((g1 * iRatio) + (g2 * pos));
        int b = (int)((b1 * iRatio) + (b2 * pos));

        return new Color( a << 24 | r << 16 | g << 8 | b );
    }
}
