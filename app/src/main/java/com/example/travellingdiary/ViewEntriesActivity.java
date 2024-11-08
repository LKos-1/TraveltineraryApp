package com.example.travellingdiary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewEntriesActivity extends AppCompatActivity {
    private static final String TAG = "ViewEntriesActivity";
    private FirebaseFirestore db;
    private TextView noEntriesText;
    private RecyclerView recyclerView;
    private DiaryEntryAdapter adapter;
    private Button addEntryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entries);

        noEntriesText = findViewById(R.id.noEntriesText);
        recyclerView = findViewById(R.id.recyclerView);
        addEntryButton = findViewById(R.id.addEntryButton);

        db = FirebaseFirestore.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DiaryEntryAdapter();
        recyclerView.setAdapter(adapter);

        // Retrieve and display diary entries
        getDiaryEntries();

        //Click listener for add entry button
        addEntryButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewEntriesActivity.this, AddEntryActivity.class);
            startActivity(intent);
        });
    }

    private void getDiaryEntries() {
        db.collection("diary_entries")
                .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Error getting documents.", e);
                            return;
                        }

                        List<DiaryEntry> entries = new ArrayList<>();
                        assert queryDocumentSnapshots != null;
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            DiaryEntry entry = doc.toObject(DiaryEntry.class);
                            entry.setId(doc.getId());
                            entries.add(entry);
                        }

                        if (entries.isEmpty()) {
                            noEntriesText.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            noEntriesText.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            adapter.setEntries(entries);
                        }
                    }
                });
    }

}




