package com.example.storytoonpic;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

@RequiresApi(api = Build.VERSION_CODES.O)
public class WebsocketListener extends WebSocketListener {
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatter);
    String currentTimeString = formattedDateTime;
    String receiveMOD = "";
    ArrayList<Bitmap> imgList = new ArrayList();
    String story = "";


    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send("Execute");
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString byteString) {
        if(receiveMOD.equals("IMAGE")) {
            byte[] imageBytes = byteString.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgList.add(bitmap);
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
        }

        if(text.equals("---IMAGE END---")) {
            receiveMOD = "";
        }
        if(text.equals("---STORY START---")) {
            receiveMOD = "STORY";
        }
        if(receiveMOD.equals("STORY")) {
            story = text;
        }
        if(text.equals("---STORY END---")) {
            receiveMOD = "";
            webSocket.close(1000, "end");

            // 비트맵 배열리스트
            imgList = imgList;
            // 스토리 스트링
            story = story;


            new ViewFragment.addview().additem("title",currentTimeString,imgList.get(4));
            new HomeFragment.addimg().additem(imgList.get(0),imgList.get(1),imgList.get(2),imgList.get(3));
        }
    }
}
