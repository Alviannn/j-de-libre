package com.github.alviannn.delibre.abstracts;

import com.github.alviannn.delibre.Main;
import com.github.alviannn.delibre.controllers.ModelHelper;
import com.github.alviannn.delibre.models.Book;
import com.github.alviannn.delibre.models.Borrow;
import com.github.alviannn.delibre.models.User;

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

        switch (view.currentSection) {
            case AbstractHomeView.BOOK: {
                model = new DefaultTableModel(Book.Field.getFieldNames(), 0);
                List<Book> books = helper.getAllBooks();

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
                List<User> users = helper.getAllUsers();

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
                List<Borrow> borrows = helper.getAllBorrowed();

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

}
