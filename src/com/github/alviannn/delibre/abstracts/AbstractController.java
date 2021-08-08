package com.github.alviannn.delibre.abstracts;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.sql.Database;

public abstract class AbstractController {

    protected final Database db;
    protected final Main main;

    public AbstractController(Database db, Main main) {
        this.db = db;
        this.main = main;
    }

    /**
     * Shows the view to the user
     * and also add listeners to components within this view
     */
    public abstract void showView();

}
