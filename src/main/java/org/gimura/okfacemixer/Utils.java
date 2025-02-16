package org.gimura.okfacemixer;

import com.sun.net.httpserver.HttpExchange;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static Map<String, String> queryToMap(String query) {
        if (query == null) {
            return null;
        }

        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(
                    URLDecoder.decode(entry[0], StandardCharsets.UTF_8),
                    URLDecoder.decode(entry[1], StandardCharsets.UTF_8)
                );
            } else {
                result.put(
                    URLDecoder.decode(entry[0], StandardCharsets.UTF_8),
                    ""
                );
            }
        }

        return result;
    }

    public static @NotNull Image loadImageFromJar(String path) throws IOException {
        return ImageIO.read(getResourceAsStream(path));
    }

    public static @NotNull String loadTextFileFromJar(String path) throws IOException {
        return new String(getResourceAsStream(path).readAllBytes(), StandardCharsets.UTF_8);
    }

    public static void sendTextResponse(@NotNull HttpExchange exchange, int code, @NotNull String message) throws IOException {
        exchange.sendResponseHeaders(code, message.length());

        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(message.getBytes());
        outputStream.close();
    }

    private static @NotNull InputStream getResourceAsStream(String path) throws IOException {
        InputStream stream = Utils.class.getResourceAsStream(path);

        if (stream == null)
            throw new IOException();

        return stream;
    }
}
