package com.example.gpaCalculator.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.gpaCalculator.model.Module;
import com.example.gpaCalculator.viewmodel.GPAViewModel;
import com.example.myapplication.R;

import java.util.List;

public class GPAActivity extends AppCompatActivity {

    private GPAViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(GPAViewModel.class);

        // Observe the modules LiveData
        viewModel.getModules().observe(this, new Observer<List<Module>>() {
            @Override
            public void onChanged(List<Module> modules) {
                if (modules != null && !modules.isEmpty()) {
                    Toast.makeText(GPAActivity.this, modules.get(0).getNom_module(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Observe the error LiveData
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(GPAActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Fetch modules when the activity starts
        viewModel.fetchModules();
    }
}