package ru.library.My_library_project.server;

import ru.library.My_library_project.api.data.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTest {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/bookBase";
        String login = "postgres";
        String password = "postpastMary";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Connection conn = DriverManager.getConnection(url, login, password);
            Statement statement = conn.createStatement();
            //String sql = "CREATE TABLE books (id varchar(60) NOT NULL,  book_name varchar(60) NOT NULL, book_author varchar(20) NOT NULL, book_about varchar(1000) NOT NULL);";
            //String sql = "DELETE INTO books(id, book_name, book_author) VALUES (\'" + UUID.randomUUID()+ "\',\'Murder on the orient express\',\'Agata Christie\')";
            //statement.execute(sql);
            String sql = "SELECT id,  book_name, book_author, book_about FROM books;";
            ResultSet resultSet = statement.executeQuery(sql);
            List<Book> books = new ArrayList<>();
           while (resultSet.next()){
                String id = resultSet.getString("id");
                String name = resultSet.getString("book_name") ;
                String author = resultSet.getString("book_author") ;
                String about = resultSet.getString("book_about") ;
                Book book= new Book();
                book.setId(id);
                book.setName(name);
                book.setAuthor(author);
                book.setAbout(about);
                books.add(book);
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
// String sql = "CREATE TABLE books (id varchar(60) NOT NULL, book_name varchar (30) NOT NULL, PRIMARY KEY (id)):";