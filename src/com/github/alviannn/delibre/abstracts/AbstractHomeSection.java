package com.github.alviannn.delibre.abstracts;

import com.github.alviannn.delibre.models.Book;
import com.github.alviannn.delibre.models.Borrow;
import com.github.alviannn.delibre.models.User;

import javax.swing.*;

public abstract class AbstractHomeSection {

    protected final AbstractHomeView view;
    public final JButton mainBtn;
    protected final int section;

    public JComboBox<String> categoryField, sortTypeField;
    public JTextField searchField;
    public JButton clearBtn, searchBtn;

    public AbstractHomeSection(AbstractHomeView view, JButton mainBtn, int section) {
        this.view = view;
        this.mainBtn = mainBtn;
        this.section = section;
    }

    protected void makeFilters() {
        JPanel filter = view.filterPanel;

        JLabel categoryLabel = new JLabel("Category"),
                sortTypeLabel = new JLabel("Sort Type"),
                searchLabel = new JLabel("Search");

        String[] categoryValues;
        switch (section) {
            case AbstractHomeView.BOOK:
                categoryValues = Book.Field.getFieldNames();
                break;
            case AbstractHomeView.USER:
                categoryValues = User.Field.getFieldNames();
                break;
            case AbstractHomeView.BORROWED:
                categoryValues = Borrow.Field.getFieldNames();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + section);
        }

        categoryField = new JComboBox<>(categoryValues);
        sortTypeField = new JComboBox<>(new String[]{"ASCENDING", "DESCENDING"});
        searchField = new JTextField();

        int formCenter = 40;
        int formGap = 35;

        categoryLabel.setBounds(30 + formCenter, 20, 130, 50);
        categoryField.setBounds(90 + formCenter, 30, 200, 30);

        sortTypeLabel.setBounds(30 + formCenter, 20 + formGap, 130, 50);
        sortTypeField.setBounds(90 + formCenter, 30 + formGap, 200, 30);

        searchLabel.setBounds(30 + formCenter, 20 + (formGap * 2), 130, 50);
        searchField.setBounds(90 + formCenter, 30 + (formGap * 2), 200, 30);

        searchBtn = new JButton("Search");

        int btnWidth = 130;
        int btnCenter = (395 - btnWidth) / 2;
        searchBtn.setBounds(btnCenter, 160, btnWidth, 40);

        filter.add(categoryLabel);
        filter.add(categoryField);

        filter.add(sortTypeLabel);
        filter.add(sortTypeField);

        filter.add(searchLabel);
        filter.add(searchField);

        filter.add(searchBtn);
    }

    protected abstract void makeDetails();

    public abstract void clearDetailsFields();

    public void clearFiltersFields() {
        categoryField.setSelectedIndex(categoryField.getItemCount() == 0 ? -1 : 0);
        sortTypeField.setSelectedIndex(sortTypeField.getItemCount() == 0 ? -1 : 0);
        searchField.setText("");
    }

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
        view.filterPanel.removeAll();

        // re-build the panels
        this.makeDetails();
        this.makeFilters();

        // disable button
        mainBtn.setEnabled(false);
    }

    /**
     * Cleans up the changed data from this section
     * <p>
     * For example: clearing the text fields
     */
    public void dispose() {
        this.clearDetailsFields();
        this.clearFiltersFields();

        // re-enable button
        mainBtn.setEnabled(true);
    }

}
