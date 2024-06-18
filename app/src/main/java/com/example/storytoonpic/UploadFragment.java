package com.example.storytoonpic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.storytoonpic.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadFragment extends Fragment {
    private static final int REQUEST_PICK_IMAGE = 102;
    private static final int MAX_IMAGES = 4;
    private List<Uri> selectedImageUris = new ArrayList<>();
    private ImageView[] uploadedImageViews = new ImageView[MAX_IMAGES];
    private TextView[] uploadTextViews = new TextView[MAX_IMAGES];
    private View[] imageFrames = new View[MAX_IMAGES];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        // Initialize image views, text views, and frames
        for (int i = 0; i < MAX_IMAGES; i++) {
            int imageViewId = getResources().getIdentifier("uploaded_image" + (i + 1), "id", getActivity().getPackageName());
            int textViewId = getResources().getIdentifier("upload_text" + (i + 1), "id", getActivity().getPackageName());
            int frameId = getResources().getIdentifier("frame_image_" + (i + 1), "id", getActivity().getPackageName());

            uploadedImageViews[i] = view.findViewById(imageViewId);
            uploadTextViews[i] = view.findViewById(textViewId);
            imageFrames[i] = view.findViewById(frameId);

            final int index = i;
            imageFrames[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery(index);
                }
            });
        }

        view.findViewById(R.id.upload_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImageUris.size() == MAX_IMAGES) {
                    showUploadStatusPopup();
                } else {
                    showImageCountWarning();
                }
            }
        });

        return view;
    }

    private void openGallery(int index) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), REQUEST_PICK_IMAGE + index);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            int index = requestCode - REQUEST_PICK_IMAGE;
            if (index >= 0 && index < MAX_IMAGES) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUris.contains(selectedImageUri)) {
                    showDuplicateImageWarning();
                    return;
                }
                if (selectedImageUris.size() >= MAX_IMAGES) {
                    showImageCountWarning();
                    return;
                }
                selectedImageUris.add(selectedImageUri);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    uploadedImageViews[index].setImageBitmap(bitmap);
                    uploadedImageViews[index].setVisibility(View.VISIBLE);
                    uploadTextViews[index].setVisibility(View.GONE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showUploadStatusPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Upload Status");
        builder.setMessage("All images uploaded successfully!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 업로드 성공 후 이미지 초기화
                resetUploadedImages();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 업로드된 이미지 초기화
    private void resetUploadedImages() {
        selectedImageUris.clear(); // 선택된 이미지 목록 초기화
        for (int i = 0; i < MAX_IMAGES; i++) {
            uploadedImageViews[i].setImageDrawable(null); // 이미지뷰 초기화
            uploadedImageViews[i].setVisibility(View.GONE); // 이미지뷰 숨기기
            uploadTextViews[i].setVisibility(View.VISIBLE); // 텍스트뷰 보이기
        }
    }



    private void showImageCountWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warning");
        builder.setMessage("Please upload exactly 4 images.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showDuplicateImageWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warning");
        builder.setMessage("This image is already selected. Please select a different image.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
