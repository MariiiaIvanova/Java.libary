package ru.library.My_library_project.client;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() throws HeadlessException{
        super("Book base");
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Books", new BooksPanel());
        tabbedPane.addTab("Clients", new ClientPanel());
        setLayout(new BorderLayout());
        add(tabbedPane);

    }

}
