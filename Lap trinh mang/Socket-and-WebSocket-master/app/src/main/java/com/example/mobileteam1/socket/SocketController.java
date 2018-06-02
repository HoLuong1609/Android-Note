package com.example.mobileteam1.socket;

import com.example.mobileteam1.model.Person;
import com.example.mobileteam1.websocket.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.example.mobileteam1.Constant.PORT;
import static com.example.mobileteam1.Constant.SERVER_HOST;

public class SocketController {

    private static final String TAG = SocketController.class.getSimpleName();

    private Socket socket;

    public void startSocket() {
        try {
            socket = new Socket(SERVER_HOST, PORT);
            sendSerializedObject();
        } catch (ConnectException e) {
            Log.e("Socket Connection failed:", e.toString());
            reConnectSocket();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendSerializedObject() {
        ObjectOutputStream out;
        ObjectInputStream in;

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            Person person = new Person("Alex", 18);

            out.writeObject(person);
            out.flush();

            String message;
            while ((message = (String) in.readObject()) != null) {
                System.out.println(message);
                out.writeObject("bye");

                if (message.equals("bye bye"))
                    break;
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void reConnectSocket() {
        Log.e(TAG, "reconnecting Socket...");
        try {
            Thread.sleep(5000);
            startSocket();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closeSocket() {
        if (socket.isClosed())
            Log.e(TAG, "Socket has already closed!");
        else {
            Log.e(TAG, "closing Socket");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
