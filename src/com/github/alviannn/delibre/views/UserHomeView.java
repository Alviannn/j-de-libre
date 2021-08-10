package com.github.alviannn.delibre.views;

import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.views.user.UserBookSection;
import com.github.alviannn.delibre.views.user.UserBorrowedSection;

public class UserHomeView extends AbstractHomeView {

    public UserBookSection bookSection;
    public UserBorrowedSection borrowedSection;

    @Override
    public AbstractHomeSection getAppliedSection() {
        switch (currentSection) {
            case BOOK:
                return bookSection;
            case BORROWED:
                return borrowedSection;
            default:
                throw new IllegalStateException("Unexpected value: " + currentSection);
        }
    }

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

        bookSection = new UserBookSection(this);
        borrowedSection = new UserBorrowedSection(this);

        // apply the default section
        bookSection.applyView();
    }

}
