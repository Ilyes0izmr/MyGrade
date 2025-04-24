package com.example.gpaCalculator.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gpaCalculator.model.entities.GroupSubject;
import com.example.myapplication.R;

import java.util.List;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {

    private final List<GroupSubject> groupSubjects;

    public GroupListAdapter(List<GroupSubject> groupSubjects) {
        this.groupSubjects = groupSubjects;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_subject, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupSubject groupSubject = groupSubjects.get(position);
        holder.textViewGroup.setText(groupSubject.getGroupName());
        holder.textViewSubject.setText(groupSubject.getSubjectName());
    }

    @Override
    public int getItemCount() {
        return groupSubjects.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView textViewGroup;
        TextView textViewSubject;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewGroup = itemView.findViewById(R.id.textViewGroup);
            textViewSubject = itemView.findViewById(R.id.textViewSubject);
        }
    }
}