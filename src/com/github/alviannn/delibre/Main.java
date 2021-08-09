package com.github.alviannn.delibre;

import com.github.alviannn.delibre.controllers.AuthController;
import com.github.alviannn.delibre.controllers.HomeController;
import com.github.alviannn.delibre.controllers.ModelHelper;
import com.github.alviannn.delibre.sql.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

    private final AuthController auth;
    private final HomeController home;
    private final ModelHelper modelHelper;
    private final Database db;

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
        this.home = new HomeController(this);

        auth.showView();
    }

    public AuthController getAuth() {
        return auth;
    }

    public HomeController getHome() {
        return home;
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
