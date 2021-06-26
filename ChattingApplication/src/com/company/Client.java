package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    Socket socket;

    BufferedReader bufferedReader;
    PrintWriter printWriter;

    String severName = "Sever";

    public Client() {
        try {
            System.out.println("Sending request to server...");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("Connection done.");

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startReading() {

        Runnable r1 = () -> {
            System.out.println("Reading from the Server");

            while (true) {
                try {
                    String message = bufferedReader.readLine();
                    if (message.equals("exit")) {
                        System.out.println("Sever has Terminated the chat");
                        break;
                    }
                    System.out.println(severName + " : " + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
//        new Thread(r1).start();
        Thread readingThread = new Thread(r1);
        readingThread.start();
    }

    private void startWriting() {
        System.out.println("Writing to the Sever..");

        Runnable r2 = ()-> {
            while (true) {
                try {
                    BufferedReader inputFromKeyboard = new BufferedReader(new InputStreamReader(System.in));
                    String message = inputFromKeyboard.readLine();
                    printWriter.println(message);
                    printWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread writerThread = new Thread(r2);
        writerThread.start();

    }

    public static void main(String[] args) {
        System.out.println("This is Client..");
        new Client();
    }
}
