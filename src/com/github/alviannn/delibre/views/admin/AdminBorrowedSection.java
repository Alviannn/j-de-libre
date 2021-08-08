package com.github.alviannn.delibre.views.admin;

import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.views.AdminHomeView;

import javax.swing.*;

public class AdminBorrowedSection extends AbstractHomeSection {

    public JButton deleteBtn;
    public JTextField idField, userIdField, bookIdField,
            usernameField, bookTitleField,
            borrowDateField, dueDateField;

    public AdminBorrowedSection(AdminHomeView view) {
        super(view, view.borrowedBtn, AbstractHomeView.BORROWED);
    }

    @Override
    protected void makeDetails() {
        JPanel details = view.detailsPanel;

        idField = new JTextField();
        idField.setEditable(false);
        userIdField = new JTextField();
        userIdField.setEditable(false);
        bookIdField = new JTextField();
        bookIdField.setEditable(false);

        usernameField = new JTextField();
        usernameField.setEditable(false);
        bookTitleField = new JTextField();
        bookTitleField.setEditable(false);

        borrowDateField = new JTextField();
        borrowDateField.setEditable(false);
        dueDateField = new JTextField();
        dueDateField.setEditable(false);

        JLabel idLabel = new JLabel("ID"),
                userIdLabel = new JLabel("User ID"),
                bookIdLabel = new JLabel("Book ID"),

                usernameLabel = new JLabel("Username"),
                bookTitleLabel = new JLabel("Book Title"),

                borrowDateLabel = new JLabel("Borrow Date"),
                dueDateLabel = new JLabel("Due Date");

        deleteBtn = new JButton("Delete");
        clearBtn = new JButton("Clear");

        int formVertGap = 35;
        int formHorzGap = 350;
        idLabel.setBounds(40, 20, 130, 50);
        idField.setBounds(120, 30, 200, 30);

        userIdLabel.setBounds(40, 20 + formVertGap, 130, 50);
        userIdField.setBounds(120, 30 + formVertGap, 200, 30);

        bookIdLabel.setBounds(40, 20 + (formVertGap * 2), 130, 50);
        bookIdField.setBounds(120, 30 + (formVertGap * 2), 200, 30);

        details.add(idLabel);
        details.add(idField);
        details.add(userIdLabel);
        details.add(userIdField);
        details.add(bookIdLabel);
        details.add(bookIdField);

        usernameLabel.setBounds(10 + formHorzGap, 5, 130, 50);
        usernameField.setBounds(90 + formHorzGap, 15, 200, 30);

        bookTitleLabel.setBounds(10 + formHorzGap, 5 + formVertGap, 130, 50);
        bookTitleField.setBounds(90 + formHorzGap, 15 + formVertGap, 200, 30);

        details.add(usernameLabel);
        details.add(usernameField);
        details.add(bookTitleLabel);
        details.add(bookTitleField);

        borrowDateLabel.setBounds(10 + formHorzGap, 5 + 80, 130, 50);
        borrowDateField.setBounds(90 + formHorzGap, 15 + 80, 200, 30);

        dueDateLabel.setBounds(10 + formHorzGap, 5 + 80 + formVertGap, 130, 50);
        dueDateField.setBounds(90 + formHorzGap, 15 + 80 + formVertGap, 200, 30);

        details.add(borrowDateLabel);
        details.add(borrowDateField);
        details.add(dueDateLabel);
        details.add(dueDateField);

        int btnWidth = 130;
        int btnCenter = ((695 - btnWidth) / 2) - 70;
        int btnGap = 10;
        deleteBtn.setBounds(btnCenter, 170, 130, 40);
        clearBtn.setBounds(btnCenter + btnWidth + btnGap, 170, btnWidth, 40);

        details.add(deleteBtn);
        details.add(clearBtn);
    }

    @Override
    public void applyView() {
        super.applyView();

        // disable filters
        categoryField.setEnabled(false);
        sortTypeField.setEnabled(false);
        searchField.setEnabled(false);
        searchBtn.setEnabled(false);
        sortTypeField.removeAllItems();
    }

    @Override
    public void clearDetailsFields() {
        idField.setText("");
        userIdField.setText("");
        bookIdField.setText("");
        usernameField.setText("");
        bookTitleField.setText("");
        borrowDateField.setText("");
        dueDateField.setText("");
    }

}
