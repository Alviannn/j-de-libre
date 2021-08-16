package com.github.alviannn.delibre.views.user;

import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.views.UserHomeView;

import javax.swing.*;

public class UserBookSection extends AbstractHomeSection {

    public JButton borrowBtn;
    public JTextField idField, titleField, authorField, yearField, pageField;

    public UserBookSection(UserHomeView view) {
        super(view, view.booksBtn, AbstractHomeView.BOOK);

        idField = new JTextField();
        idField.setEditable(false);
        titleField = new JTextField();
        authorField = new JTextField();
        yearField = new JTextField();
        pageField = new JTextField();

        borrowBtn = new JButton("Borrow Book");
    }

    @Override
    protected void makeDetails() {
        JPanel details = view.detailsPanel;

        JLabel idLabel = new JLabel("ID"),
                titleLabel = new JLabel("Title"),
                authorLabel = new JLabel("Author"),
                yearLabel = new JLabel("Year"),
                pageLabel = new JLabel("Page");

        int formCenter = 40;
        int formGap = 35;
        idLabel.setBounds(30 + formCenter, 20, 130, 50);
        idField.setBounds(80 + formCenter, 30, 200, 30);

        titleLabel.setBounds(30 + formCenter, 20 + formGap, 130, 50);
        titleField.setBounds(80 + formCenter, 30 + formGap, 200, 30);

        authorLabel.setBounds(30 + formCenter, 20 + (formGap * 2), 130, 50);
        authorField.setBounds(80 + formCenter, 30 + (formGap * 2), 200, 30);

        details.add(idLabel);
        details.add(idField);

        details.add(titleLabel);
        details.add(titleField);

        details.add(authorLabel);
        details.add(authorField);

        yearLabel.setBounds(30 + 270 + formCenter, 20, 130, 50);
        yearField.setBounds(80 + 270 + formCenter, 30, 200, 30);

        pageLabel.setBounds(30 + 270 + formCenter, 20 + formGap, 130, 50);
        pageField.setBounds(80 + 270 + formCenter, 30 + formGap, 200, 30);

        details.add(yearLabel);
        details.add(yearField);

        details.add(pageLabel);
        details.add(pageField);

        int btnWidth = 130;
        int btnGap = 10;

        borrowBtn.setBounds(202 + btnGap, 160, btnWidth, 40);
        clearBtn.setBounds(202 + btnWidth + (btnGap * 2), 160, 130, 40);

        details.add(borrowBtn);
        details.add(clearBtn);
    }

    @Override
    public void clearDetailsFields() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        pageField.setText("");
    }

}
