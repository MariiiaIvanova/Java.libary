package ru.library.My_library_project.client;

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

        String imagePath = "D:/IdeaProjects/My_labrary_project/lupa.jpg";
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String imagePath1 = "D:/IdeaProjects/My_labrary_project/eye.jpg";
        BufferedImage myPicture1 = null;
        try {
            myPicture1 = ImageIO.read(new File(imagePath1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JButton findButton = new JButton("Find book");
        findButton.setPreferredSize(new Dimension(300,100));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        findButton.add(picLabel);

        JButton seeButton = new JButton("See all");
        seeButton.setPreferredSize(new Dimension(300,100));
        JLabel picLabel1 = new JLabel(new ImageIcon(myPicture1));
        seeButton.add(picLabel1);

        add(findButton, BorderLayout.WEST);
        add(seeButton, BorderLayout.EAST);

    }
}
