package com.github.alviannn.delibre.util;

import java.util.Arrays;

public enum SortType {

    NONE,
    ASCENDING,
    DESCENDING;

    public static String[] getNames() {
        return Arrays.stream(SortType.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

}
