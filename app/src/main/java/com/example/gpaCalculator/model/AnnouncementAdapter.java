package com.example.gpaCalculator.model;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gpaCalculator.model.entities.Announcement;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {
    private List<Announcement> announcements;
    private final OnLinkClickListener onLinkClickListener;

    public interface OnLinkClickListener {
        void onLinkClick(String url);
    }

    public AnnouncementAdapter(List<Announcement> announcements, OnLinkClickListener listener) {
        this.announcements = new ArrayList<>(announcements);
        this.onLinkClickListener = listener;
    }

    public void updateAnnouncements(List<Announcement> newAnnouncements) {
        this.announcements.clear();
        this.announcements.addAll(newAnnouncements);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_announcement, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        Announcement announcement = announcements.get(position);
        holder.titleTextView.setText(announcement.getTitle());
        holder.contentTextView.setText(announcement.getContent());
        holder.dateTextView.setText(announcement.getDate());

        if (announcement.getLink() != null && !announcement.getLink().isEmpty()) {
            holder.linkTextView.setVisibility(View.VISIBLE);
            holder.linkTextView.setText("Open Link");
            holder.linkTextView.setOnClickListener(v -> {
                if (onLinkClickListener != null) {
                    onLinkClickListener.onLinkClick(announcement.getLink());
                }
            });
        } else {
            holder.linkTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, contentTextView, dateTextView, linkTextView;

        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            linkTextView = itemView.findViewById(R.id.linkTextView);
        }
    }
}