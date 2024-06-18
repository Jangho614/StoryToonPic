package com.example.storytoonpic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private DocumentFragment fd;
    private HomeFragment fh;
    private UploadFragment fu;

    private ImageView imgBg1;
    private ImageView imgBg2;
    private ImageView imgBg3;

    private BottomNavigationView bottomNavigationView;
    private View selectedView;
    private ImageView currentSelected;
    private ImageView selectedDocument;
    private ImageView selectedHome;
    private ImageView selectedUpload;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        fd = new DocumentFragment();
        fh = new HomeFragment();
        fu = new UploadFragment();

        selectedDocument = findViewById(R.id.selected_document);
        selectedHome = findViewById(R.id.selected_home);
        selectedUpload = findViewById(R.id.selected_upload);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fh).commit();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        imgBg1 = findViewById(R.id.icon_bg_1);
        imgBg2 = findViewById(R.id.icon_bg_2);
        imgBg3 = findViewById(R.id.icon_bg_3);

        // Initially hide all icon_bg views except the one for f2 (selected by default)
        imgBg1.setVisibility(View.INVISIBLE);
        imgBg2.setVisibility(View.INVISIBLE);
        imgBg3.setVisibility(View.INVISIBLE);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                ImageView selectedIconView = null;
                View selectedImageView = null;


                if (item.getItemId() == R.id.navigation_document) {
                    selectedFragment = fd;
                    selectedImageView = imgBg1;
                    selectedIconView = selectedDocument;
                } else if (item.getItemId() == R.id.navigation_home) {
                    selectedFragment = fh;
                    selectedImageView = imgBg2;
                    selectedIconView = selectedHome;
                } else if (item.getItemId() == R.id.navigation_upload) {
                    selectedFragment = fu;
                    selectedImageView = imgBg3;
                    selectedIconView = selectedUpload;
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
                }

                if (selectedImageView != null) {
                    animateImageView(selectedImageView);
                }

                if (selectedIconView != null) {
                    showSelectedImage(selectedIconView);
                }

                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    private void clearAnimation(View view) {
        view.clearAnimation();
    }

    private void animateImageView(View imageView) {
        if (selectedView != null && selectedView != imageView) {
            clearAnimation(selectedView); // Clear animation of previously selected view
            slideViewUp(selectedView); // Slide up the previously selected view
        }

        applySlideDownAnimation(imageView); // Apply slide down animation to the selected view
        selectedView = imageView;
    }

    private void applySlideDownAnimation(View view) {
        if (view == null) return;

        view.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        view.startAnimation(animation);
    }

    private void slideViewUp(View view) {
        if (view == null) return;

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(animation);
    }

    private void showSelectedImage(ImageView selectedImageView) {
        if (currentSelected != null && currentSelected != selectedImageView) {
            hideSelectedImage(currentSelected);
        }

        // Adjust this value to move the image higher
        float fromYDelta = selectedImageView.getHeight() * 0.25f;

        selectedImageView.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, fromYDelta, 0);
        animate.setDuration(300);
        animate.setFillAfter(true);
        selectedImageView.startAnimation(animate);
        currentSelected = selectedImageView;
    }



    private void hideSelectedImage(ImageView selectedImageView) {

        float toYDelta = selectedImageView.getHeight() * 0.25f; // Adjust this value to move the image higher

        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, toYDelta);
        animate.setDuration(300);
        animate.setFillAfter(true);
        selectedImageView.startAnimation(animate);
        selectedImageView.setVisibility(View.INVISIBLE);
    }
}
