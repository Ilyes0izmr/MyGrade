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

        // Optionally pre-fill input fields if data exists
        if (module.getExamNote()> 0) {
            holder.moduleExamInput.setText(String.valueOf(module.getExamNote());
        }
        if (module.getTdNote()>0){
            holder.moduleTDInput.setText(String.valueOf(module.getTdNote()));
        }
        if (module.getTpNote() > 0) {
            holder.moduleTPInput.setText(String.valueOf(module.getTpNote()));
        }
    }

    @Override
    public int getItemCount() {
        return modules.size();
    }

    public static class ModuleViewHolder extends RecyclerView.ViewHolder {
        TextView moduleName;
        EditText moduleExamInput;
        EditText moduleTDInput;
        EditText moduleTPInput;

        public ModuleViewHolder(View itemView) {
            super(itemView);
            moduleName = itemView.findViewById(com.example.myapplication.R.id.moduleName);
            moduleExamInput = itemView.findViewById(R.id.moduleExamInput);
            moduleTDInput = itemView.findViewById(R.id.moduleTDInput);
            moduleTPInput = itemView.findViewById(R.id.moduleTPInput);
        }
    }
}