package com.github.alviannn.delibre.controllers;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.abstracts.AbstractHomeController;
import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
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
        ModelHelper helper = main.getModelHelper();
        Database db = main.getDB();

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

        AbstractHomeSection[] sectionArr = {view.bookSection, view.userSection, view.borrowedSection};
        for (AbstractHomeSection tmp : sectionArr) {
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
                helper.removeBook(Integer.parseInt(idString));
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
                helper.removeUser(Integer.parseInt(idString));
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
                helper.removeBorrowedBook(Integer.parseInt(idString));
                JOptionPane.showMessageDialog(null, "Successfully deleted the data!", title, JOptionPane.INFORMATION_MESSAGE);
            }

            section.clearDetailsFields();
            table.clearSelection();

            this.refreshTable(view);
        });

        view.bookSection.saveBtn.addActionListener(e -> {
            AdminBookSection section = view.bookSection;

            String title = "Saving Book";
            String idText = section.idField.getText();

            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No data was selected!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            String yearString = section.yearField.getText(),
                    pageString = section.pageField.getText();

            if (!Utils.isInt(yearString) || !Utils.isInt(pageString)) {
                JOptionPane.showMessageDialog(null, "Year or Page field must be integer!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                db.query("UPDATE books SET title = ?, author = ?, year = ?, pageCount = ? WHERE id = ?;",
                        section.titleField.getText(), section.authorField.getText(),
                        yearString, pageString,
                        section.idField.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            section.clearDetailsFields();
            table.clearSelection();

            this.refreshTable(view);
        });
    }

    private void changeAdminSection(AdminHomeView view, int type) {
        view.disposeSections();

        AbstractHomeSection section = view.getSection(type);
        section.applyView();

        this.refreshTable(view);
        view.updateUI();
    }

}
