package com.example.gpaCalculator.repository.api;


import com.example.gpaCalculator.model.Module;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Query;

/**
 * interface class that handles the http operations using Retrofit
 *
 */
public interface ModuleApi {
    @GET("formations/get_modules_json")
    Call<List<Module>> getModules(
            @Query("sem") int semester,
            @Query("spec") int specialization
    );
}
