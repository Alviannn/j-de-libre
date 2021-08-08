package com.github.alviannn.delibre.controllers;

import com.github.alviannn.delibre.models.Book;
import com.github.alviannn.delibre.models.Borrow;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.sql.Database;
import com.github.alviannn.delibre.sql.Results;
import com.sun.istack.internal.Nullable;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ModelHelper {

    private final Database db;

    public ModelHelper(Database db) {
        this.db = db;
    }

    /**
     * Tries to find a user within the database
     *
     * @param name the username
     * @return the user object if exists, null is otherwise
     */
    @Nullable
    public User findUser(String name) {
        if (name.isEmpty()) {
            return null;
        }

        try (Results res = db.results("SELECT * FROM users WHERE name = ?;", name)) {
            ResultSet rs = res.getResultSet();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getDate("registerDate"),
                        rs.getBoolean("admin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ---------------------------------------------------------------------------------------------- //

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Results res = db.results("SELECT * FROM users;")) {
            ResultSet rs = res.getResultSet();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getDate("registerDate"),
                        rs.getBoolean("admin"));

                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        try (Results res = db.results("SELECT * FROM books;")) {
            ResultSet rs = res.getResultSet();
            while (rs.next()) {
                Book book = new Book(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getInt("pageCount"));

                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    public List<Borrow> getAllBorrowed() {
        List<Borrow> borrows = new ArrayList<>();

        try (Results results = db.results(
                "SELECT borrows.*, users.name AS username, books.title AS title FROM borrows "
                + "JOIN users ON borrows.userId = users.id "
                + "JOIN books ON borrows.bookId = books.id;")) {

            ResultSet rs = results.getResultSet();
            while (rs.next()) {
                Borrow borrow = new Borrow(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getInt("bookId"),
                        rs.getDate("borrowDate"),
                        rs.getDate("dueDate"),
                        rs.getString("username"),
                        rs.getString("title")
                );

                borrows.add(borrow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return borrows;
    }

    // ---------------------------------------------------------------------------------------------- //

    public void registerUser(String name, String password) {
        try {
            db.query("INSERT INTO users (name, password, admin) VALUES (?, ?, ?);", name, password, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertBook(String title, String author, int year, int pageCount) {
        try {
            db.query("INSERT INTO books (title, author, year, pageCount) VALUES (?, ?, ?, ?);",
                    title, author, year, pageCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrowBook(User user, Book book) {
        long millis = System.currentTimeMillis();

        Date curDate = new Date(millis);
        Date dueDate = new Date(millis + TimeUnit.DAYS.toMillis(7L));

        try {
            db.query("INSERT INTO borrows (userId, bookId, borrowDate, dueDate) VALUES (?, ?, ?, ?);",
                    user.getId(), book.getId(), curDate, dueDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------------------------------- //

    public void removeUser(int id) {
        // deletes every reference from `borrows` table
        try {
            db.query("DELETE FROM borrows WHERE userId = ?;", id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.query("DELETE FROM users WHERE id = ?;", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeBook(int id) {
        // deletes every reference from `borrows` table
        try {
            db.query("DELETE FROM borrows WHERE bookId = ?;", id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            db.query("DELETE FROM books WHERE id = ?;", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeBorrowedBook(int id) {
        try {
            db.query("DELETE FROM borrowed WHERE id = ?;", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
