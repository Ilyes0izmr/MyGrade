package com.example.gpaCalculator.repository;



import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gpaCalculator.model.Module;
import com.example.gpaCalculator.repository.api.ModuleApi;
import com.example.gpaCalculator.repository.api.RetrofitClient;
import com.example.gpaCalculator.viewmodel.GPAViewModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GPARepository {
    private ModuleApi moduleApi;

    public GPARepository() {
        moduleApi = RetrofitClient.getClient().create(ModuleApi.class);
    }

    /**
     * Fetches modules from the API.
     *
     * @param semester      The semester number.
     * @param specialization The specialization ID.
     * @return A LiveData object containing the list of modules.
     */
    public LiveData<List<Module>> fetchModules(int semester, int specialization) {
        MutableLiveData<List<Module>> data = new MutableLiveData<>();

        // Make the API call
        moduleApi.getModules(semester, specialization).enqueue(new Callback<List<Module>>() {
            @Override
            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body()); // Success: Set the fetched modules
                } else {
                    // Handle empty or invalid response
                    String errorMessage = "Invalid response from server.";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string(); // Get error details
                        } catch (IOException e) {
                            errorMessage = "Failed to parse error response.";
                        }
                    }
                    data.setValue(null); // Set null value for LiveData
                    propagateError(errorMessage); // Propagate the error
                }
            }

            @Override
            public void onFailure(Call<List<Module>> call, Throwable t) {
                // Handle network failure
                String errorMessage = "Failed to fetch modules: " + t.getMessage();
                data.setValue(null); // Set null value for LiveData
                propagateError(errorMessage); // Propagate the error
            }
        });

        return data;
    }

    /**
     * Propagates an error message to the ViewModel.
     *
     * @param errorMessage The error message to propagate.
     */
    private void propagateError(String errorMessage) {
        // This method can be used to notify the ViewModel about errors.
        // You can pass the error message to the ViewModel using a callback or interface.
        // For now, we'll leave this as a placeholder.
        System.err.println("Error: " + errorMessage); // Log the error for debugging
    }
}