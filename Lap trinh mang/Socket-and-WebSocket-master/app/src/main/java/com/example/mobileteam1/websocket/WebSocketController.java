package com.example.mobileteam1.websocket;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import rx.Observable;
import rx.functions.Action1;

public class WebSocketController {

    public static final String SERVER_URL = "ws://localhost:8080/WebSocketDemo/actions";

    private WebSocket ws;

    public WebSocketController() {
        startSocket(SERVER_URL);
        wsSendMessage("Hello there!");
    }

    private void startSocket(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        VPBSWebSocketListener listener = new VPBSWebSocketListener();
        ws = client.newWebSocket(request, listener);
    }

    private void wsSendMessage(String text) {
        ws.send(text);
    }

    public void reConnectWs() {
        Log.e(this.getClass().getSimpleName(), "reConnecting...");
        Observable.just(true)
                .delay(5000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean result) {
                        startSocket(SERVER_URL);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("reConnectWs", throwable.toString());
                    }
                });
    }
}
