import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;

import java.util.Scanner;

public class client {
    BufferedReader br;// for reading
    PrintWriter out;// for writing
    Socket socket;

    public client() {
        try {
            System.out.println("sending request to server");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("connetction done");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startWriting();
            startreading();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void startreading() {
        Runnable r1 = () -> {
            System.out.println("reader started...");
            while (true) {
                try {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("server terminated the chat");
                        break;
                    } else {
                        LocalTime dt = LocalTime.now();
                        System.out.println("server: " + msg + " [" + dt.getHour() + " : " + dt.getMinute() + " : "
                                + dt.getSecond() + "]");
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {
            System.out.println("writer started");
            while (true) {
                try {
                    Scanner sc = new Scanner(System.in);
                    String cont = sc.nextLine();

                    out.println(cont);
                    out.flush();

                } catch (Exception e) {

                }
            }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is cliet");
        Scanner sc = new Scanner(System.in);
        new client();
    }
}
