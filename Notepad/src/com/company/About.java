package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class About extends JFrame  {

    About(){
        setBounds(600,200,450,400);
        setLayout(null);

        ImageIcon imageIcon = new ImageIcon("src/img/text.jpeg"); //getting the image
        //changing the resolution of the image
        Image image = imageIcon.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);

        ImageIcon imageIcon1 = new ImageIcon(image); //converting again to image icon to add in label
        JLabel label = new JLabel(imageIcon1); //adding the icon in the label
        label.setBounds(175,40,100,100); //label bound

        add(label);//adding label

        JLabel aboutInText = new JLabel("<html>Simple Notepad Application<br>" +
                "Enables you to write open save print and edit with shortcut keys<br>" +
                "Version: 1.1v<br>" +
                "Developer: Mohamed Ameen<br>" +
                "Contact: mohamedameen@gmail.com<br>" +
                "</html>");

//        aboutInText.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        aboutInText.setBounds(50,60,400,300);
        add(aboutInText);

        JButton button = new JButton("OK");
        button.setBounds(175,300,80,40);

        add(button);
        button.addActionListener(e -> this.setVisible(false));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
