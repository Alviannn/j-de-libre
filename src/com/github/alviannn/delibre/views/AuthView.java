package com.github.alviannn.delibre.views;

import com.github.alviannn.delibre.abstracts.AbstractView;

import javax.swing.*;
import java.awt.*;

public class AuthView extends AbstractView {

    public JButton loginBtn, registerBtn;
    public JTextField userField, pwdField;

    public AuthView() {
        super(400, 500);
    }

    @Override
    protected void buildFrame() {
        JLabel userLabel = new JLabel("USERNAME", JLabel.CENTER),
                pwdLabel = new JLabel("PASSWORD", JLabel.CENTER);

        userField = new JTextField();
        pwdField = new JPasswordField();

        loginBtn = new JButton("Login");
        registerBtn = new JButton("Register");

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 2, -40, 10));

        formPanel.add(userLabel);
        formPanel.add(userField);

        formPanel.add(pwdLabel);
        formPanel.add(pwdField);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);

        formPanel.setBorder(BorderFactory.createEmptyBorder(120, 30, 0, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(60, 50, 110, 50));

        this.setLayout(new GridLayout(2, 1, 0, 20));

        this.add(formPanel);
        this.add(buttonPanel);
    }

}
