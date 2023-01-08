package ru.library.My_library_project.client;

import ru.library.My_library_project.api.data.Book;
import com.caucho.hessian.client.HessianProxyFactory;
import ru.library.My_library_project.api.services.BookService;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class BooksPanel extends JPanel {
    public BooksPanel() {

        String abo = null;
        BookList list = new BookList();
        JTextField bookField = new JTextField(40);
        JTextArea aboutArea = new JTextArea();
        JButton addButton = new JButton("Add");
        JButton delButton = new JButton("Delete");
        JButton searchButton = new JButton("search for tne book");

        JPanel aboutPanel = new JPanel(new BorderLayout());
        aboutPanel.add(aboutArea, BorderLayout.CENTER);

        String imagePath = "D:/IdeaProjects/My_labrary_project/image.jpg";
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(BooksPanel.this, "There should be no empty fields");
                String s = JOptionPane.showInputDialog(BooksPanel.this, "Please, write name of the book","Input", JOptionPane.INFORMATION_MESSAGE);
                String t = JOptionPane.showInputDialog(BooksPanel.this, "Please, write author of the book","Input", JOptionPane.INFORMATION_MESSAGE);
                String abo = JOptionPane.showInputDialog(BooksPanel.this, "Please, write about the book","Input", JOptionPane.INFORMATION_MESSAGE);
                if((s != null) && (t != null) && (abo != null)) {
                    Book book = new Book();
                    book.setName(s);
                    book.setAuthor(t);
                    book.setAbout(abo);

                    aboutArea.setText(abo);

                    try {
                        String url = "http://127.0.0.1:8080/book";
                        HessianProxyFactory factory = new HessianProxyFactory();
                        BookService bookService = (BookService) factory.create(BookService.class, url);
                        String id =  bookService.addBook(book);
                        Book book1 = bookService.getBook(id);
 //todo перечитать с сервера
                        list.getBookModel().addBook(book1);
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }

                }

            }
        });

       delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book book = list.getSelectedValue();
                try {
                    String url = "http://127.0.0.1:8080/book";
                    HessianProxyFactory factory = new HessianProxyFactory();
                    BookService bookService = (BookService) factory.create(BookService.class, url);
                    bookService.delBook(book.getId());
                    aboutArea.setText("");
                    list.getBookModel().delBook(book);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(addButton);
        toolBar.add(delButton);

        JPanel bookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bookPanel.add(new JLabel("Book:"));
        bookPanel.add(bookField);

        bookPanel.add(searchButton);

       searchButton.addActionListener(new ActionListener() {///////////////////
           @Override
           public void actionPerformed(ActionEvent e) {

               String searcher = bookField.getText();

               try {
                   String url = "http://127.0.0.1:8080/book";
                   HessianProxyFactory factory = new HessianProxyFactory();
                   BookService bookService = (BookService) factory.create(BookService.class, url);
                   /*bookService.searchBook(searcher);
                   /nt abo = list.getBookModel().searchBook(searcher);
                   aboutArea.setText(list.getSelectedValue().getAbout());*/
                   aboutArea.setText(bookService.searchBook(searcher));


               } catch (MalformedURLException ex) {
                   throw new RuntimeException(ex);
               }
           }

       }
       );


        //JPanel aboutPanel = new JPanel(new BorderLayout());
       // aboutPanel.add(aboutArea, BorderLayout.CENTER);
       // add(abo, aboutArea);


        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        JPanel jPanel = new JPanel(new BorderLayout());
        //jPanel.setSize(new Dimension(myPicture.getWidth(), myPicture.getHeight()));
        jPanel.add(picLabel);
        jPanel.setVisible(true);

        aboutPanel.setBorder(BorderFactory.createTitledBorder("About"));
        jPanel.setBorder(BorderFactory.createTitledBorder("***************************************************"));


        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(toolBar, BorderLayout.NORTH);
        leftPanel.add(list, BorderLayout.CENTER);
       // leftPanel.setBorder(BorderFactory.createTitledBorder(""));

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(bookPanel, BorderLayout.NORTH);
        rightPanel.add(aboutPanel, BorderLayout.CENTER);
        rightPanel.add(jPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(300);

        setLayout(new BorderLayout());
        add(splitPane, BorderLayout.CENTER);
    }

}
