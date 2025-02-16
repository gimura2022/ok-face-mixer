package org.gimura.okfacemixer;

import com.sun.net.httpserver.HttpServer;
import org.gimura.okfacemixer.server.ImageMixHandler;
import org.gimura.okfacemixer.server.JsonMixHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8040), 0);

        server.createContext("/api/mix_image.gif", new ImageMixHandler());
        server.createContext("/api/mix_json", new JsonMixHandler());

        server.setExecutor(null);
        server.start();
    }
}