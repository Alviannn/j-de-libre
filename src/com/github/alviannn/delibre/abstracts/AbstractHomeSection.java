package com.github.alviannn.delibre.abstracts;

import javax.swing.*;

public abstract class AbstractHomeSection {

    protected final AbstractHomeView view;
    public final JButton mainBtn;
    protected final int section;
    public JButton clearBtn;

    public AbstractHomeSection(AbstractHomeView view, JButton mainBtn, int section) {
        this.view = view;
        this.mainBtn = mainBtn;
        this.section = section;

        this.clearBtn = new JButton("Clear");
    }

    protected abstract void makeDetails();

    public abstract void clearDetailsFields();

    /**
     * Updates the view of the current home view
     * This will completely change the view by adding the preferred section
     * <p>
     * Estimated target to be changed: table, details, and filter section
     */
    public void applyView() {
        view.currentSection = section;

        // clear the panels
        view.detailsPanel.removeAll();

        // re-build the panels
        view.changeFilterSection();
        this.makeDetails();

        // disable section button (ex: Book, User, Borrowed Books button)
        mainBtn.setEnabled(false);
        // disable search field (from filter section)
        // because the default category isn't searchable (which is an ID)
        view.searchField.setEnabled(false);
    }

    /**
     * Cleans up the changed data from this section
     * <p>
     * For example: clearing the text fields
     */
    public void dispose() {
        this.clearDetailsFields();

        // re-enable button
        mainBtn.setEnabled(true);
    }

}
