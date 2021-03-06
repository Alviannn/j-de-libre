package com.github.alviannn.delibre.util;

import java.util.Arrays;

public enum SortType {

    ASCENDING("ASC"),
    DESCENDING("DESC");

    private final String key;

    SortType(String key) {
        this.key = key;
    }

    public String makeQuery(String column) {
        return "ORDER BY " + column + " " + key;
    }

    public static String[] getNames() {
        return Arrays.stream(SortType.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

}
