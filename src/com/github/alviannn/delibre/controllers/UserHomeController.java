package com.github.alviannn.delibre.controllers;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.abstracts.AbstractHomeController;
import com.github.alviannn.delibre.sql.Database;
import com.github.alviannn.delibre.views.UserHomeView;

public class UserHomeController extends AbstractHomeController {

    public UserHomeController(Main main) {
        super(main);
    }

    @Override
    public void showView() {
        ModelHelper helper = main.getModelHelper();
        Database db = main.getDB();

        UserHomeView view = new UserHomeView();
        view.setVisible(true);

        view.booksBtn.addActionListener(e -> {
        });
        view.borrowedBtn.addActionListener(e -> {
        });
        // user's view shouldn't have any 'Users' menu
        view.remove(view.usersBtn);

        this.refreshTable(view);
    }

}
