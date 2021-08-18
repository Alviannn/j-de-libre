package com.github.alviannn.delibre.abstracts;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.controllers.ModelHelper;
import com.github.alviannn.delibre.models.Book;
import com.github.alviannn.delibre.models.Borrow;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.util.SortType;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public abstract class AbstractHomeController extends AbstractController {

    public AbstractHomeController(Main main) {
        super(main);
    }

    /**
     * Refreshes the data table on either user or admin home view
     * This will also handle the sort and search functionality
     *
     * @param view the targeted view
     */
    protected void refreshTable(AbstractHomeView view) {
        DefaultTableModel model = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ModelHelper helper = main.getModelHelper();

        String sortItem = (String) view.sortTypeField.getSelectedItem();
        String categoryItem = (String) view.categoryField.getSelectedItem();
        SortType currentSort = SortType.valueOf(sortItem);

        switch (view.currentSection) {
            case AbstractHomeView.BOOK: {
                model = new DefaultTableModel(Book.Field.getFieldNames(), 0);
                String column = Book.Field.fromName(categoryItem).getColumn();

                List<Book> books = helper.getAllBooks(currentSort, column, view.searchField.getText());

                for (Book book : books) {
                    model.addRow(new Object[]{
                            book.getId() + "",
                            book.getTitle(), book.getAuthor(),
                            book.getYear() + "", book.getPageCount() + ""});
                }
                break;
            }
            case AbstractHomeView.USER: {
                model = new DefaultTableModel(User.Field.getFieldNames(), 0);
                String column = User.Field.fromName(categoryItem).getColumn();

                List<User> users = helper.getAllUsers(currentSort, column, view.searchField.getText());

                for (User user : users) {
                    model.addRow(new Object[]{
                            user.getId() + "",
                            user.getName(),
                            dateFormat.format(user.getRegisteredDate())});
                }
                break;
            }
            case AbstractHomeView.BORROWED: {
                model = new DefaultTableModel(Borrow.Field.getFieldNames(), 0);
                String column = Borrow.Field.fromName(categoryItem).getColumn();

                List<Borrow> borrows;
                User user = main.getCurrentUser();

                if (user.isAdmin()) {
                    borrows = helper.getAllBorrowed(currentSort, column, view.searchField.getText());
                } else {
                    borrows = helper.getAllBorrowedUser(currentSort, column, view.searchField.getText(), user);
                }

                for (Borrow borrow : borrows) {
                    model.addRow(new Object[]{
                            borrow.getId() + "",
                            borrow.getUserId() + "",
                            borrow.getBookId() + "",
                            dateFormat.format(borrow.getBorrowDate()),
                            dateFormat.format(borrow.getDueDate()),
                            borrow.getUsername(),
                            borrow.getBookTitle()
                    });
                }
                break;
            }
        }

        assert model != null;

        view.table.setModel(model);
        // possibly filled, therefore we're clearing it to reset the search
        view.searchField.setText("");
    }

    protected void useCategorySelectAction(AbstractHomeView view) {
        view.categoryField.addActionListener(e -> {
            String item = (String) view.categoryField.getSelectedItem();
            boolean canSearch;

            switch (view.currentSection) {
                case AbstractHomeView.BOOK:
                    canSearch = Book.Field.fromName(item).isSearchable();
                    break;
                case AbstractHomeView.USER:
                    canSearch = User.Field.fromName(item).isSearchable();
                    break;
                case AbstractHomeView.BORROWED:
                    canSearch = Borrow.Field.fromName(item).isSearchable();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + view.currentSection);
            }

            view.searchField.setText("");
            view.searchField.setEnabled(canSearch);
        });
    }

    protected void useClearAction(AbstractHomeView view) {
        for (int i = AbstractHomeView.BOOK; i <= AbstractHomeView.BORROWED; i++) {
            try {
                AbstractHomeSection section = view.getSection(i);
                section.clearBtn.addActionListener(e -> view.clearSelection());
            } catch (Exception ignored) {
                // error could occur since user view doesn't allow getting User section (it doesn't exist)
            }
        }
    }

    protected void useLogoutAction(AbstractHomeView view) {
        view.logoutBtn.addActionListener(e -> {
            view.dispose();
            main.setCurrentUser(null);
            main.getAuthController().showView();
        });
    }

    /**
     * Action that changes currently applied section to another section
     * <p>
     * Will automatically dispose the previous sections, apply the new one, and updates the UI (components and table)
     * To shorten codes
     */
    protected void changeSectionAction(AbstractHomeView view, int type) {
        view.disposeSections();

        AbstractHomeSection section = view.getSection(type);
        section.applyView();

        this.refreshTable(view);
        view.updateUI();
    }

}
