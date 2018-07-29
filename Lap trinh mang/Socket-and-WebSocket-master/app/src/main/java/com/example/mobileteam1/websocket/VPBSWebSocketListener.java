package com.example.mobileteam1.websocket;

import com.example.mobileteam1.MyApplication;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * @ Created by MinhPQ on 9/16/2017.
 */

public class VPBSWebSocketListener extends WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;

    private static final String TAG = VPBSWebSocketListener.class.getSimpleName();

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.e("VPBSWebSocketListener", "open socket");

    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.e("VPBSWebSocketListener", "onMessage -" + text);
        try {
            getMessageData(text);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {

    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Log.e("VPBSWebSocketListener", "onClosing websocket");
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.e("VPBSWebSocketListener", "onFailure websocket " + t.getMessage());
        MyApplication.getWebSocketController().reConnectWs();
    }

    private void getMessageData(String data) {

    }
}
