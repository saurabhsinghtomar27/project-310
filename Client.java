package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    BufferedReader reading;
    Socket socket;
    PrintWriter sending;

    public Client() {
        try {
            System.out.println("Sending Request to Server");
            socket = new Socket("127.0.0.1", 7777);
            reading = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sending = new PrintWriter(socket.getOutputStream());
            read();
            send();
        } catch (Exception e) {
            // TODO: handle exception

        }

    }

    public void send() throws IOException {
        Runnable r2 = () -> {
            System.out.println("sending started");
            Scanner sc = new Scanner(System.in);
            String msg = sc.nextLine();
            sending.println(msg);
            sending.flush();
            sc.close();

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
                        socket.close();
                        break;
                    } else {
                        System.out.println("Server :" + s);
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
        new Client();
    }
}