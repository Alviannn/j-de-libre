package com.github.alviannn.delibre.controllers;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.abstracts.AbstractController;
import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.models.Book;
import com.github.alviannn.delibre.models.Borrow;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.sql.Database;
import com.github.alviannn.delibre.sql.Results;
import com.github.alviannn.delibre.views.AdminHomeView;
import com.github.alviannn.delibre.views.admin.AdminBookSection;
import com.github.alviannn.delibre.views.admin.AdminBorrowedSection;
import com.github.alviannn.delibre.views.admin.AdminUserSection;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeController extends AbstractController {

    private User currentUser;

    public HomeController(Database db, Main main) {
        super(db, main);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void showView() {
        // todo: check if user an admin or not
        AdminHomeView view = new AdminHomeView();
        view.setVisible(true);

        view.booksBtn.addActionListener(e -> this.changeAdminSection(view, AbstractHomeView.BOOK));
        view.usersBtn.addActionListener(e -> this.changeAdminSection(view, AbstractHomeView.USER));
        view.borrowedBtn.addActionListener(e -> this.changeAdminSection(view, AbstractHomeView.BORROWED));

        JTable table = view.table;
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                switch (view.currentSection) {
                    case AbstractHomeView.BOOK: {
                        AdminBookSection book = view.bookSection;

                        book.idField.setText((String) table.getValueAt(row, 0));
                        book.titleField.setText((String) table.getValueAt(row, 1));
                        book.authorField.setText((String) table.getValueAt(row, 2));
                        book.yearField.setText((String) table.getValueAt(row, 3));
                        book.pageField.setText((String) table.getValueAt(row, 4));
                        break;
                    }
                    case AbstractHomeView.USER: {
                        AdminUserSection user = view.userSection;

                        user.idField.setText((String) table.getValueAt(row, 0));
                        user.nameField.setText((String) table.getValueAt(row, 1));
                        user.registeredField.setText((String) table.getValueAt(row, 2));
                        break;
                    }
                    case AbstractHomeView.BORROWED: {
                        AdminBorrowedSection borrowed = view.borrowedSection;

                        borrowed.idField.setText((String) table.getValueAt(row, 0));
                        borrowed.userIdField.setText((String) table.getValueAt(row, 1));
                        borrowed.bookIdField.setText((String) table.getValueAt(row, 2));
                        borrowed.borrowDateField.setText((String) table.getValueAt(row, 3));
                        borrowed.dueDateField.setText((String) table.getValueAt(row, 4));
                        borrowed.usernameField.setText((String) table.getValueAt(row, 5));
                        borrowed.bookTitleField.setText((String) table.getValueAt(row, 6));
                        break;
                    }
                    default:
                        throw new IllegalStateException("Unexpected value: " + view.currentSection);
                }
            }
        });

        this.refreshTable(view);

        AbstractHomeSection[] sections = {view.bookSection, view.userSection, view.borrowedSection};
        for (AbstractHomeSection tmp : sections) {
            tmp.clearBtn.addActionListener(e -> {
                tmp.clearDetailsFields();
                table.clearSelection();
            });
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

    @Nullable
    public Book findBook(String title) {
        try (Results res = db.results("SELECT * FROM books WHERE title = ?;", title)) {
            ResultSet rs = res.getResultSet();
            if (rs.next()) {
                return new Book(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year"),
                        rs.getInt("pageCount"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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

    private void refreshTable(AbstractHomeView view) {
        // todo: check if view is admin or user
        DefaultTableModel model = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        switch (view.currentSection) {
            case AbstractHomeView.BOOK: {
                model = new DefaultTableModel(Book.Field.getFieldNames(), 0);
                List<Book> books = this.getAllBooks();

                for (Book book : books) {
                    model.addRow(new Object[]{
                            book.getId() + "",
                            book.getTitle(), book.getAuthor(),
                            book.getYear() + "", book.getPageCount() + ""});
                }
                break;
            }
            case AbstractHomeView.USER: {
                model = new DefaultTableModel(User.Field.getFieldNames(), 0);
                List<User> users = main.getAuth().getAllUsers();

                for (User user : users) {
                    model.addRow(new Object[]{
                            user.getId() + "",
                            user.getName(),
                            dateFormat.format(user.getRegisteredDate())});
                }
                break;
            }
            case AbstractHomeView.BORROWED: {
                model = new DefaultTableModel(Borrow.Field.getFieldNames(), 0);
                List<Borrow> borrows = this.getAllBorrowed();

                for (Borrow borrow : borrows) {
                    model.addRow(new Object[]{
                            borrow.getId() + "",
                            borrow.getUserId() + "",
                            borrow.getBookId() + "",
                            dateFormat.format(borrow.getBorrowDate()),
                            dateFormat.format(borrow.getDueDate()),
                            borrow.getUsername(),
                            borrow.getBookTitle()
                    });
                }
                break;
            }
        }

        assert model != null;
        JTable table = view.table;
        table.setModel(model);
    }

    private void changeAdminSection(AdminHomeView view, int type) {
        view.disposeSections();

        AbstractHomeSection section = view.getSection(type);
        section.applyView();

        this.refreshTable(view);
        view.updateUI();
    }

}
