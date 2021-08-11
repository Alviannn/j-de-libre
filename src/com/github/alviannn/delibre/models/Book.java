package com.github.alviannn.delibre.models;

import java.util.Arrays;

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
        ID("ID", false),
        TITLE("Title"),
        AUTHOR("Author"),
        YEAR("Year", false),
        PAGE_COUNT("Page", false);

        private final String name;
        private final boolean searchable;

        Field(String name, boolean searchable) {
            this.name = name;
            this.searchable = searchable;
        }

        Field(String name) {
            this(name, true);
        }

        public String getName() {
            return name;
        }

        public boolean isSearchable() {
            return searchable;
        }

        public static String[] getFieldNames() {
            return Arrays.stream(Field.values()).map(Field::getName).toArray(String[]::new);
        }
    }

}
