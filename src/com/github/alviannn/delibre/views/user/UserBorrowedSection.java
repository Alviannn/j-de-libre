package com.github.alviannn.delibre.views.user;

import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.views.UserHomeView;

public class UserBorrowedSection extends AbstractHomeSection {

    public UserBorrowedSection(UserHomeView view) {
        super(view, view.borrowedBtn, AbstractHomeView.BORROWED);
    }

    @Override
    protected void makeDetails() {

    }

    @Override
    public void clearDetailsFields() {

    }

}
