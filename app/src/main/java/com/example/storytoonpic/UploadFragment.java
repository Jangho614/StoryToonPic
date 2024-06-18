package com.example.storytoonpic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;


public class UploadFragment extends Fragment {
    private static final int REQUEST_PICK_IMAGE = 102;
    private static final int MAX_IMAGES = 4;
    private List<Uri> selectedImageUris = new ArrayList<>();
    private ImageView[] uploadedImageViews = new ImageView[MAX_IMAGES];
    private TextView[] uploadTextViews = new TextView[MAX_IMAGES];
    private View[] imageFrames = new View[MAX_IMAGES];

    OkHttpClient client = new OkHttpClient();

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
                    postImage();
                    connectWS("test"); // todo: change need
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

    private void showFailedImageWarning() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warning");
        builder.setMessage("Upload failed. Please try again.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void postImage() {
        // OkHttpClient 인스턴스 생성
        OkHttpClient client = new OkHttpClient();

        // URL 빌더를 사용해 HttpUrl 생성
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http") // 혹은 "https" 사용
                .host("211.34.216.22") // 211.34.216.22
                .port(5232)
                .addPathSegment("upload_photo")
                .addQueryParameter("id", "test")
                .build();

        // MultipartBody 생성
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        selectedImageUris.forEach(uri -> {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                builder.addFormDataPart("files", "image.jpg",
                        RequestBody.create(byteArray, MediaType.parse("image/*jpg")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        RequestBody requestBody = builder.build();

        // Request 생성
        Request request = new Request.Builder()
                .url(httpUrl) // TODO: USER IDENTIFY NAME NEEDED
                .post(requestBody)
                .build();

        // 비동기 요청
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 네트워크 요청 실패 처리
                System.out.println("Request failed");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    // 성공적인 응답 처리
                    String responseData = response.body().string();
                    System.out.println("Response success: " + responseData);
                } else {
                    // 실패 응답 처리
                    System.out.println("Response failed");
                }
            }
        });
    }


    private void connectWS(String id) {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http") // 혹은 "https" 사용
                .host("211.34.216.22") // 211.34.216.22
                .port(5232)
                .addPathSegment("ws")
                .addPathSegment(id)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        WebSocket websocket = client.newWebSocket(request, new WebsocketListener());
    }

}
