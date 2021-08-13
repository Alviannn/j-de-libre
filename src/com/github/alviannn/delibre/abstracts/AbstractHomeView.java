package com.github.alviannn.delibre.abstracts;

import com.github.alviannn.delibre.models.Book;
import com.github.alviannn.delibre.models.Borrow;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.util.SortType;
import com.github.alviannn.delibre.util.Utils;
import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractHomeView extends AbstractView {

    /** The number representing the books section */
    public static final int BOOK = 0;

    /** The number representing the users section */
    public static final int USER = 1;

    /** The number representing the borrowed books section */
    public static final int BORROWED = 2;

    public JButton booksBtn, usersBtn, borrowedBtn, logoutBtn;
    public JTable table;
    public JPanel detailsPanel;

    public JComboBox<String> categoryField, sortTypeField;
    public JTextField searchField;
    public JButton searchBtn;

    /**
     * The current applied section
     * <p>
     * Possible section values: {@link #BOOK}, {@link #USER}, {@link #BORROWED}
     */
    public int currentSection;

    public AbstractHomeView() {
        // default viewport is 720p
        super(1280, 720);
    }

    /**
     * Gets the currently applied section object from the home view
     */
    @NotNull
    public abstract AbstractHomeSection getAppliedSection();

    /**
     * Gets the specified section object
     *
     * @param type the specified section type
     */
    @NotNull
    public abstract AbstractHomeSection getSection(int type);

    /**
     * In-charge of disposing or removing any types of sections
     * that are currently applied to the view
     */
    public void disposeSections() {
        this.getAppliedSection().dispose();
    }

    /**
     * Builds the filter section
     * Used for filtering data or searching data within the application
     */
    private JPanel buildFilterSection() {
        JPanel filter = new JPanel(null);
        filter.setBorder(BorderFactory.createTitledBorder("Filter"));

        JLabel categoryLabel = new JLabel("Category"),
                sortTypeLabel = new JLabel("Sort Type"),
                searchLabel = new JLabel("Search");

        int formCenter = 40;
        int formGap = 35;

        categoryLabel.setBounds(30 + formCenter, 20, 130, 50);
        categoryField.setBounds(90 + formCenter, 30, 200, 30);

        sortTypeLabel.setBounds(30 + formCenter, 20 + formGap, 130, 50);
        sortTypeField.setBounds(90 + formCenter, 30 + formGap, 200, 30);

        searchLabel.setBounds(30 + formCenter, 20 + (formGap * 2), 130, 50);
        searchField.setBounds(90 + formCenter, 30 + (formGap * 2), 200, 30);

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

        return filter;
    }

    public void clearFilterSection() {
        categoryField.setSelectedIndex(categoryField.getItemCount() == 0 ? -1 : 0);
        sortTypeField.setSelectedIndex(sortTypeField.getItemCount() == 0 ? -1 : 0);
        searchField.setText("");
    }

    /**
     * Changes the stuff related to each section (in this case the {@link #categoryField} and {@link #searchField})
     */
    public void changeFilterSection() {
        JComboBox<String> category = categoryField;

        this.clearFilterSection();
        categoryField.removeAllItems();

        String[] names;
        switch (currentSection) {
            case AbstractHomeView.BOOK:
                names = Book.Field.getFieldNames();
                break;
            case AbstractHomeView.USER:
                names = User.Field.getFieldNames();
                break;
            case AbstractHomeView.BORROWED:
                names = Borrow.Field.getFieldNames();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentSection);
        }

        for (String name : names) {
            category.addItem(name);
        }

        // could be disabled from borrowed book section
        // therefore, I'm re-enabling it here just in-case it's disabled
        searchField.setEnabled(true);
    }

    /**
     * Builds the menu section
     * This is where user can select which section they want to be in
     */
    private JPanel buildMenuSection() {
        JPanel menu = new JPanel(new GridLayout(2, 0));
        menu.setBorder(BorderFactory.createTitledBorder("Menu"));

        JPanel first = new JPanel(new GridLayout(3, 0, 20, 20));
        JPanel second = new JPanel(new GridLayout(1, 0, 20, 20));

        first.setBorder(BorderFactory.createEmptyBorder(10, 10, 100, 10));
        second.setBorder(BorderFactory.createEmptyBorder(260, 10, 10, 10));

        first.add(booksBtn);
        first.add(usersBtn);
        first.add(borrowedBtn);

        second.add(logoutBtn);

        menu.add(first);
        menu.add(second);

        return Utils.marginWrap(menu, 5);
    }

    @Override
    protected void buildFrame() {
        booksBtn = new JButton("Books");
        usersBtn = new JButton("Users");
        borrowedBtn = new JButton("Borrowed Books");
        logoutBtn = new JButton("Logout");

        categoryField = new JComboBox<>(new String[0]);
        sortTypeField = new JComboBox<>(SortType.getNames());
        searchField = new JTextField();
        searchBtn = new JButton("Search");

        table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        detailsPanel = new JPanel(null);
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Details"));

        JPanel bottomCenter = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = Utils.getDefaultGridBagConstraints();
        gbc.ipadx = 300;

        bottomCenter.add(detailsPanel, gbc);
        gbc.gridx = 1;
        gbc.ipadx = 0;
        bottomCenter.add(this.buildFilterSection(), gbc);

        JPanel center = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        center.add(scrollPane, gbc);
        gbc.ipady = 200;
        gbc.gridy = 1;
        center.add(bottomCenter, gbc);

        this.add(this.buildMenuSection(), BorderLayout.WEST);
        this.add(Utils.marginWrap(center, 5, 0, 5, 5), BorderLayout.CENTER);
    }

}
