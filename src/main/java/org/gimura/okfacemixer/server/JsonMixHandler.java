package org.gimura.okfacemixer.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.gimura.okfacemixer.Utils;
import org.gimura.okfacemixer.mixer.Face;
import org.gimura.okfacemixer.mixer.utils.JsonFaceHelper;

import java.io.IOException;

public class JsonMixHandler extends BaseMixHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            Face out = getMixedFace(exchange);

            if (out == null)
                return;

            exchange.getResponseHeaders().add("Content-Type", "text/json");
            Utils.sendTextResponse(exchange, 200, JsonFaceHelper.writeToJson(out));
        } catch (Exception e) {
            e.printStackTrace();

            Utils.sendTextResponse(exchange, 500, "Caught uncaught server exception!");
        }
    }
}
