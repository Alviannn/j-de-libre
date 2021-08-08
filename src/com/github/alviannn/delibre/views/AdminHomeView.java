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
    public AbstractHomeSection getAppliedSection() {
        AbstractHomeSection section;

        switch (currentSection) {
            case BOOK:
                section = bookSection;
                break;
            case USER:
                section = userSection;
                break;
            case BORROWED:
                section = borrowedSection;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentSection);
        }

        return section;
    }

    @Override
    public AbstractHomeSection getSection(int type) {
        AbstractHomeSection section;

        switch (type) {
            case BOOK:
                section = bookSection;
                break;
            case USER:
                section = userSection;
                break;
            case BORROWED:
                section = borrowedSection;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return section;
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
