package ru.library.My_library_project.client;

import ru.library.My_library_project.server.BookServiceImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ClientPanel extends JPanel {
    public ClientPanel() {

        BookList list = new BookList();
        JTextField bookField = new JTextField(40);

        String imagePath = "lupa.jpg";
        BufferedImage myPicture =null;
        try {
            myPicture = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String imagePath1 = "eye.jpg";
        BufferedImage myPicture1 = null;
        try {
            myPicture1 = ImageIO.read(new File(imagePath1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new Dimension(300, 450));
        scrollPane.setLayout(new ScrollPaneLayout());
        list.setLayoutOrientation(JList.VERTICAL);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        JButton findButton = new JButton("Find book");
        findButton.setPreferredSize(new Dimension(300,100));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        findButton.add(picLabel);
        findButton.setBackground(Color.getHSBColor((float) 0.59,(float) 0.43,(float) 0.3));



        setLayout(new BorderLayout());
        JPanel p = new JPanel(new BorderLayout(10,10));
        add(p, BorderLayout.CENTER);


        JButton seeButton = new JButton("See all");
        seeButton.setPreferredSize(new Dimension(300,100));

        JLabel picLabel1 = new JLabel(new ImageIcon(myPicture1));
        seeButton.add(picLabel1);
        seeButton.setBackground(Color.getHSBColor((float) 0.59,(float) 0.43,(float) 0.3));

        //add(findButton, BorderLayout.WEST);
        //add(seeButton, BorderLayout.EAST);
        //p.add(bookListArea, BorderLayout.SOUTH);


        JTextArea aboutArea = new JTextArea(30, 80);


        JPanel aboutPanel = new JPanel(new BorderLayout());

        aboutPanel.setBorder(BorderFactory.createTitledBorder("About the book"));
        aboutPanel.add(aboutArea, BorderLayout.CENTER);


        JPanel bookPanel = new JPanel(new FlowLayout());
        p.add(bookPanel, BorderLayout.NORTH);

        bookPanel.add(findButton);
        bookPanel.add(new JLabel("Book:"));
        bookPanel.add(bookField, BorderLayout.NORTH);
        bookPanel.add(seeButton);

        p.add(scrollPane, BorderLayout.SOUTH);
        p.add(aboutPanel, BorderLayout.CENTER);

        p.setBackground(Color.getHSBColor((float) 0.59,(float) 0.43,(float) 0.3));

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searcher = bookField.getText();
                BookServiceImpl bookService = new BookServiceImpl();
                aboutArea.setText(bookService.searchBook(searcher));
            }
        }
        );


        seeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookServiceImpl bookService = new BookServiceImpl();

                list.getBookModel().setBookList(bookService.seeBook());

                scrollPane.revalidate();
                scrollPane.repaint();
                p.add(scrollPane, BorderLayout.SOUTH);

            }
        }
        );

    }
}
