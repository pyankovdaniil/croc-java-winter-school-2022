package ru.croc.task11.client;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientListener implements Runnable {
    public static List<ClientListener> clientListeners = new CopyOnWriteArrayList<>();
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String clientUsername;

    public ClientListener(Socket socket) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientUsername = reader.readLine();
            clientListeners.add(this);
            outMessage("SERVER: " + clientUsername + " has entered the chat!");
        } catch (IOException e) {
            close(socket, reader, writer);
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                String messageFromClient = reader.readLine();
                outMessage(messageFromClient);
            } catch (IOException e) {
                close(socket, reader, writer);
                break;
            }
        }
    }

    private void outMessage(String message) {
        for (ClientListener listener : clientListeners) {
            try {
                if (!listener.clientUsername.equals(this.clientUsername)) {
                    listener.writer.write(message);
                    listener.writer.newLine();
                    listener.writer.flush();
                }
            } catch (IOException e) {
                close(socket, reader, writer);
            }
        }
    }

    private void removeClientListener() {
        clientListeners.remove(this);
        outMessage("SERVER: " + clientUsername + " has let the chat!");
    }

    private void close(Socket socket, BufferedReader reader, BufferedWriter writer) {
        removeClientListener();
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
}
