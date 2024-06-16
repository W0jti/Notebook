package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private EditText titleInput;
    private EditText contentInput;
    private long noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        titleInput = findViewById(R.id.input_title);
        contentInput = findViewById(R.id.input_content);

        Intent intent = getIntent();
        noteId = intent.getLongExtra("id", -1);
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");

        if (noteId == -1) {
            Log.e("EditActivity", "Invalid note ID");
            finish();
            return;
        }

        titleInput.setText(title);
        contentInput.setText(content);

        Button saveNote = findViewById(R.id.save_button);
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNote();
            }
        });

        Button discardNote = findViewById(R.id.discard_button);
        discardNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button deleteNote = findViewById(R.id.delete_button);
        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote();
            }
        });


    }

    private void updateNote() {
        String title = titleInput.getText().toString();
        String content = contentInput.getText().toString();

        RoomNoteDatabase db = RoomNoteDatabase.getDatabase(this);
        RoomDao roomDao = db.roomDao();
        RoomNoteDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Note note = new Note();
                note.setId(noteId);
                note.setTitle(title);
                note.setContent(content);

                roomDao.update(note);
                runOnUiThread(() -> {
                    Toast.makeText(EditActivity.this, "Zapisano notatkę", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(intent);
                });
            } catch (Exception e) {
                Log.e("EditActivity", "Failed to update note", e);
                runOnUiThread(() -> Toast.makeText(EditActivity.this, "Failed to update note", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void deleteNote() {
        RoomNoteDatabase db = RoomNoteDatabase.getDatabase(this);
        RoomDao roomDao = db.roomDao();
        RoomNoteDatabase.databaseWriteExecutor.execute(() -> {
            try {
                Note note = new Note();
                note.setId(noteId);

                roomDao.delete(note);
                runOnUiThread(() -> {
                    Toast.makeText(EditActivity.this, "Usunięto notatkę", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditActivity.this, MainActivity.class);
                    startActivity(intent);
                });
            } catch (Exception e) {
                Log.e("EditActivity", "Failed to delete note", e);
                runOnUiThread(() -> Toast.makeText(EditActivity.this, "Failed to delete note", Toast.LENGTH_SHORT).show());
            }
        });
    }
}