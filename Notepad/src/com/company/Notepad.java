package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

public class Notepad extends JFrame implements ActionListener {

    JTextArea textArea;
    JScrollPane scrollPane;
    String selectedText;

    Notepad() {

        //Basics of Frame
        basicFrameStructure();

        //MenuBar
        creatingMenuBarAddingToFrame();

        //TextArea
        addingTextAreaWithinScrollPane();

        basicEndStatements();
    }

    private void addingTextAreaWithinScrollPane() {
        textArea = new JTextArea();
        //Changing the font and the size of the text entered in the textarea
        textArea.setFont(new Font("Consolas", Font.PLAIN, 20));
        textArea.setLineWrap(true);  //break the line if it touch the border
        textArea.setWrapStyleWord(true); //it breaks the line by word (didn't break the word itself it moves
        //the total word to the next line)

        scrollPane = new JScrollPane(textArea); //to give scroll bar automatically
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); //to remove the default the border

        add(scrollPane, BorderLayout.CENTER);
    }

    private void creatingMenuBarAddingToFrame() {
        JMenuBar menuBar = new JMenuBar();

        //Menus
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu help = new JMenu("help");

        JMenuItem newDoc = new JMenuItem("New");
        newDoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newDoc.addActionListener(this);

        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);

        JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        print.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exit.addActionListener(this);

        file.add(newDoc);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);

        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);

        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.addActionListener(this);

        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);

        JMenuItem selectAll = new JMenuItem("Select All");
        selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectAll.addActionListener(this);

        edit.add(copy);
        edit.add(cut);
        edit.add(paste);
        edit.add(selectAll);

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(this);

        help.add(about);

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);

//        add(menuBar); //we can't use this because it align the menubar in border layout vertically
        setJMenuBar(menuBar);
    }

    private void basicEndStatements() {
        //Always set visible statement should be at the last now i have experienced it haha
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void basicFrameStructure() {
        setTitle("Notepad");
        setIconImage(new ImageIcon("src/img/text.jpeg").getImage());
//        setSize(700, 600);
        setBounds(300, 70, 700, 600);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("New")) {
//            textArea.setText("");
            new Notepad();
        } else if (e.getActionCommand().equals("Open")) {
            JFileChooser chooser = new JFileChooser();
//            chooser.setAcceptAllFileFilterUsed(false); //ignore all files
            FileNameExtensionFilter allowedExtensions = new FileNameExtensionFilter("Only Text Files", "txt");
            chooser.setFileFilter(allowedExtensions);

            int action = chooser.showOpenDialog(this);
            if (action != chooser.APPROVE_OPTION) {
                return;
            }

            File fileToOpen = chooser.getSelectedFile();
            try{
                BufferedReader reader = new BufferedReader(new FileReader(fileToOpen));
                textArea.read(reader,null);
            }catch (Exception exception){
                exception.printStackTrace();
            }

        } else if (e.getActionCommand().equals("Save")) {
            JFileChooser saveAs = new JFileChooser();
            saveAs.setApproveButtonText("Save"); //setting the name for approve button default one is open
            int action = saveAs.showOpenDialog(this);

            if (action != saveAs.APPROVE_OPTION) {
                return;
            }

            File filename = new File(saveAs.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;

            try {
                outFile = new BufferedWriter(new FileWriter(saveAs.getSelectedFile() + ".txt"));
                textArea.write(outFile);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }else if(e.getActionCommand().equals("Print")){
            try{
                textArea.print();
            }catch (Exception exception){
                exception.printStackTrace();;
            }
        }else if(e.getActionCommand().equals("Exit")){
            System.exit(0);
        }else if(e.getActionCommand().equals("Copy")){
            selectedText = textArea.getSelectedText();
        }else if(e.getActionCommand().equals("Paste")){
            textArea.insert(selectedText,textArea.getCaretPosition());
        }else if(e.getActionCommand().equals("Cut")){
            selectedText = textArea.getSelectedText();
            textArea.replaceRange("",textArea.getSelectionStart(),textArea.getSelectionEnd());
        }else if(e.getActionCommand().equals("Select All")){
            textArea.selectAll();
        }else if(e.getActionCommand().equals("About")){
            new About().setVisible(true);
        }
    }
}
