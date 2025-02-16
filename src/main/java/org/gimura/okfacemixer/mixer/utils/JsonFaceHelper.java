package org.gimura.okfacemixer.mixer.utils;

import org.gimura.okfacemixer.Utils;
import org.gimura.okfacemixer.mixer.Face;
import org.gimura.okfacemixer.mixer.elements.EmptyFaceElement;
import org.gimura.okfacemixer.mixer.elements.FaceElement;
import org.gimura.okfacemixer.mixer.elements.IFaceElement;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class JsonFaceHelper {
    public static String writeToJson(@NotNull Face face) throws JSONException {
        JSONObject rootObj = new JSONObject();

        rootObj.accumulate("brows", writeFaceElementToJson(face.getBrows()));
        rootObj.accumulate("eyes", writeFaceElementToJson(face.getEyes()));
        rootObj.accumulate("mouth", writeFaceElementToJson(face.getMouth()));

        return rootObj.toString();
    }

    private static @NotNull JSONObject writeFaceElementToJson(IFaceElement element) throws JSONException {
        JSONObject obj = new JSONObject();

        if (element instanceof EmptyFaceElement) {
            obj.accumulate("is_empty_element", true);

            return obj;
        }

        FaceElement new_element = (FaceElement) element;

        obj.accumulate("is_empty_element", false);
        obj.accumulate("x", new_element.getX());
        obj.accumulate("y", new_element.getY());
        obj.accumulate("file", new_element.getImagePath());

        return obj;
    }

    public static @NotNull Face loadFromFile(String path) throws IOException, JSONException {
        return loadFromJsonString(Utils.loadTextFileFromJar(path));
    }

    public static @NotNull Face loadFromJsonString(String str) throws JSONException, IOException {
        JSONObject rootObj = new JSONObject(str);

        return new Face(loadFaceElement(rootObj.getJSONObject("brows")),
                loadFaceElement(rootObj.getJSONObject("eyes")),
                loadFaceElement(rootObj.getJSONObject("mouth")));
    }

    private static @NotNull IFaceElement loadFaceElement(@NotNull JSONObject obj) throws JSONException, IOException {
        if (obj.getBoolean("is_empty_element"))
            return new EmptyFaceElement();

        String file = obj.getString("file");
        int x = obj.getInt("x");
        int y = obj.getInt("y");

        return new FaceElement(Utils.loadImageFromJar(file), file, x, y);
    }
}
