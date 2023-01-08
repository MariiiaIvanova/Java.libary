package ru.library.My_library_project.server;

import ru.library.My_library_project.api.data.Book;
import ru.library.My_library_project.api.services.BookService;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class BookServiceImpl implements BookService {
    String url = "jdbc:postgresql://localhost:5432/bookBase";
    String login = "postgres";
    String password = "postpastMary";
    @Override
    public String addBook(Book book) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            String id = UUID.randomUUID().toString();
            book.setId(id);
            Connection conn = DriverManager.getConnection(url, login, password);
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO books(id, book_name, book_author, book_about) VALUES (\'" + book.getId() + "\',\'"+book.getName()+"\',\'"+book.getAuthor()+"\',\'"+book.getAbout()+"\')";
            statement.execute(sql);
            statement.close();
            conn.close();
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String searchBook(String searcher) {

        try {
          //  book.getAbout(name);
            Connection conn = DriverManager.getConnection(url, login, password);
            Statement statement = conn.createStatement();
            String sql = "SELECT book_about FROM books WHERE book_name='" + searcher + "'";
            statement.execute(sql);
            //ResultSet resultSet = statement.executeQuery(sql);
            statement.close();
            conn.close();
            return sql;
            //return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void delBook(String id) {
        try {
            Connection conn = DriverManager.getConnection(url, login, password);
            Statement statement = conn.createStatement();
            String sql = "DELETE FROM books WHERE id='" + id + "'";
            statement.execute(sql);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book getBook(String id) {
        try {
            Connection conn = DriverManager.getConnection(url, login, password);
            Statement statement = conn.createStatement();
            String sql = "SELECT book_name, book_author, book_about FROM books WHERE id='" + id + "'"; //???
            ResultSet resultSet = statement.executeQuery(sql);
            Book book = null;
            if (resultSet.next()) {
                book = new Book();
                book.setId(id);
                book.setName(resultSet.getString("book_name"));
                book.setAuthor(resultSet.getString("book_author"));
                book.setAbout(resultSet.getString("book_about"));
            }
            statement.close();
            conn.close();
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> getBookList() {
        return null;
    }
}
