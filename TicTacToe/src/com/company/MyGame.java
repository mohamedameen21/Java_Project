package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener {

    JLabel heading, clockLabel;
    Font font = new Font("", Font.BOLD, 30);
    JPanel mainPanel;

    JButton[] jButtons = new JButton[9];

    //Game variables
    int[] gameChances = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int activePlayer = 0;

    int[][] wps = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };

    int winner = 2;

    boolean gameOver = false;

    MyGame() {

        //Creating The Title for the Frame and width and height
        System.out.println("Creating Instance of Game");
        setTitle("My Tic Tac Toe Game...");
        setSize(600, 600);
        ImageIcon icon = new ImageIcon("src/img/tictactoe.png");
        setIconImage(icon.getImage());

        //Program close when the frame closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //The gui part will handled in this function
        createGUI();
        setVisible(true);
    }

    private void createGUI() {

        //Setting background
        this.getContentPane().setBackground(Color.decode("#2196f3"));

        //Making our Frame BorderLayout
        this.setLayout(new BorderLayout());


        createAndAddHeading();

        createAndUpdateClock();

        createAndAddPannel();

    }

    private void createAndAddPannel() {
        //Panel Section
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 3));

        for (int i = 1; i <= 9; i++) {
            JButton btn = new JButton();
//            btn.setIcon(new ImageIcon("src/img/1.png"));
            btn.setBackground(Color.decode("#90caf9"));
            btn.setFont(font);
            mainPanel.add(btn);
            jButtons[i - 1] = btn;

            btn.addActionListener(this);
            btn.setName(i - 1 + ""); //setting unique name to the buttons to identify separately
        }

        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void createAndAddHeading() {
        //Creating a heading in the north part of the frame
        heading = new JLabel("Tic Tac Toe");
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER); //make the title or heading centre
        heading.setForeground(Color.WHITE);
        this.add(heading, BorderLayout.NORTH);
    }

    private void createAndUpdateClock() {
        //Creating Clock in the bottom
        clockLabel = new JLabel("Clock");
        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setForeground(Color.WHITE);
        this.add(clockLabel, BorderLayout.SOUTH);


        //Creating an anonymous class instead of creating a class that extends thread
        // and then creating the object of the class
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        //getting the current date and time in String
                        String dateTime = new Date().toString();

                        //Setting the current time in the clockLabel
                        clockLabel.setText(dateTime);

                        //Making the thread to sleep for one second to avoid cpu consumption
                        //because the thread will run for one sec by printing the same value
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //getting the button which is pressed recently
        JButton currentButton = (JButton) e.getSource();
        String buttonName = currentButton.getName();

        //getting the index position of the buttons
        int name = Integer.parseInt(buttonName.trim());


        if (gameOver) {
            JOptionPane.showMessageDialog(this, "Game Over..");
            return;
        }

        //Set the icon to the grid on which the button is clicked on altering order
        //1st o and 1 and vice versa
        if (gameChances[name] == 2) {
            if (activePlayer == 1) {
                currentButton.setIcon(new ImageIcon("src/img/1.png"));
                gameChances[name] = activePlayer;
                activePlayer = 0;
            } else {
                currentButton.setIcon(new ImageIcon("src/img/o.png"));
                gameChances[name] = activePlayer;
                activePlayer = 1;
            }


            //Find the Winner
            checkWhoWon();


        } else {
            JOptionPane.showMessageDialog(this, "Position already occupied");
        }


    }

    private void checkWhoWon() {
        for (int[] temp : wps) {

            //check the entered 1 and 0 s are matching the wining postion combinations from the wps array
            if ((gameChances[temp[0]] == gameChances[temp[1]]) &&
                    gameChances[temp[1]] == gameChances[temp[2]] &&
                    gameChances[temp[2]] != 2) {

                winner = gameChances[temp[0]];
                gameOver = true;
                JOptionPane.showMessageDialog(this, "Player " + winner + " has won the game..");

                playAgain();
                break;
            }
        }

        //draw logic
        int c = 0;
        for (int x : gameChances) {
            if (x == 2) {
                c++;
                break;
            }
        }
        if (c == 0 && gameOver == false) {
            JOptionPane.showMessageDialog(this, "Its draw");
            playAgain();
            gameOver = true;
        }
    }

    private void playAgain() {
        int i = JOptionPane.showConfirmDialog(this, "Do you want to play again??");
        if (i == 0) { //for yes
            this.setVisible(false);
            new MyGame();
        } else if (i == 1) { //for no
            System.exit(0);
        } else { // 2 for cancel

        }
        System.out.println(i);
    }
}
