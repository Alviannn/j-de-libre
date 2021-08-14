package com.github.alviannn.delibre.controllers;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.abstracts.AbstractController;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.views.AuthView;

import javax.swing.*;

public class AuthController extends AbstractController {

    public AuthController(Main main) {
        super(main);
    }

    @Override
    public void showView() {
        ModelHelper helper = main.getModelHelper();
        AuthView view = new AuthView();
        view.setVisible(true);

        view.loginBtn.addActionListener(e -> {
            String title = "Login";
            String name = view.userField.getText();
            String pwd = view.pwdField.getText();

            if (name.isEmpty() || pwd.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Username or password cannot be empty!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            User found = helper.findUser(name);
            if (found == null) {
                JOptionPane.showMessageDialog(view, "No user found!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (found.getPassword().equals(pwd)) {
                JOptionPane.showMessageDialog(view, "Successfully logged in!", title, JOptionPane.INFORMATION_MESSAGE);
                view.dispose();

                main.setCurrentUser(found);
                if (found.isAdmin()) {
                    main.getAdminController().showView();
                } else {
                    main.getUserController().showView();
                }
            } else {
                JOptionPane.showMessageDialog(view, "Username or password does not match!", title, JOptionPane.ERROR_MESSAGE);
            }
        });

        view.registerBtn.addActionListener(e -> {
            String title = "Register";
            String name = view.userField.getText();
            String pwd = view.pwdField.getText();

            if (name.isEmpty() || pwd.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username or password cannot be empty!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            User found = helper.findUser(name);
            if (found != null) {
                JOptionPane.showMessageDialog(null, "User already registered!", title, JOptionPane.ERROR_MESSAGE);
            } else {
                helper.registerUser(name, pwd);
                JOptionPane.showMessageDialog(null, "Successfully registered a new user! Please login after this!", title, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

}
