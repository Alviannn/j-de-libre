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
     * Shows the VIEW to the user
     */
    public abstract void showView();

}
