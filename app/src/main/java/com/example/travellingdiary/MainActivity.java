package com.example.travellingdiary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travellingdiary.databinding.ActivityMainBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();

        Button viewEntriesButton = findViewById(R.id.viewEntriesButton);
        viewEntriesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewEntriesActivity.class);
            startActivity(intent);
        });

        //addDummyEntry();
    }

    /*private void addDummyEntry() {
        // Create a new diary entry with dummy data
        DiaryEntry entry = new DiaryEntry(null, "My testing Entry", "This is a test entry", System.currentTimeMillis());

        // Add the entry to Firestore
        db.collection("diary_entries")
                .add(entry)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }*/
}
