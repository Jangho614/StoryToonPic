package com.example.storytoonpic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ViewFragment extends Fragment {
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatter);
    String currentTimeString = formattedDateTime;


    String title = "오늘의 일기";
    String date = currentTimeString;
    Bitmap im4;

    public RecyclerView recyclerView;
    static ViewAdapter adapter = new ViewAdapter();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        recyclerView = view.findViewById(R.id.viewRecycler);

        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Bitmap im4 = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        //im4 = imgList.get(4);
        adapter.addItem(new ViewAdapter.Item(title, date, im4));


        return view;
    }
}
