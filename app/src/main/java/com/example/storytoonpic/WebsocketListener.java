package com.example.storytoonpic;

import android.content.Context;

import com.example.storytoonpic.MainActivity.Utils;

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
        String receiveMOD = "";
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
        if(text.equals("---IMAGE START---")) {
            receiveMOD = "IMAGE";
        }
        if(receiveMOD.equals("IMAGE")) {
            // todo: 이미지받기
        }
        if(text.equals("---IMAGE END---")) {
            receiveMOD = "";
        }
        if(text.equals("---STORY START---")) {
            receiveMOD = "STORY";
        }
        if(receiveMOD.equals("STORY")) {
            // todo: 스토리받기
        }
        if(text.equals("---STORY END---")) {
            receiveMOD = "";
            webSocket.close(1000, "end");
            Context MainActivity = null;
            new Utils().showToast(MainActivity);
        }
    }
}
