package com.example.gpaCalculator.model;
import com.example.gpaCalculator.model.entities.Announcement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/v1/ann/announcement") // Endpoint path here
    Call<List<Announcement>> getAnnouncements();
}
