package com.example.gpaCalculator.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.gpaCalculator.view.MainActivity;
import com.example.gpaCalculator.viewmodel.SplashViewModel;
import com.example.myapplication.R;

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

        // Load GIF into ImageView
        ImageView gifImageView = findViewById(R.id.gif_splash);
        Glide.with(this)
                .asGif()
                .load(R.drawable.splash_gif) // Ensure splash_gif.gif is in res/drawable
                .into(gifImageView);

        // Initialize ViewModel (if needed)
        SplashViewModel splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        // Delay navigation to MainActivity
        new Handler(Looper.getMainLooper()).postDelayed(this::navigateToMainActivity, 2000);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
