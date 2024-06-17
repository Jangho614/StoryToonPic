package com.example.storytoonpic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class UploadFragment extends Fragment {
    Button Upload_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_upload, container, false);

        Upload_btn = view.findViewById(R.id.upload_btn); // 레이아웃에서 버튼을 찾아 초기화

        Upload_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "이미지 업로딩 중...", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
