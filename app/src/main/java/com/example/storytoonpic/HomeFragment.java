package com.example.storytoonpic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    List<Integer> images = new ArrayList<>();
    private CircleIndicator3 indicator;

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

        return view;
    }
}
