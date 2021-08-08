package com.github.alviannn.delibre.views.admin;

import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.models.Book;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.views.AdminHomeView;

import javax.swing.*;

public class AdminBookSection extends AbstractHomeSection {

    public JButton saveBtn, deleteBtn, clearBtn;
    public JTextField idField, titleField, authorField, yearField, pageField;

    public AdminBookSection(AdminHomeView view) {
        super(view, view.booksBtn, AbstractHomeView.BOOK);
    }

    @Override
    protected void makeDetails() {
        JPanel details = view.detailsPanel;

        idField = new JTextField();
        idField.setEditable(false);
        titleField = new JTextField();
        authorField = new JTextField();
        yearField = new JTextField();
        pageField = new JTextField();

        JLabel idLabel = new JLabel("ID"),
                titleLabel = new JLabel("Title"),
                authorLabel = new JLabel("Author"),
                yearLabel = new JLabel("Year"),
                pageLabel = new JLabel("Page");

        saveBtn = new JButton("Save");
        deleteBtn = new JButton("Delete");
        clearBtn = new JButton("Clear");

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
        int btnCenter = (695 - btnWidth) / 2;
        int btnGap = 10;
        saveBtn.setBounds(btnCenter - btnWidth - btnGap, 160, btnWidth, 40);
        deleteBtn.setBounds(btnCenter, 160, 130, 40);
        clearBtn.setBounds(btnCenter + btnWidth + btnGap, 160, btnWidth, 40);

        details.add(saveBtn);
        details.add(deleteBtn);
        details.add(clearBtn);
    }

    @Override
    public void applyView() {
        view.currentSection = section;

        // clear the panels
        view.detailsPanel.removeAll();
        view.filterPanel.removeAll();

        // re-build the panels
        this.makeDetails();
        this.makeFilters(Book.Field.getFieldNames());

        // disable button
        mainBtn.setEnabled(false);
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
