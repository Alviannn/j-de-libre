package com.github.alviannn.delibre.views.admin;

import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.views.AdminHomeView;

import javax.swing.*;

public class AdminUserSection extends AbstractHomeSection {

    public JButton deleteBtn;
    public JTextField idField, nameField, registeredField;

    public AdminUserSection(AdminHomeView view) {
        super(view, view.usersBtn, AbstractHomeView.USER);

        idField = new JTextField();
        idField.setEditable(false);
        nameField = new JTextField();
        nameField.setEditable(false);
        registeredField = new JTextField();
        registeredField.setEditable(false);

        deleteBtn = new JButton("Delete");
    }

    @Override
    protected void makeDetails() {
        JPanel details = view.detailsPanel;

        JLabel idLabel = new JLabel("ID"),
                nameLabel = new JLabel("Username"),
                registeredLabel = new JLabel("Registered Date");

        int formCenter = (695 - 300) / 2;
        int formGap = 35;
        idLabel.setBounds(formCenter, 20, 130, 50);
        idField.setBounds(formCenter + 100, 30, 200, 30);

        nameLabel.setBounds(formCenter, 20 + formGap, 130, 50);
        nameField.setBounds(formCenter + 100, 30 + formGap, 200, 30);

        registeredLabel.setBounds(formCenter, 20 + (formGap * 2), 130, 50);
        registeredField.setBounds(formCenter + 100, 30 + (formGap * 2), 200, 30);

        details.add(idLabel);
        details.add(idField);

        details.add(nameLabel);
        details.add(nameField);

        details.add(registeredLabel);
        details.add(registeredField);

        int btnWidth = 130;
        int btnCenter = ((695 - btnWidth) / 2) - 70;
        int btnGap = 10;
        deleteBtn.setBounds(btnCenter, 160, 130, 40);
        clearBtn.setBounds(btnCenter + btnWidth + btnGap, 160, btnWidth, 40);

        details.add(deleteBtn);
        details.add(clearBtn);
    }

    @Override
    public void clearDetailsFields() {
        idField.setText("");
        nameField.setText("");
        registeredField.setText("");
    }

}
