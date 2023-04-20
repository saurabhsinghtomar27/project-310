import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class client extends JFrame {
    BufferedReader br;// for reading
    PrintWriter out;// for writing
    Socket socket;
    private JLabel heading = new JLabel("Client");
    private JTextArea mesArea = new JTextArea();
    private JTextField messageINput = new JTextField();
    // private Font font=new Font("MV Boli", Font.PLAIN, 40);

    public client() {
        try {
            System.out.println("sending request to server");
            socket = new Socket("192.168.198.43", 7778);
            System.out.println("connection done");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            gui1();
            handleEvent();
            // startWriting();
            startreading();
        } catch (Exception e) {

            e.setStackTrace(null);
        }
    }

    private void handleEvent() {
        messageINput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                // throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                System.out.println("Key released " + e.getKeyCode());
                if (e.getKeyCode() == 10) {
                    LocalTime dt = LocalTime.now();
                    String contentToSend = messageINput.getText();
                    mesArea.append("Me :" + contentToSend + " [" + dt.getHour() + " : " + dt.getMinute() + " : "
                            + dt.getSecond() + "]\n");
                    out.println(contentToSend);
                    out.flush();
                    messageINput.setText("");
                    messageINput.requestFocus();
                }
            }

        });
    }

    private void gui1() {

        this.setTitle("Chat Application");
        this.setSize(550, 550);
        this.setLocationRelativeTo(null); // center
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        messageINput.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.MAGENTA);
        mesArea.setEditable(false);
        heading.setSize(1000, 1000);
        // heading.setFont("MV Boli", Font.PLAIN, 40);
        JScrollPane jScrollPane = new JScrollPane(mesArea);
        this.add(heading, BorderLayout.NORTH);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(messageINput, BorderLayout.SOUTH);
    }

    public void startreading() {
        Runnable r1 = () -> {

            while (true) {
                try {
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        JOptionPane.showMessageDialog(this, "Chat Terminated");
                        messageINput.setEnabled(false);
                        System.out.println("server terminated the chat");
                        break;
                    } else {
                        LocalTime dt = LocalTime.now();
                        mesArea.append("server: " + msg + " [" + dt.getHour() + " : " + dt.getMinute() + " : "
                                + dt.getSecond() + "]\n");
                    }
                } catch (Exception e) {
                    e.setStackTrace(null);
                }

            }
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {

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

        new client();
    }
}
