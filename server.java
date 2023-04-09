import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;// for reading
    PrintWriter out;// for writing

    public server() {
        try {
            server = new ServerSocket(7777);
            System.out.println("sever is ready to connect");
            socket = server.accept();
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            startWriting();
            startreading();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void startreading() {
        Runnable r1 = () -> {
            System.out.println("reader started...");
            while (true) {
                try {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("client terminated the chat");
                        break;
                    } else {
                        LocalTime dt = LocalTime.now();
                        System.out.println("Client : " + msg + " [" + dt.getHour() + " : " + dt.getMinute() + " : "
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
        System.out.println("Enter your name");
        Scanner sc = new Scanner(System.in);

        // public String name = sc.nextLine();

        new server();
    }
}
