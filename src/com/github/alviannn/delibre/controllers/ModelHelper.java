package com.github.alviannn.delibre.controllers;

import com.github.alviannn.delibre.models.Book;
import com.github.alviannn.delibre.models.Borrow;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.sql.Database;
import com.github.alviannn.delibre.sql.Results;
import com.github.alviannn.delibre.util.SortType;
import com.sun.istack.internal.Nullable;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ModelHelper {

    private final Database db;

    private final String BOOK_SELECT_QUERY = "SELECT * FROM books";
    private final String USER_SELECT_QUERY = "SELECT * FROM users";
    private final String BORROW_SELECT_QUERY = "SELECT" +
                                               " borrows.*, users.name AS username, books.title AS title " +
                                               "FROM borrows" +
                                               " JOIN users ON borrows.userId = users.id" +
                                               " JOIN books ON borrows.bookId = books.id";

    public ModelHelper(Database db) {
        this.db = db;
    }

    // ---------------------------------------------------------------------------------------------- //

    /**
     * Wraps the SQL result query to make shorter code
     * <br><br>
     * Okay, but why is it really needed? Because to make the filter section works
     * it needs {@code SortTypes} and {@code Categories}. To do that we need to insert some additional queries
     * <br><br>
     * Those required additional queries are:
     * <ol>
     *     <li>For sorting (ex: {@code ORDER BY column ASC})</li>
     *     <li>For searching (ex: {@code WHERE column LIKE ?})</li>
     * </ol>
     * <br>
     * And by using this we can insert those additional queries to all {@code SELECT} queries and make our code shorter
     * since we're going to use it repeatedly anyways.
     *
     * @param modelName   the selected model name (later will pick the query either
     *                    {@link #BOOK_SELECT_QUERY} or
     *                    {@link #USER_SELECT_QUERY} or
     *                    {@link #BORROW_SELECT_QUERY})
     * @param sortQuery   the additional sort query (for sorting purpose)
     * @param column      the selected column or field (for either sort or search)
     * @param filterValue the search value from the search field (for filtering through the DB)
     * @see Database#results
     */
    private Results wrappedResults(String modelName, String sortQuery, String column, String filterValue) throws SQLException {
        String conditionalQuery = " WHERE " + column + " LIKE ?";

        if (modelName.matches("borrow::user-\\d+")) {
            String userIdText = modelName.split("-")[1];
            int userId = Integer.parseInt(userIdText);

            modelName = "borrow";
            conditionalQuery += " AND userId = " + userId;
        }

        String query;
        switch (modelName) {
            case "book":
                query = BOOK_SELECT_QUERY;
                break;
            case "user":
                query = USER_SELECT_QUERY;
                break;
            case "borrow":
                query = BORROW_SELECT_QUERY;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + modelName);
        }

        query += "%s %s";
        return db.results(String.format(query, conditionalQuery, sortQuery), "%" + filterValue + "%");
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

        try (Results res = db.results(USER_SELECT_QUERY + " WHERE name = ?;", name)) {
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

    /**
     * Tries to find a borrow object within the database
     *
     * @param id the borrow object ID
     * @return the borrowed object if exists, null is otherwise
     */
    public Borrow findBorrowedBook(int id) {
        try (Results res = db.results(BORROW_SELECT_QUERY + " WHERE borrows.id = ?", id)) {
            ResultSet rs = res.getResultSet();
            if (rs.next()) {
                return new Borrow(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getInt("bookId"),
                        rs.getDate("borrowDate"),
                        rs.getDate("dueDate"),
                        rs.getString("username"),
                        rs.getString("title")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Determines if a book is already registered to the DB.
     * A book is determined as already registered if both title and author is in the database.
     *
     * @param title  the book title
     * @param author the book author
     * @return true if book is already registered, false is otherwise
     */
    public boolean doesBookExists(String title, String author) {
        try (Results res = db.results(BOOK_SELECT_QUERY + " WHERE title = ? AND author = ?;", title, author)) {
            ResultSet rs = res.getResultSet();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Determines if a user already borrowed a book
     *
     * @param userId the user id
     * @param bookId the book id
     * @return true if the user already borrowed the book, false is otherwise
     */
    public boolean isBookBorrowed(int userId, int bookId) {
        try (Results res = db.results(BORROW_SELECT_QUERY + " WHERE borrows.userId = ? AND borrows.bookId = ?;", userId, bookId)) {
            ResultSet rs = res.getResultSet();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ---------------------------------------------------------------------------------------------- //

    public List<User> getAllUsers(SortType sort, String column, String filter) {
        List<User> users = new ArrayList<>();
        String sortQuery = sort.makeQuery(column);

        try (Results res = this.wrappedResults("user", sortQuery, column, filter)) {
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

    public List<Book> getAllBooks(SortType sort, String column, String filter) {
        List<Book> books = new ArrayList<>();
        String sortQuery = sort.makeQuery(column);

        try (Results res = this.wrappedResults("book", sortQuery, column, filter)) {
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

    public List<Borrow> getAllBorrowed(SortType sort, String column, String filter) {
        List<Borrow> borrows = new ArrayList<>();
        String sortQuery = sort.makeQuery(column);

        try (Results results = this.wrappedResults("borrow", sortQuery, column, filter)) {
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

    /**
     * The difference between this and {@link #getAllBorrowed}, this one is for user specific.
     * In the {@link #getAllBorrowed} you can get all borrow objects, but not for a specific user.
     */
    public List<Borrow> getAllBorrowedUser(SortType sort, String column, String filter, User user) {
        List<Borrow> borrows = new ArrayList<>();

        String sortQuery = sort.makeQuery(column);
        String tableQuery = "borrow::user-" + user.getId();

        try (Results results = this.wrappedResults(tableQuery, sortQuery, column, filter)) {
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

    public void borrowBook(int userId, int bookId) {
        long millis = System.currentTimeMillis();

        Date curDate = new Date(millis);
        Date dueDate = new Date(millis + TimeUnit.DAYS.toMillis(7L));

        try {
            db.query("INSERT INTO borrows (userId, bookId, borrowDate, dueDate) VALUES (?, ?, ?, ?);",
                    userId, bookId, curDate, dueDate);
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
            db.query("DELETE FROM borrows WHERE id = ?;", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
