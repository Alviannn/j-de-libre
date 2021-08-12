package com.github.alviannn.delibre.models;

import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;

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
        ID("ID", "id"),
        USER_ID("User ID", "userId"),
        BOOK_ID("Book ID", "bookId"),

        BORROW_DATE("Borrow Date", "borrowDate"),
        DUE_DATE("Due Date", "dueDate"),

        USERNAME("Username", "user.name"),
        BOOK_TITLE("Book Title", "book.title");

        private final String name, column;
        private final boolean searchable;

        Field(String name, String column) {
            this.name = name;
            this.column = column;
            this.searchable = false;
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
