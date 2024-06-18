package com.example.storytoonpic;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WebsocketListener extends WebSocketListener {
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatter);
    String currentTimeString = formattedDateTime;


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
//            new ViewFragment.addview().additem("title",currentTimeString,bitmap);
//            new HomeFragment.addimg().additem(im1,im2,im3,im4);



        }
    }
}
