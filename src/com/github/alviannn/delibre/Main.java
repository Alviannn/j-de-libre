package com.github.alviannn.delibre;

import com.github.alviannn.delibre.controllers.AuthController;
import com.github.alviannn.delibre.controllers.HomeController;
import com.github.alviannn.delibre.sql.Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

    private final AuthController auth;
    private final HomeController home;

    public Main() {
        Database db = new Database();
        db.connect();

        try {
            db.query(new File("init.sql"));
        } catch (FileNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        this.auth = new AuthController(db, this);
        this.home = new HomeController(db, this);

        auth.showView();
//        home.showView();
    }

    public AuthController getAuth() {
        return auth;
    }

    public HomeController getHome() {
        return home;
    }

    public static void main(String[] args) {
        new Main();
    }

}
