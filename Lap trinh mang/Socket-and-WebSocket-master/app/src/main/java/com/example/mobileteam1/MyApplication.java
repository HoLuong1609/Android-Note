package com.example.mobileteam1;

import com.example.mobileteam1.websocket.WebSocketController;

public class MyApplication {

    private static WebSocketController webSocketController;

    public static void main(String[] args) {
        webSocketController = new WebSocketController();

    }

    public static WebSocketController getWebSocketController() {
        return webSocketController;
    }
}
