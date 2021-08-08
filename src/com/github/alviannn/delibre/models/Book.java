package com.github.alviannn.delibre.models;

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
        ID("ID"),
        TITLE("Title"),
        AUTHOR("Author"),
        YEAR("Year"),
        PAGE_COUNT("Page");

        private final String name;

        Field(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static String[] getFieldNames() {
            Field[] types = Field.values();
            String[] names = new String[types.length];

            for (int i = 0; i < types.length; i++) {
                names[i] = types[i].getName();
            }

            return names;
        }
    }

}
