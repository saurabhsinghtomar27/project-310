package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader reading;
    PrintWriter sending;

    public Server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept the connection");
            socket = server.accept();
            reading = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sending = new PrintWriter(socket.getOutputStream());
            read();
            send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send() {
        Runnable r2 = () -> {
            System.out.println("sending started");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                String msg = br.readLine();
                sending.println(msg);
                sending.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        };
        new Thread(r2).start();
    }

    public void read() {
        Runnable r1 = () -> {
            System.out.println("Reading message");
            while (true) {
                try {
                    String s = reading.readLine();
                    if (s.equals("exit")) {
                        System.out.println("Chat is terminated");
                        break;
                    } else {
                        System.out.println("client :" + s);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        new Thread(r1).start();
    }

    public static void main(String[] args) {
        new Server();
    }
}
