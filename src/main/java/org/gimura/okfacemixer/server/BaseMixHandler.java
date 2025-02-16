package org.gimura.okfacemixer.server;

import com.sun.net.httpserver.HttpExchange;
import org.gimura.okfacemixer.Utils;
import org.gimura.okfacemixer.mixer.Face;
import org.gimura.okfacemixer.mixer.Mixer;
import org.gimura.okfacemixer.mixer.utils.JsonFaceHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

public class BaseMixHandler {
    private enum FaceLoadType {
        JSON,
        FILE,
    }

    protected Face getMixedFace(@NotNull HttpExchange exchange) throws Exception {
        Map<String, String> query = Utils.queryToMap(exchange.getRequestURI().getQuery());

        String leftFaceType = query.get("left_type");
        String rightFaceType = query.get("right_type");

        if (leftFaceType == null || rightFaceType == null) {
            Utils.sendTextResponse(exchange, 400, "Left or right face type not found!");
            return null;
        }

        FaceLoadType leftLoadType, rightLoadType;

        try {
            leftLoadType = getFaceLoadType(leftFaceType);
            rightLoadType = getFaceLoadType(rightFaceType);
        } catch (IllegalArgumentException e) {
            Utils.sendTextResponse(exchange, 400, "Left or right face type is invalid!");
            return null;
        }

        String leftFacePath = query.get("left");
        String rightFacePath = query.get("right");

        if (leftFacePath == null || rightFacePath == null) {
            Utils.sendTextResponse(exchange, 400, "Left or right face path not found!");
            return null;
        }

        Face leftFace, rightFace;

        try {
            leftFace = loadFace(leftFacePath, leftLoadType);
            rightFace = loadFace(rightFacePath, rightLoadType);
        } catch (JSONException e) {
            Utils.sendTextResponse(exchange, 400, "Invalid json syntax!");
            return null;
        } catch (IOException e) {
            Utils.sendTextResponse(exchange, 404, "Json file or image not found!");
            return null;
        }

        return Mixer.mixFaces(leftFace, rightFace);
    }

    @Contract(pure = true)
    private FaceLoadType getFaceLoadType(@NotNull String type) {
        return switch (type) {
            case "json" -> FaceLoadType.JSON;
            case "file" -> FaceLoadType.FILE;
            default -> throw new IllegalArgumentException();
        };
    }

    private Face loadFace(String facePath, @NotNull FaceLoadType loadType) throws JSONException, IOException {
        return switch (loadType) {
            case FILE -> JsonFaceHelper.loadFromFile(facePath);
            case JSON -> JsonFaceHelper.loadFromJsonString(facePath);
        };
    }
}
