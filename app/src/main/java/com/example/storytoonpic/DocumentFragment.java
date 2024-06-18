package com.example.storytoonpic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.storytoonpic.R;

import me.relex.circleindicator.CircleIndicator3;
import java.util.Arrays;
import java.util.List;


public class DocumentFragment extends Fragment {

    private ViewPager2 viewPager2;
    private CircleIndicator3 indicator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        viewPager2 = view.findViewById(R.id.pager_images);
        indicator = view.findViewById(R.id.indicator);

        List<Integer> imageList = Arrays.asList(
                R.drawable.image1, // replace with your drawable resources
                R.drawable.image2
        );

        ImageAdapter adapter = new ImageAdapter(imageList);
        viewPager2.setAdapter(adapter);
        indicator.setViewPager(viewPager2);

        return view;
    }
}

