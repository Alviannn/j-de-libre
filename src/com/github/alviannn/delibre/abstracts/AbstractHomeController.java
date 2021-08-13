package com.github.alviannn.delibre.abstracts;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.controllers.ModelHelper;
import com.github.alviannn.delibre.models.Book;
import com.github.alviannn.delibre.models.Borrow;
import com.github.alviannn.delibre.models.User;
import com.github.alviannn.delibre.util.SortType;
import com.github.alviannn.delibre.views.AdminHomeView;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public abstract class AbstractHomeController extends AbstractController {

    public AbstractHomeController(Main main) {
        super(main);
    }

    protected void refreshTable(AbstractHomeView view) {
        DefaultTableModel model = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        ModelHelper helper = main.getModelHelper();

        String sortItem = (String) view.getAppliedSection().sortTypeField.getSelectedItem();
        String categoryItem = (String) view.getAppliedSection().categoryField.getSelectedItem();

        SortType currentSort = SortType.NONE;
        if (sortItem != null) {
            currentSort = SortType.valueOf(sortItem);
        }

        switch (view.currentSection) {
            case AbstractHomeView.BOOK: {
                model = new DefaultTableModel(Book.Field.getFieldNames(), 0);
                String column = Book.Field.fromName(categoryItem).getColumn();

                List<Book> books = helper.getAllBooks(currentSort, column);

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

                List<User> users = helper.getAllUsers(currentSort, column);

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

                List<Borrow> borrows = helper.getAllBorrowed(currentSort, column);

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
