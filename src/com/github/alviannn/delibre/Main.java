package com.github.alviannn.delibre;

import com.github.alviannn.delibre.abstracts.AbstractHomeController;
import com.github.alviannn.delibre.controllers.AdminHomeController;
import com.github.alviannn.delibre.controllers.AuthController;
import com.github.alviannn.delibre.controllers.ModelHelper;
import com.github.alviannn.delibre.controllers.UserHomeController;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.sql.Database;

import java.io.File;

public class Main {

    private final AuthController auth;
    private final ModelHelper modelHelper;
    private final Database db;
    private final AbstractHomeController admin, user;

    private User currentUser;

    public Main() {
        this.db = new Database();

        try {
            db.connect();
            // execute the SQL initialization
            db.query(new File("init.sql"));
        } catch (Exception e) {
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

    public AbstractHomeController getAdminController() {
        return admin;
    }

    public AbstractHomeController getUserController() {
        return user;
    }

    public AuthController getAuthController() {
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
