package com.github.alviannn.delibre;

import com.github.alviannn.delibre.abstracts.AbstractHomeController;
import com.github.alviannn.delibre.controllers.AdminHomeController;
import com.github.alviannn.delibre.controllers.AuthController;
import com.github.alviannn.delibre.controllers.ModelHelper;
import com.github.alviannn.delibre.controllers.UserHomeController;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.sql.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

    private final AuthController auth;
    private final ModelHelper modelHelper;
    private final Database db;
    private final AbstractHomeController admin, user;

    private User currentUser;

    public Main() {
        this.db = new Database();
        db.connect();

        try {
            db.query(new File("init.sql"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        this.modelHelper = new ModelHelper(db);

        this.auth = new AuthController(this);
        this.admin = new AdminHomeController(this);
        this.user = new UserHomeController(this);

        auth.showView();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public AbstractHomeController getAdmin() {
        return admin;
    }

    public AbstractHomeController getUser() {
        return user;
    }

    public AuthController getAuth() {
        return auth;
    }

    public ModelHelper getModelHelper() {
        return modelHelper;
    }

    public Database getDB() {
        return db;
    }

    public static void main(String[] args) {
        new Main();
    }

}
