package com.github.alviannn.delibre.controllers;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.abstracts.AbstractController;
import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.models.Book;
import com.github.alviannn.delibre.models.Borrow;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.views.AdminHomeView;
import com.github.alviannn.delibre.views.admin.AdminBookSection;
import com.github.alviannn.delibre.views.admin.AdminBorrowedSection;
import com.github.alviannn.delibre.views.admin.AdminUserSection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class HomeController extends AbstractController {

    private User currentUser;

    public HomeController(Main main) {
        super(main);
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void showView() {
        ModelHelper helper = main.getModelHelper();

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

        AbstractHomeSection[] sections = {view.bookSection, view.userSection, view.borrowedSection};
        for (AbstractHomeSection tmp : sections) {
            tmp.clearBtn.addActionListener(e -> {
                tmp.clearDetailsFields();
                table.clearSelection();
            });
        }

        view.bookSection.deleteBtn.addActionListener(e -> {
            AdminBookSection section = view.bookSection;

            String title = "Book Deletion";
            String idString = section.idField.getText();

            if (idString.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No data was selected!", title, JOptionPane.ERROR_MESSAGE);
            } else {
                int id = Integer.parseInt(idString);
                helper.removeBook(id);

                JOptionPane.showMessageDialog(null, "Successfully deleted the data!", title, JOptionPane.INFORMATION_MESSAGE);
            }

            section.clearDetailsFields();
            table.clearSelection();

            this.refreshTable(view);
        });

        view.userSection.deleteBtn.addActionListener(e -> {
            AdminUserSection section = view.userSection;

            String title = "User Deletion";
            String idString = section.idField.getText();

            if (idString.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No data was selected!", title, JOptionPane.ERROR_MESSAGE);
            } else {
                int id = Integer.parseInt(idString);
                helper.removeUser(id);

                JOptionPane.showMessageDialog(null, "Successfully deleted the data!", title, JOptionPane.INFORMATION_MESSAGE);
            }

            section.clearDetailsFields();
            table.clearSelection();

            this.refreshTable(view);
        });

        view.borrowedSection.deleteBtn.addActionListener(e -> {
            AdminBorrowedSection section = view.borrowedSection;

            String title = "Borrowed Book Deletion";
            String idString = section.idField.getText();

            if (idString.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No data was selected!", title, JOptionPane.ERROR_MESSAGE);
            } else {
                int id = Integer.parseInt(idString);
                helper.removeUser(id);

                JOptionPane.showMessageDialog(null, "Successfully deleted the data!", title, JOptionPane.INFORMATION_MESSAGE);
            }

            section.clearDetailsFields();
            table.clearSelection();

            this.refreshTable(view);
        });

        view.logoutBtn.addActionListener(e -> {
            view.dispose();
            main.getAuth().showView();
        });

        this.refreshTable(view);
    }

    private void refreshTable(AbstractHomeView view) {
        DefaultTableModel model = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ModelHelper helper = main.getModelHelper();

        switch (view.currentSection) {
            case AbstractHomeView.BOOK: {
                model = new DefaultTableModel(Book.Field.getFieldNames(), 0);
                List<Book> books = helper.getAllBooks();

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
                List<User> users = helper.getAllUsers();

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
                List<Borrow> borrows = helper.getAllBorrowed();

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
        view.table.setModel(model);
    }

    private void changeAdminSection(AdminHomeView view, int type) {
        view.disposeSections();

        AbstractHomeSection section = view.getSection(type);
        section.applyView();

        this.refreshTable(view);
        view.updateUI();
    }

}
