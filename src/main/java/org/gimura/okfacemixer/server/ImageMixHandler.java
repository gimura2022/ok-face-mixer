package org.gimura.okfacemixer.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.gimura.okfacemixer.Utils;
import org.gimura.okfacemixer.mixer.Face;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageMixHandler extends BaseMixHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Face out = getMixedFace(exchange);

            if (out == null)
                return;

            Image image = out.renderToImage();

            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage) image, "GIF", byteArray);

            exchange.sendResponseHeaders(200, byteArray.size());
            exchange.getResponseHeaders().add("Content-Type", "image/gif");

            OutputStream outputStream = exchange.getResponseBody();
            byteArray.writeTo(outputStream);

            byteArray.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();

            Utils.sendTextResponse(exchange, 500, "Caught uncaught server exception!");
        }
    }
}
