package com.example.storytoonpic;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WebsocketListener extends WebSocketListener {
    String receiveMOD = "";


    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send("Execute");
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString byteString) {
        if(receiveMOD.equals("IMAGE")) {
            byte[] imageBytes = byteString.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            State.images.add(bitmap);
            System.out.println(State.images);
        }
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
        if(text.equals("---IMAGE START---")) {
            receiveMOD = "IMAGE";
            State.images.clear();
        }
        if(text.equals("---IMAGE END---")) {
            receiveMOD = "";
        }
        if(text.equals("---STORY START---")) {
            receiveMOD = "STORY";
        }
        if(text.equals("---STORY END---")) {
            webSocket.close(1000, "end");
            receiveMOD = "";
        }
        if(receiveMOD.equals("STORY")) {
            State.story = text;
        }
    }


}
