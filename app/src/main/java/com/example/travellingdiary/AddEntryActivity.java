package com.example.travellingdiary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class AddEntryActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button saveButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        saveButton = findViewById(R.id.saveButton);
        db = FirebaseFirestore.getInstance();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEntry();
            }
        });
    }

    private void saveEntry() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        long date = System.currentTimeMillis();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Title and description cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        DiaryEntry entry = new DiaryEntry(null, title, description, date);
        db.collection("diary_entries")
                .add(entry)
                .addOnSuccessListener(documentReference -> {
                    String id = documentReference.getId();
                    entry.setId(id);
                    Toast.makeText(this, "Entry added successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error adding entry", Toast.LENGTH_SHORT).show());
    }
}



