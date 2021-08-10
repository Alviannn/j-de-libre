package com.github.alviannn.delibre.views.user;

import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.views.UserHomeView;

public class UserBookSection extends AbstractHomeSection {

    public UserBookSection(UserHomeView view) {
        super(view, view.booksBtn, AbstractHomeView.BOOK);
    }

    @Override
    protected void makeDetails() {

    }

    @Override
    public void clearDetailsFields() {

    }

}
