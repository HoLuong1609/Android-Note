/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifs;

/**
 *
 * @author mobileTeam1
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/actions")
public class DeviceWebSocketServer {

    @OnOpen
    public void open(Session session) {
        try {
            session.getBasicRemote().sendText("You are connected. Your ID is " + session.getId());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @OnClose
    public void close(Session session) {
        System.out.println(session.getId() + " was disconnected!");
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println(error.getMessage());
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        System.out.println("handleMessage: " + message);
    }
}
