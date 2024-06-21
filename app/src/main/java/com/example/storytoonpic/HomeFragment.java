package com.example.storytoonpic;

import static com.example.storytoonpic.ViewFragment.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeFragment extends Fragment {

    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatter);
    String currentTimeString = formattedDateTime;

    ViewPager2 viewPager;
    static ImageSliderAdapter sliderAdapter;
    static List<Bitmap> images = new ArrayList<>();
    TextView datev, contentv;
    String date = currentTimeString;
    Button toViewBtn;

    String title, content;
    Bitmap im0,im1,im2,im3;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Fragment의 레이아웃을 inflate 합니다.
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.pager_images);

        // 이미지 추가
        sliderAdapter = new ImageSliderAdapter(requireContext(), images);
        viewPager.setAdapter(sliderAdapter);

        toViewBtn = view.findViewById(R.id.view_upload_btn);

        datev = view.findViewById(R.id.home_day);
        contentv = view.findViewById(R.id.home_content);

        images.clear();

        System.out.println(State.images);
        if(!State.images.isEmpty()){
            im0 = State.images.get(0);
            im1 = State.images.get(1);
            im2 = State.images.get(2);
            im3 = State.images.get(3);
        }
        //im0~3 = imgList.get(0~3);
        //content = Story;
        images.add(im0);
        images.add(im1);
        images.add(im2);
        images.add(im3);

        if(!State.story.isEmpty()) {
            content = State.story;
        }
        contentv.setText("Content: " + content);
        datev.setText("Date : " + date);

        toViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addItem(new ViewAdapter.Item(content, date, im0,im1,im2,im3));
            }
        });


        return view;
    }
}
