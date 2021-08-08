package com.github.alviannn.delibre.abstracts;

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
    public JPanel detailsPanel, filterPanel;

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
     * Builds the menu section
     */
    private JPanel buildMenu() {
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

        return this.marginWrap(menu, 5);
    }

    @Override
    protected void buildFrame() {
        booksBtn = new JButton("Books");
        usersBtn = new JButton("Users");
        borrowedBtn = new JButton("Borrowed Books");
        logoutBtn = new JButton("Logout");

        table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        detailsPanel = new JPanel(null);
        filterPanel = new JPanel(null);

        detailsPanel.setBorder(BorderFactory.createTitledBorder("Details"));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));

        JPanel bottomCenter = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = this.getDefaultGridBagConstraints();
        gbc.ipadx = 300;

        bottomCenter.add(detailsPanel, gbc);
        gbc.gridx = 1;
        gbc.ipadx = 0;
        bottomCenter.add(filterPanel, gbc);

        JPanel center = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        center.add(scrollPane, gbc);
        gbc.ipady = 200;
        gbc.gridy = 1;
        center.add(bottomCenter, gbc);

        this.add(this.buildMenu(), BorderLayout.WEST);
        this.add(this.marginWrap(center, 5, 0, 5, 5), BorderLayout.CENTER);
    }

}
