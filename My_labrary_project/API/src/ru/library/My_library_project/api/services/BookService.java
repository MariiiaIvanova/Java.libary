package ru.library.My_library_project.api.services;

import ru.library.My_library_project.api.data.Book;

import java.util.List;

public interface BookService {

    String addBook(Book book);

    void delBook(String id);

    Book getBook(String id);


    List<Book> getBookList();

    String searchBook(String searcher);
}
