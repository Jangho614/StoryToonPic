package com.example.storytoonpic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private static final String TAG = "ShopNewsActivity";
    private ViewPager2 pager;
    private ImgAdapter pagerAdapter;
    private CircleIndicator indicator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Use getActivity() to get the context of the activity containing this fragment
//        pager = view.findViewById(R.id.pager_images);
//        pagerAdapter = new ImgAdapter(getActivity()); // Pass getActivity() as context
//        pager.setAdapter(pagerAdapter);
//
//        indicator = view.findViewById(R.id.indicator);
//        indicator.setViewPager(pager);

        return view;
    }
}
