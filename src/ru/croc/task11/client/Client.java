package ru.croc.task11.client;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String clientUsername;

    public Client(Socket socket, String clientUsername) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientUsername = clientUsername;
        } catch (IOException e) {
            close(socket, reader, writer);
        }
    }

    private void sendMessage() {
        try {
            writer.write(clientUsername);
            writer.newLine();
            writer.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String message = scanner.nextLine();

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss");
                String dateAndTime = now.format(formatter);

                writer.write("[" + clientUsername + " at " + dateAndTime + "]: " + message);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            close(socket, reader, writer);
        }
    }

    private void listenForMessages() {
        Thread listenThread = new Thread(() -> {
            while (socket.isConnected()) {
                try {
                    String message = reader.readLine();
                    System.out.println(message);
                } catch (IOException e) {
                    close(socket, reader, writer);
                }
            }
        });

        listenThread.start();
    }

    private void close(Socket socket, BufferedReader reader, BufferedWriter writer) {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        Socket socket = new Socket("127.0.0.1", 2003);
        Client client = new Client(socket, username);

        client.listenForMessages();
        client.sendMessage();
    }
}
