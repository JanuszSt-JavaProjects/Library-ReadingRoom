package library.frontend.view.admin.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import library.backend.library.domain.Book;
import library.backend.library.service.BookService;
import library.frontend.view.admin.view.AdmBookView;
import org.springframework.context.ApplicationContext;


public class BookForm extends FormLayout {

    AdmBookView admBookView;
    ApplicationContext context;
    BookService bookService;

    private TextField title = new TextField("Title");
    private TextField author = new TextField("Author");
    private IntegerField releaseDate = new IntegerField("Release year");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button modify = new Button("Modify");

    HorizontalLayout buttons = new HorizontalLayout(modify, save, delete);

    private final Binder<Book> binder = new Binder<>(Book.class);


    public BookForm(AdmBookView admBookView, ApplicationContext context) {
        this.admBookView = admBookView;
        this.context = context;
        bookService = context.getBean(BookService.class);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setVisible(false);
        modify.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        modify.setVisible(false);

        binder.bindInstanceFields(this);


        save.addClickListener(click -> save());
        modify.addClickListener(click -> update());
        delete.addClickListener(click -> delete());

        VerticalLayout layout_Right =
                new VerticalLayout(title, author, releaseDate, buttons);

        add(layout_Right);
    }


    public void setSaveAction() {
        setEnabled(true);
        setBook(new Book());
        modify.setVisible(false);
        save.setVisible(true);
        save.setEnabled(true);

    }

    public void setUpdateAction() {
        setEnabled(true);
        save.setVisible(false);
        modify.setVisible(true);

    }


    public void setBook(Book book) {

        binder.setBean(book);

        if (book == null) {
            setVisible(false);
        } else {
            setVisible(true);
            title.focus();
        }
    }


    private void save() {

        Book book = binder.getBean();
        bookService.save(book);
        admBookView.refresh();
        setBook(book);
        setDisable();

    }

    private void update() {

        Book book = binder.getBean();
        bookService.update(book);
        admBookView.refresh();
        setBook(book);
        setDisable();
    }


    private void delete() {
        Book book = binder.getBean();
        bookService.delete(book.getId());
        admBookView.refresh();
        setBook(book);
    }

    void setDisable(){
        setEnabled(false);

    }

}
