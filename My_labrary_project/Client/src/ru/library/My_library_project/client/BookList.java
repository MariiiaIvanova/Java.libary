package ru.library.My_library_project.client;

import ru.library.My_library_project.api.data.Book;

import javax.swing.*;

public class BookList extends JList<Book> {

    public BookList() {
        super(new BookModel<>());
        setCellRenderer(new BookRenderer());
    }

    public BookModel getBookModel() {
       return (BookModel) getModel();
    }
}
