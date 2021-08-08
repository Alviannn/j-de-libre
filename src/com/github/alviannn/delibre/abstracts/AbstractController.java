package com.github.alviannn.delibre.abstracts;

import com.github.alviannn.delibre.Main;

public abstract class AbstractController {

    protected final Main main;

    public AbstractController(Main main) {
        this.main = main;
    }

    /**
     * Shows the view to the user
     * and also add listeners to components within this view
     */
    public abstract void showView();

}
