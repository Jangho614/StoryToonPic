package com.example.storytoonpic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class HomeFragment extends Fragment {

    ViewPager2 viewPager;
    ImageSliderAdapter sliderAdapter;
    private CircleIndicator3 indicator;
    static List<Bitmap> images = new ArrayList<>();
    TextView titlev,datev,contentv;
    String title,date,content;



    public static class addimg{
        public void additem(Bitmap im1, Bitmap im2, Bitmap im3, Bitmap im4){
            images.add(im1);
            images.add(im2);
            images.add(im3);
            images.add(im4);
        }

    }

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
        titlev.setText("Title : ");
        datev.setText("Date : ");
        contentv.setText("Content : ");

        return view;
    }

    private String gettitle() {
    }
}
