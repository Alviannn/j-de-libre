package com.github.alviannn.delibre.controllers;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.abstracts.AbstractHomeController;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.models.Borrow;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.views.UserHomeView;
import com.github.alviannn.delibre.views.user.UserBookSection;
import com.github.alviannn.delibre.views.user.UserBorrowedSection;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserHomeController extends AbstractHomeController {

    public UserHomeController(Main main) {
        super(main);
    }

    @Override
    public void showView() {
        ModelHelper helper = main.getModelHelper();

        UserHomeView view = new UserHomeView();
        view.setVisible(true);

        view.booksBtn.addActionListener(e -> this.changeSectionAction(view, AbstractHomeView.BOOK));
        view.borrowedBtn.addActionListener(e -> this.changeSectionAction(view, AbstractHomeView.BORROWED));

        JTable table = view.table;
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();

                switch (view.currentSection) {
                    case AbstractHomeView.BOOK: {
                        UserBookSection book = view.bookSection;

                        book.idField.setText((String) table.getValueAt(row, 0));
                        book.titleField.setText((String) table.getValueAt(row, 1));
                        book.authorField.setText((String) table.getValueAt(row, 2));
                        book.yearField.setText((String) table.getValueAt(row, 3));
                        book.pageField.setText((String) table.getValueAt(row, 4));
                        break;
                    }
                    case AbstractHomeView.BORROWED: {
                        UserBorrowedSection borrowed = view.borrowedSection;

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

        this.useClearAction(view);
        this.useCategorySelectAction(view);
        this.useLogoutAction(view);

        view.searchBtn.addActionListener(e -> this.refreshTable(view));

        view.bookSection.borrowBtn.addActionListener(e -> {
            User user = main.getCurrentUser();

            String idText = view.bookSection.idField.getText();
            String title = "Borrowing Book";

            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No data was selected!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            int bookId = Integer.parseInt(idText);
            if (helper.isBookBorrowed(user.getId(), bookId)) {
                JOptionPane.showMessageDialog(view, "You have already borrowed this book!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            helper.borrowBook(user.getId(), bookId);
            JOptionPane.showMessageDialog(view, "Successfully borrowed a new book!", title, JOptionPane.PLAIN_MESSAGE);
        });

        view.borrowedSection.returnBtn.addActionListener(e -> {
            UserBorrowedSection section = view.borrowedSection;

            String title = "Returning Borrowed Book";
            String idText = section.idField.getText();

            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(view, "No data was selected!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = Integer.parseInt(idText);

            Borrow borrow = helper.findBorrowedBook(id);
            helper.removeBorrowedBook(id);

            JOptionPane.showMessageDialog(view, "Successfully returned the borrowed book!");
            // todo: show receipt
        });

        this.refreshTable(view);
    }

}
