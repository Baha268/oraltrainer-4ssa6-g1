package com.esprit.oral.progress.util;

import java.util.List;

/** CEFR levels in ascending order. */
public final class Levels {

    private static final List<String> ORDER = List.of("A1", "A2", "B1", "B2", "C1", "C2");

    private Levels() {}

    /** Position of the level (0 = A1), or -1 if null or unknown. */
    public static int rank(String level) {
        if (level == null) return -1;
        return ORDER.indexOf(level.trim().toUpperCase());
    }

    public static boolean isValid(String level) {
        return rank(level) >= 0;
    }
}
