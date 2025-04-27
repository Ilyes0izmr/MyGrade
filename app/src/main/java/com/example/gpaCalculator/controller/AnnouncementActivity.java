package com.example.gpaCalculator.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpaCalculator.model.AnnouncementAdapter;
import com.example.gpaCalculator.model.ApiService;
import com.example.gpaCalculator.model.entities.Announcement;
import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnnouncementActivity extends AppCompatActivity
        implements AnnouncementAdapter.OnLinkClickListener {

    private RecyclerView recyclerView;
    private AnnouncementAdapter announcementAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter with empty list and this activity as click listener
        announcementAdapter = new AnnouncementAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(announcementAdapter);

        // Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://680da6b7c47cb8074d90d956.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create the API service
        ApiService apiService = retrofit.create(ApiService.class);

        // Make the API call
        Call<List<Announcement>> call = apiService.getAnnouncements();
        call.enqueue(new Callback<List<Announcement>>() {
            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Announcement> announcements = response.body();
                    if (!announcements.isEmpty()) {
                        announcementAdapter.updateAnnouncements(announcements);
                    } else {
                        Toast.makeText(AnnouncementActivity.this,
                                "No announcements found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String error = "Error: " + response.code();
                    try {
                        error += " - " + response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(AnnouncementActivity.this,
                            error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {
                Log.e("API Error", "Request failed", t);
                Toast.makeText(AnnouncementActivity.this,
                        "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onLinkClick(String url) {
        // Handle link click - open in browser
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}