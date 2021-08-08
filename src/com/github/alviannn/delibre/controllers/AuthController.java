package com.github.alviannn.delibre.controllers;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.abstracts.AbstractController;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.sql.Database;
import com.github.alviannn.delibre.sql.Results;
import com.github.alviannn.delibre.views.AuthView;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthController extends AbstractController {

    public AuthController(Database db, Main main) {
        super(db, main);
    }

    @Override
    public void showView() {
        AuthView view = new AuthView();
        view.setVisible(true);

        view.loginBtn.addActionListener(e -> {
            String title = "Login";
            String name = view.userField.getText();
            String pwd = view.pwdField.getText();

            if (name.isEmpty() || pwd.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username or password cannot be empty!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            User found = this.findUser(name);
            if (found == null) {
                JOptionPane.showMessageDialog(null, "No user found!", title, JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (found.getPassword().equals(pwd)) {
                JOptionPane.showMessageDialog(null, "Successfully logged in!", title, JOptionPane.INFORMATION_MESSAGE);
                view.dispose();
                main.getHome().showView();
            } else {
                JOptionPane.showMessageDialog(null, "Username or password does not match!", title, JOptionPane.ERROR_MESSAGE);
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

            User found = this.findUser(name);
            if (found != null) {
                JOptionPane.showMessageDialog(null, "User already registered!", title, JOptionPane.ERROR_MESSAGE);
            } else {
                this.registerUser(name, pwd);
                JOptionPane.showMessageDialog(null, "Successfully registered a new user! Please login after this!", title, JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Executes the user registration query
     *
     * @param name the username
     * @param password the password
     */
    public void registerUser(String name, String password) {
        try {
            db.query("INSERT INTO users (name, password, admin) VALUES (?, ?, ?);", name, password, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to find a user within the database
     *
     * @param name the username
     * @return the user object if exists, null is otherwise
     */
    @Nullable
    public User findUser(String name) {
        if (name.isEmpty()) {
            return null;
        }

        try (Results res = db.results("SELECT * FROM users WHERE name = ?;", name)) {
            ResultSet rs = res.getResultSet();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getDate("registerDate"),
                        rs.getBoolean("admin"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Results res = db.results("SELECT * FROM users;")) {
            ResultSet rs = res.getResultSet();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getDate("registerDate"),
                        rs.getBoolean("admin"));

                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

}
