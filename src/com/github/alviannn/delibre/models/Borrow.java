package com.github.alviannn.delibre.models;

import java.sql.Date;

public class Borrow {

    private final int id, userId, bookId;
    private final Date borrowDate, dueDate;

    public Borrow(int id, int userId, int bookId, Date borrowDate, Date dueDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
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

    public enum Field {
        ID("ID"),
        USER_ID("User ID"),
        BOOK_ID("Book ID"),

        USERNAME("Username"),
        BOOK_TITLE("Book Title"),

        BORROW_DATE("Borrow Date"),
        DUE_DATE("Due Date");

        private final String name;

        Field(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static String[] getFieldNames() {
            Field[] types = values();
            String[] names = new String[types.length];

            for (int i = 0; i < types.length; i++) {
                names[i] = types[i].getName();
            }

            return names;
        }
    }

}
