package com.github.alviannn.delibre.models;

import java.util.Arrays;
import java.util.Optional;

public class Book {

    private final int id;
    private final String title, author;
    private final int year, pageCount;

    public Book(int id, String title, String author, int year, int pageCount) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pageCount = pageCount;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public int getPageCount() {
        return pageCount;
    }

    public enum Field {
        ID("ID", "id", false),
        TITLE("Title", "title", true),
        AUTHOR("Author", "author", true),
        YEAR("Year", "year", false),
        PAGE_COUNT("Page", "pageCount", false);

        private final String name, column;
        private final boolean searchable;

        Field(String name, String column, boolean searchable) {
            this.name = name;
            this.column = column;
            this.searchable = searchable;
        }

        public String getName() {
            return name;
        }

        public String getColumn() {
            return column;
        }

        public boolean isSearchable() {
            return searchable;
        }

        public static String[] getFieldNames() {
            return Arrays.stream(Field.values()).map(Field::getName).toArray(String[]::new);
        }

        public static Field fromName(String name) {
            Optional<Field> first = Arrays.stream(values())
                    .filter(f -> f.name.equals(name))
                    .findFirst();

            return first.orElse(null);
        }

    }

}
