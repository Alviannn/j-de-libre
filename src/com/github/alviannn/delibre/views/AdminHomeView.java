package com.github.alviannn.delibre.views;

import com.github.alviannn.delibre.abstracts.AbstractHomeSection;
import com.github.alviannn.delibre.abstracts.AbstractHomeView;
import com.github.alviannn.delibre.views.admin.AdminBookSection;
import com.github.alviannn.delibre.views.admin.AdminBorrowedSection;
import com.github.alviannn.delibre.views.admin.AdminUserSection;

public class AdminHomeView extends AbstractHomeView {

    public AdminBookSection bookSection;
    public AdminUserSection userSection;
    public AdminBorrowedSection borrowedSection;

    @Override
    public AbstractHomeSection getSection(int type) {
        switch (type) {
            case BOOK:
                return bookSection;
            case USER:
                return userSection;
            case BORROWED:
                return borrowedSection;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    @Override
    protected void buildFrame() {
        super.buildFrame();

        this.setTitle("De Libre - Admin");

        bookSection = new AdminBookSection(this);
        userSection = new AdminUserSection(this);
        borrowedSection = new AdminBorrowedSection(this);

        // apply the default section
        bookSection.applyView();
    }

}
