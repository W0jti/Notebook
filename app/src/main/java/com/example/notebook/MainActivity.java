package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);

        Bundle bundle = getIntent().getExtras();


        RoomNoteDatabase db = RoomNoteDatabase.getDatabase(this);
        RoomDao roomDao = db.roomDao();
        RoomNoteDatabase.databaseWriteExecutor.execute(() -> {
//            roomDao.deleteAll();
//
//            roomDao.insertAll(new Note("Zakupy", "jajka, chleb, masło, mleko, pomarańcze, orzechy..."),
//                    new Note("Ogród", "skosić trawę, podlać warzywa, zasadzić jabłoń"));

            if (bundle !=null){
                roomDao.insertAll(new Note(bundle.getString("title", "Tytuł"),bundle.getString("content", "")));
            }
        });

        LiveData<List<Note>> notes = roomDao.getAll();
        notes.observe(this, words -> {
            words.forEach(
                    w -> {
                        Log.d("DB", w.toString());
                    }
            );
            ArrayAdapter<Note> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);
            list.setAdapter(adapter);
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note note = (Note) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("id", note.getId());
                intent.putExtra("title", note.getTitle());
                intent.putExtra("content", note.getContent());
                startActivity(intent);
            }
        });

        FloatingActionButton addNote = findViewById(R.id.plus_button);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
}