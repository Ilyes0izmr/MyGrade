package com.example.gpaCalculator.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.gpaCalculator.MainActivity;
import com.example.gpaCalculator.model.database.DatabaseSeeder;
import com.example.myapplication.R;
import com.example.gpaCalculator.controller.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    /**
     * Entry point of the activity.
     * Displays a GIF using Glide and navigates to MainActivity after a delay.
     *
     * @param savedInstanceState If the activity is being re-initialized, this Bundle contains its previous state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // load the gif//
        ImageView gifImageView = findViewById(R.id.gif_splash);
        Glide.with(this)
                .asGif()
                .load(R.drawable.splash_gif)
                .into(gifImageView);

        // initialize ViewModel
        SplashViewModel splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        // delay navigation to MainActivity
        new Handler(Looper.getMainLooper()).postDelayed(this::navigateToLoginActivity, 4300);

    }
    private void navigateToLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Closes SplashActivity so it doesn’t stay in the back stack
    }

}
