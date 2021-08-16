package com.github.alviannn.delibre.controllers;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.abstracts.AbstractHomeController;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.views.UserHomeView;

public class UserHomeController extends AbstractHomeController {

    public UserHomeController(Main main) {
        super(main);
    }

    @Override
    public void showView() {
        UserHomeView view = new UserHomeView();
        view.setVisible(true);

        view.booksBtn.addActionListener(e -> this.changeSectionAction(view, AbstractHomeView.BOOK));
        view.borrowedBtn.addActionListener(e -> this.changeSectionAction(view, AbstractHomeView.BORROWED));

        // user's view shouldn't have any 'Users' menu
        view.remove(view.usersBtn);

        this.refreshTable(view);
    }

}
