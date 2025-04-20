package com.example.gpaCalculator.controller;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpaCalculator.model.Module;
import com.example.myapplication.R;

import java.util.List;
//
public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.ModuleViewHolder> {
    private static List<Module> moduleList;

    public ModuleAdapter(List<Module> moduleList) {
        this.moduleList = moduleList;
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_item, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module module = moduleList.get(position);
        holder.bind(module);
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    public List<Module> getModules() {
        return moduleList;
    }

    public static class ModuleViewHolder extends RecyclerView.ViewHolder {
        private final TextView moduleName;
        private final TextView coefficientText;
        private final TextView resultText;
        private final EditText tdInput;
        private final EditText tpInput;
        private final EditText examInput;

        public ModuleViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleName = itemView.findViewById(R.id.moduleName);
            coefficientText = itemView.findViewById(R.id.moduleCoefficient);
            resultText = itemView.findViewById(R.id.moduleResult);
            tdInput = itemView.findViewById(R.id.tdInput);
            tpInput = itemView.findViewById(R.id.tpInput);
            examInput = itemView.findViewById(R.id.examInput);
        }

        public void bind(Module module) {
            // Set module name and coefficient
            moduleName.setText(module.getNom_module());
            coefficientText.setText("Coefficient: " + module.getCoefficient());

            // Handle TD Visibility
            if (module.isHasTD()) {
                // Add a debug state to find why ??
                Log.d("HAS_TD",String.valueOf(module.isHasTD()));

                tdInput.setVisibility(View.VISIBLE);
            } else {
                Log.d("HAS_TD",String.valueOf(module.isHasTD()));
                tdInput.setVisibility(View.GONE);
            }

            // Handle TP Visibility
            if (module.isHasTP()) {
                System.out.println(module.isHasTP());
                tpInput.setVisibility(View.VISIBLE);
            } else {
                tpInput.setVisibility(View.GONE);
            }

            // Exam is always present
            examInput.setVisibility(View.VISIBLE);

            // Clear previous TextWatchers to avoid memory leaks
            tdInput.removeTextChangedListener(tdTextWatcher);
            tpInput.removeTextChangedListener(tpTextWatcher);
            examInput.removeTextChangedListener(examTextWatcher);

            // Add TextWatchers to listen for input changes
            tdInput.addTextChangedListener(tdTextWatcher);
            tpInput.addTextChangedListener(tpTextWatcher);
            examInput.addTextChangedListener(examTextWatcher);

            // Calculate initial result
            calculateModuleResult(module);
        }

        // TextWatcher for TD input
        private final TextWatcher tdTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Module module = moduleList.get(getAdapterPosition());
                calculateModuleResult(module);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // TextWatcher for TP input
        private final TextWatcher tpTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Module module = moduleList.get(getAdapterPosition());
                calculateModuleResult(module);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // TextWatcher for Exam input
        private final TextWatcher examTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Module module = moduleList.get(getAdapterPosition());
                calculateModuleResult(module);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        private void calculateModuleResult(Module module) {
            float td = 0, tp = 0, exam = 0;

            // Parse TD value if available
            if (module.isHasTD() && !tdInput.getText().toString().isEmpty()) {
                td = Float.parseFloat(tdInput.getText().toString());
            }

            // Parse TP value if available
            if (module.isHasTP() && !tpInput.getText().toString().isEmpty()) {
                tp = Float.parseFloat(tpInput.getText().toString());
            }

            // Parse Exam value if available
            if (!examInput.getText().toString().isEmpty()) {
                exam = Float.parseFloat(examInput.getText().toString());
            }

            float moduleResult;
            if (module.isHasTD() && module.isHasTP()) {
                moduleResult = ((td + tp) / 2) * 0.4f + exam * 0.6f;
            } else if (module.isHasTD()) {
                moduleResult = td * 0.4f + exam * 0.6f;
            } else if (module.isHasTP()) {
                moduleResult = tp * 0.4f + exam * 0.6f;
            } else {
                moduleResult = exam;
            }

            // Update module's result and display it
            module.setCoursNote(moduleResult);
            resultText.setText("Result: " + String.format("%.2f", moduleResult));
        }
    }
}
