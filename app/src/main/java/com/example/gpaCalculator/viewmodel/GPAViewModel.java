package com.example.gpaCalculator.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gpaCalculator.model.Module;
import com.example.gpaCalculator.repository.GPARepository;

import java.util.List;

public class GPAViewModel extends AndroidViewModel {
    private GPARepository repository;
    private LiveData<List<Module>> modulesLiveData;
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public GPAViewModel(@NonNull Application application) {
        super(application);
        repository = new GPARepository();
    }

    public LiveData<List<Module>> getModules() {
        return modulesLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    // Fetch modules with default values for semester and specialization
    public void fetchModules() {
        fetchModules(1, 184); // Default values
    }

    // Fetch modules with specified semester and specialization
    public void fetchModules(int semester, int specialization) {
        modulesLiveData = repository.fetchModules(semester, specialization);
    }

    // Propagate errors from the repository to the ViewModel
    public void setError(String errorMessage) {
        errorLiveData.setValue(errorMessage);
    }
}