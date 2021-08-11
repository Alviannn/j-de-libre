package com.github.alviannn.delibre.models;

import java.sql.Date;
import java.util.Arrays;

public class Borrow {

    private final int id, userId, bookId;
    private final Date borrowDate, dueDate;
    private final String username, bookTitle;

    public Borrow(int id, int userId, int bookId, Date borrowDate, Date dueDate, String username, String bookTitle) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.username = username;
        this.bookTitle = bookTitle;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getBookId() {
        return bookId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getUsername() {
        return username;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public enum Field {
        ID("ID"),
        USER_ID("User ID"),
        BOOK_ID("Book ID"),

        BORROW_DATE("Borrow Date"),
        DUE_DATE("Due Date"),

        USERNAME("Username"),
        BOOK_TITLE("Book Title");

        private final String name;
        private final boolean searchable;

        Field(String name) {
            this.name = name;
            this.searchable = false;
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
