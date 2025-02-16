package org.gimura.okfacemixer.mixer;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Mixer {
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Face mixFaces(@NotNull Face face0, @NotNull Face face1) {
        return new Face(face0.brows, face0.eyes, face1.mouth);
    }
}
