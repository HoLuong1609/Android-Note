package com.example.mobileteam1.datagramsocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class Example {

    public static final String SERVER_IP = "192.168.0.182";
    public static final int SERVER_PORT = 8888;
    public static final byte[] BUFFER = new byte[4096];

    public static void main(String[] args) throws IOException, InterruptedException {
//        connectServer();
        clientNoConnectMode();
    }

    private static void clientNoConnectMode() throws IOException {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            System.out.println("Client started ");

            while (true) {
                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
                ds.receive(incoming);

                System.out.println("Received: " + new String(incoming.getData(), 0, incoming.getLength()));
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } finally {
            if (ds != null)
                ds.close();
        }
    }

    private static void connectServer() throws IOException, InterruptedException {
        Socket socket = null;
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected: " + socket);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            for (int i = '0'; i <= '9'; i++) {
                os.write(i);
                int ch = is.read();
                System.out.println((char) ch + " ");
                Thread.sleep(200);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Can not connect to server");
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
