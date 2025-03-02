package com.example.gpaCalculator.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpaCalculator.model.Module;
import com.example.myapplication.R;


import java.util.List;

public class ModulAdapter extends RecyclerView.Adapter<ModulAdapter.ModuleViewHolder> {

    private List<Module> modules;

    public ModulAdapter(List<Module> modules) {
        this.modules = modules;
    }
    public void updateModules(List<Module> newModules) {
        this.modules = newModules;
        notifyDataSetChanged(); // Notify the RecyclerView about the data change
    }

    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_module, parent, false);
        return new ModuleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        Module module = modules.get(position);

        // Bind module name
        holder.moduleName.setText(module.getNom_module());

        // Handle visibility of input fields based on module flags
        holder.moduleCoursInput.setVisibility(module.isHasCours() ? View.VISIBLE : View.GONE);
        holder.moduleTDInput.setVisibility(module.isHasTD() ? View.VISIBLE : View.GONE);
        holder.moduleTPInput.setVisibility(module.isHasTP() ? View.VISIBLE : View.GONE);

        // Optionally pre-fill input fields if data exists
        if (module.getCoursNote() > 0) {
            holder.moduleCoursInput.setText(String.valueOf(module.getCoursNote()));
        } else {
            holder.moduleCoursInput.setText(""); // Clear the field if no value exists
        }

        if (module.getTdNote() > 0) {
            holder.moduleTDInput.setText(String.valueOf(module.getTdNote()));
        } else {
            holder.moduleTDInput.setText("");
        }

        if (module.getTpNote() > 0) {
            holder.moduleTPInput.setText(String.valueOf(module.getTpNote()));
        } else {
            holder.moduleTPInput.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    public static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView moduleName;
        EditText moduleCoursInput; // Input field for Cours
        EditText moduleTDInput;   // Input field for TD
        EditText moduleTPInput;   // Input field for TP

        public ModuleViewHolder(View itemView) {
            super(itemView);
            moduleName = itemView.findViewById(com.example.myapplication.R.id.moduleName);
            moduleCoursInput = itemView.findViewById(R.id.moduleCoursInput);
            moduleTDInput = itemView.findViewById(R.id.moduleTDInput);
            moduleTPInput = itemView.findViewById(R.id.moduleTPInput);
        }
    }
}