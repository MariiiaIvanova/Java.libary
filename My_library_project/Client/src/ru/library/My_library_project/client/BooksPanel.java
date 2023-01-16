package ru.library.My_library_project.client;

import ru.library.My_library_project.api.data.Book;
import com.caucho.hessian.client.HessianProxyFactory;
import ru.library.My_library_project.api.services.BookService;
import ru.library.My_library_project.server.BookServiceImpl;

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

        BookList list = new BookList();
        JTextField bookField = new JTextField(40);
        JTextArea aboutArea = new JTextArea();
        JButton addButton = new JButton("Add");
        JButton delButton = new JButton("Delete");
        JButton searchButton = new JButton("search for tne book");
        JButton seeButton = new JButton("See all");
        JButton cleanButton = new JButton("Cleaner");

        JPanel aboutPanel = new JPanel(new BorderLayout());
        aboutPanel.add(aboutArea, BorderLayout.CENTER);

        String imagePath = "image.jpg";
        BufferedImage myPicture;
        try {
            myPicture = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String s = JOptionPane.showInputDialog(BooksPanel.this, "Please, write name of the book","Input", JOptionPane.INFORMATION_MESSAGE);
                String t = JOptionPane.showInputDialog(BooksPanel.this, "Please, write author of the book","Input", JOptionPane.INFORMATION_MESSAGE);
                String abo = JOptionPane.showInputDialog(BooksPanel.this, "Please, write about the book","Input", JOptionPane.INFORMATION_MESSAGE);
                if ((!s.equals("")) && (!t.equals("")) && (!abo.equals(""))) {
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
                else {
                    JOptionPane.showMessageDialog(BooksPanel.this, "There should be no empty fields");
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
        toolBar.add(seeButton);

        JPanel bookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bookPanel.add(new JLabel("Book:"));
        bookPanel.add(bookField);

        bookPanel.add(searchButton);
        searchButton.setBackground(Color.getHSBColor((float) 0.59,(float) 0.41,(float) 0.88));

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               String searcher = bookField.getText();

                BookServiceImpl bookService = new BookServiceImpl();

                aboutArea.setText(bookService.searchBook(searcher));

            }
       }
       );



        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.add(picLabel);
        jPanel.setVisible(true);


        jPanel.setBorder(BorderFactory.createTitledBorder("***************************************************"));
        aboutPanel.setBorder(BorderFactory.createTitledBorder("About"));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(toolBar, BorderLayout.NORTH);
        leftPanel.add(cleanButton, BorderLayout.SOUTH);
        leftPanel.add(list, BorderLayout.CENTER);



        cleanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int t = JOptionPane.showConfirmDialog(BooksPanel.this, "Are you sure? All books will be deleted");
                if(t == 0) {
                    BookServiceImpl bookService = new BookServiceImpl();
                    aboutArea.setText("");
                    bookService.cleaner();
                    list.getBookModel().cleaner();
                    list.removeAll();
                    repaint();
                    revalidate();
                    leftPanel.add(list, BorderLayout.CENTER);
                }
            }
        });



        seeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             BookServiceImpl bookService = new BookServiceImpl();

                list.getBookModel().setBookList(bookService.seeBook());
                leftPanel.add(list, BorderLayout.CENTER);

            }
        }
        );

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
