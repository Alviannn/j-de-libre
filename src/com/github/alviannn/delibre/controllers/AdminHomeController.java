package com.github.alviannn.delibre.controllers;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.abstracts.AbstractHomeController;
import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.models.Book;
import com.github.alviannn.delibre.models.Borrow;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.sql.Database;
import com.github.alviannn.delibre.util.Utils;
import com.github.alviannn.delibre.views.AdminHomeView;
import com.github.alviannn.delibre.views.admin.AdminBookSection;
import com.github.alviannn.delibre.views.admin.AdminBorrowedSection;
import com.github.alviannn.delibre.views.admin.AdminUserSection;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class AdminHomeController extends AbstractHomeController {

    public AdminHomeController(Main main) {
        super(main);
    }

    @Override
    public void showView() {
        AdminHomeView view = new AdminHomeView();
        view.setVisible(true);

        view.booksBtn.addActionListener(e -> this.changeSectionAction(view, AbstractHomeView.BOOK));
        view.usersBtn.addActionListener(e -> this.changeSectionAction(view, AbstractHomeView.USER));
        view.borrowedBtn.addActionListener(e -> this.changeSectionAction(view, AbstractHomeView.BORROWED));

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

        // combined to an array and loop it to simplify codes
        AbstractHomeSection[] sectionArr = {view.bookSection, view.userSection, view.borrowedSection};
        // insert action to clear button (to all sections)
        for (AbstractHomeSection section : sectionArr) {
            section.clearBtn.addActionListener(e -> view.clearSelection());
        }

        view.categoryField.addActionListener(e -> {
            String item = (String) view.categoryField.getSelectedItem();
            boolean canSearch = true;

            switch (view.currentSection) {
                case AbstractHomeView.BOOK:
                    canSearch = Book.Field.fromName(item).isSearchable();
                    break;
                case AbstractHomeView.USER:
                    canSearch = User.Field.fromName(item).isSearchable();
                    break;
                case AbstractHomeView.BORROWED:
                    canSearch = Borrow.Field.fromName(item).isSearchable();
                    break;
            }

            view.searchField.setText("");
            view.searchField.setEnabled(canSearch);
        });

        view.searchBtn.addActionListener(e -> this.refreshTable(view));

        view.bookSection.deleteBtn.addActionListener(e -> this.deleteSectionItemAction(view, AbstractHomeView.BOOK));
        view.userSection.deleteBtn.addActionListener(e -> this.deleteSectionItemAction(view, AbstractHomeView.USER));
        view.borrowedSection.deleteBtn.addActionListener(e -> this.deleteSectionItemAction(view, AbstractHomeView.BORROWED));

        view.bookSection.insertBtn.addActionListener(e -> {
            ModelHelper helper = main.getModelHelper();
            AdminBookSection section = view.bookSection;

            String title = "Insert Book";

            if (!section.idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please 'clear' the fields to insert a book!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!this.isBookFieldValid(view, section, title)) {
                return;
            }

            helper.insertBook(
                    section.titleField.getText(),
                    section.authorField.getText(),
                    Integer.parseInt(section.yearField.getText()),
                    Integer.parseInt(section.pageField.getText())
            );

            view.clearSelection();
            this.refreshTable(view);
        });

        view.bookSection.saveBtn.addActionListener(e -> {
            Database db = main.getDB();
            AdminBookSection section = view.bookSection;

            String title = "Saving Book";
            String idText = section.idField.getText();

            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No data was selected!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!this.isBookFieldValid(view, section, title)) {
                return;
            }

            try {
                db.query("UPDATE books SET title = ?, author = ?, year = ?, pageCount = ? WHERE id = ?;",
                        section.titleField.getText(), section.authorField.getText(),
                        section.yearField.getText(), section.pageField.getText(),
                        section.idField.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            view.clearSelection();
            this.refreshTable(view);
        });

        view.logoutBtn.addActionListener(e -> {
            view.dispose();
            main.setCurrentUser(null);
            main.getAuthController().showView();
        });

        this.refreshTable(view);
    }

    /**
     * Action that deletes the currently selected item within a section
     * <p>
     * Automatically picks the currently applied section and decides which data should be deleted
     * based on the provided {@code type}.
     */
    private void deleteSectionItemAction(AdminHomeView view, int type) {
        ModelHelper helper = main.getModelHelper();
        String title, idString;

        switch (type) {
            case AbstractHomeView.BOOK:
                title = "Book Deletion";
                idString = view.bookSection.idField.getText();
                break;
            case AbstractHomeView.USER:
                title = "User Deletion";
                idString = view.userSection.idField.getText();
                break;
            case AbstractHomeView.BORROWED:
                title = "Borrowed Book Deletion";
                idString = view.borrowedSection.idField.getText();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        if (idString.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No data was selected!", title, JOptionPane.ERROR_MESSAGE);
        } else {
            int id = Integer.parseInt(idString);
            switch (type) {
                case AbstractHomeView.BOOK:
                    helper.removeBook(id);
                    break;
                case AbstractHomeView.USER:
                    helper.removeUser(id);
                    break;
                case AbstractHomeView.BORROWED:
                    helper.removeBorrowedBook(id);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }

            JOptionPane.showMessageDialog(view, "Successfully deleted the data!", title, JOptionPane.INFORMATION_MESSAGE);
        }

        view.getSection(type).clearDetailsFields();
        view.table.clearSelection();

        this.refreshTable(view);
    }

    private boolean isBookFieldValid(AdminHomeView view, AdminBookSection section, String dialogTitle) {
        String yearText = section.yearField.getText(),
                pageText = section.pageField.getText();

        if (!Utils.isInt(yearText) || !Utils.isInt(pageText)) {
            JOptionPane.showMessageDialog(view, "Year or Page must be integer!", dialogTitle, JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String titleText = section.titleField.getText(),
                authorText = section.authorField.getText();

        if (titleText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Title cannot be empty!");
            return false;
        }
        if (authorText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Author cannot be empty!");
            return false;
        }

        return true;
    }

}
