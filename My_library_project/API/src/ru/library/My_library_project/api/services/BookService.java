package ru.library.My_library_project.api.services;

import ru.library.My_library_project.api.data.Book;

import java.util.List;

public interface BookService {

    String addBook(Book book);

    void delBook(String id);

    Book getBook(String id);

    String searchBook(String searcher);
    List<Object> seeBook();

    List<Object> getBookList();


    void cleaner();

}
