package com.example.storytoonpic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import me.relex.circleindicator.CircleIndicator3;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HomeFragment extends Fragment {

    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatter);
    String currentTimeString = formattedDateTime;

    ViewPager2 viewPager;
    static ImageSliderAdapter sliderAdapter;
    private CircleIndicator3 indicator;
    static List<Bitmap> images = new ArrayList<>();
    TextView titlev, datev, contentv;
    String date = currentTimeString;

    String title, content;
    Bitmap im0,im1,im2,im3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Fragment의 레이아웃을 inflate 합니다.
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.pager_images);

        // 이미지 추가
        sliderAdapter = new ImageSliderAdapter(requireContext(), images);
        viewPager.setAdapter(sliderAdapter);
        indicator = view.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        titlev = view.findViewById(R.id.home_titleText);
        datev = view.findViewById(R.id.home_day);
        contentv = view.findViewById(R.id.home_content);

        Bitmap im0 = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Bitmap im1 = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Bitmap im2 = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Bitmap im3 = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        //im0~3 = imgList.get(0~3);
        //content = Story;
        images.add(im0);
        images.add(im1);
        images.add(im2);
        images.add(im3);

        titlev.setText("Title : " + title);
        contentv.setText("Content: " + content);
        datev.setText("Date : " + date);


        return view;
    }

}
