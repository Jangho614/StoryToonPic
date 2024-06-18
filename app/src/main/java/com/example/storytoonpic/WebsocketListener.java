package com.example.storytoonpic;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebsocketListener extends WebSocketListener {
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send("Execute");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("Received message: " + text);
        if(text.equals("drawing pictures")) {
            // todo: 그림을 그리는 중
        }
        if(text.equals("drawing completed")) {
            // todo: 그림 다그림
        }
        if(text.equals("writing story")) {
            // todo: 스토리 짜는 중
        }
        if(text.equals("done! please send 'GET /download_photo' request")) {
            webSocket.close(1000, "end");
        }
    }
}
