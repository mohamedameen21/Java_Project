package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket server; //Server
    Socket socket;   //Client

    BufferedReader bufferedReader;
    PrintWriter printWriter;

    String clientName = "Client";

    public Server() {

        try {
            server = new ServerSocket(7777);  //creating a server in the specific port
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting......");

            socket = server.accept(); //Accept the incoming connection

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  //we can read from Socket
            printWriter = new PrintWriter(socket.getOutputStream());  //We can write to the Socket or Client

            startReading();
            startWriting();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReading() {

        //Thread - read the data

        //Lambda expression for creating a thread
        Runnable r1 = () -> {
            System.out.println("Reading from the Client");

            while (true) {
                try {
                    String message = bufferedReader.readLine();  //reading from buffered Reader
                    if (message.equals("Client has terminated the chat")) {  //terminate if client entered exit
                        System.out.println("Client Terminated the chat");
                        System.exit(0);
                        break;
                    }
                    System.out.println(clientName + " : " + message);    //printing the message
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread readerThread = new Thread(r1);
        readerThread.start();

//        Thread test = new Thread() {
//            @Override
//            public void run() {
//
//            }
//        };

        //Many methods to create a Threads lambda expression anonymous class or separate class .
    }

    private void startWriting() {

        //Thread - writes the data to the client
        Runnable r2 = () -> {
            System.out.println("Writing to the Client..");

            while (true) {
                try {
                    //Reading from the keyboard
                    BufferedReader inputFromConsole = new BufferedReader(new InputStreamReader(System.in));
                    if(inputFromConsole.equals("exit")){
                        System.exit(0);
                    }
                    String content = inputFromConsole.readLine();  //Converting as a String what we have entered
                    printWriter.println(content); //sending as the outputStream to the socket
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
        System.out.println("This is Server..");
        new Server();
    }
}
