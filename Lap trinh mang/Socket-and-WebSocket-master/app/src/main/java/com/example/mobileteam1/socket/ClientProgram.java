package com.example.mobileteam1.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static com.example.mobileteam1.Constant.PORT;
import static com.example.mobileteam1.Constant.SERVER_HOST;

public class ClientProgram {

    public static void main(String[] args) {
//        connectSocket();
        final SocketController socketController = new SocketController();
        socketController.startSocket();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        socketController.closeSocket();
    }

    private static void connectSocket() {
        Socket socket;
        BufferedWriter os;
        BufferedReader is;

        try {
            socket = new Socket(SERVER_HOST, PORT);

            os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            os.write("Hello!");
            os.newLine();
            os.flush();
            os.write("I am Tom Cat");
            os.newLine();
            os.flush();
            os.write("QUIT");
            os.newLine();
            os.flush();

            String responseLine;
            while ((responseLine = is.readLine()) != null) {
                System.out.println("Server: " + responseLine);
                if (responseLine.contains("OK")) {
                    break;
                }
            }

            os.close();
            is.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
