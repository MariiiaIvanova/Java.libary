package ru.library.My_library_project.client;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class BookModel<Book> extends AbstractListModel<Book>{
    private List<Book> list =new ArrayList<>();


    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Book getElementAt(int index) {
        return list.get(index);
    }

    public void setBookList(List<Book> bookList){
        list = bookList;
    }

    public void addBook(Book book){
        list.add(book);
        fireIntervalAdded(book, list.size()-1, list.size()-1);
    }

    public void cleaner (){
        list.clear();
    }

    public void delBook(Book book){
        list.remove(book);
        fireIntervalRemoved(book, list.size(), list.size());
    }

}
