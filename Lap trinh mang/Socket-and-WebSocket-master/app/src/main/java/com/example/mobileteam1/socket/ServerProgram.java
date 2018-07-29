package com.example.mobileteam1.socket;

import com.example.mobileteam1.model.Person;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static com.example.mobileteam1.Constant.PORT;

public class ServerProgram {

    public static void main(String[] args) {
//        acceptConnection();
        receiveSerializeObject();
    }

    private static void acceptConnection() {
        ServerSocket serverSocket = null;
        String line;
        BufferedReader is;
        BufferedWriter os;
        Socket socket;


        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Server is waiting to accept user...");

        try {
            socket = serverSocket.accept();
            System.out.println("Accept a client!");

            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                line = is.readLine();

                os.write(">>" + line);
                os.newLine();
                os.flush();

                if (line.equals("QUIT")) {
                    os.write(">> OK");
                    os.newLine();
                    os.flush();
                    break;
                }
            }
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server stopped!");
    }

    private static void receiveSerializeObject() {
        ServerSocket serverSocket = null;
        Socket socket;
        ObjectOutputStream out;
        ObjectInputStream in;

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Server is waiting to accept user...");

        try {
            socket = serverSocket.accept();
            System.out.println("Accept a client!");


            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());


            Person person;
            if ((person = (Person) in.readObject()) != null)
                System.out.println(person.toString());
            out.writeObject("bye bye");
            out.close();
            in.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
