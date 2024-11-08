package com.example.travellingdiary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiaryEntryAdapter extends RecyclerView.Adapter<DiaryEntryAdapter.DiaryEntryViewHolder> {
    private List<DiaryEntry> entries = new ArrayList<>();

    public void setEntries(List<DiaryEntry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiaryEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_entry_item, parent, false);
        return new DiaryEntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryEntryViewHolder holder, int position) {
        DiaryEntry entry = entries.get(position);
        holder.titleTextView.setText(entry.getTitle());
        holder.descriptionTextView.setText(entry.getDescription());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = sdf.format(new Date(entry.getDate()));
        holder.dateTextView.setText(formattedDate);

        holder.deleteButton.setOnClickListener(v -> {
            String id = entry.getId();
            FirebaseFirestore.getInstance().collection("diary_entries").document(id)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        entries.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, entries.size());
                    });
        });
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    static class DiaryEntryViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dateTextView;
        TextView descriptionTextView;
        Button deleteButton;

        public DiaryEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            dateTextView = itemView.findViewById(R.id.date);
            descriptionTextView = itemView.findViewById(R.id.description);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}



