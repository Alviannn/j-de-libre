package com.github.alviannn.delibre.views;

import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.views.user.UserBookSection;
import com.github.alviannn.delibre.views.user.UserBorrowedSection;

import javax.swing.*;

public class UserHomeView extends AbstractHomeView {

    public UserBookSection bookSection;
    public UserBorrowedSection borrowedSection;

    @Override
    public AbstractHomeSection getSection(int type) {
        switch (type) {
            case BOOK:
                return bookSection;
            case BORROWED:
                return borrowedSection;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    @Override
    protected void buildFrame() {
        super.buildFrame();

        this.setTitle("De Libre - User");

        // remove 'Users' button from the menu panel because the user view won't have any use for it
        // probably going to change it later if I'm going to add a "change password" feature.
        JPanel firstPanel = (JPanel) menuPanel.getComponents()[0];
        firstPanel.remove(usersBtn);

        bookSection = new UserBookSection(this);
        borrowedSection = new UserBorrowedSection(this);

        // apply the default section
        bookSection.applyView();
    }

}
