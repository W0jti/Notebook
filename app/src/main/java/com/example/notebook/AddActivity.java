package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    String title, content;

    EditText titleInput;
    EditText contentInput;

    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleInput = (EditText) findViewById(R.id.input_title);
        contentInput = (EditText) findViewById(R.id.input_content);


        Button saveNote = (Button) findViewById(R.id.save_button);
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                title = titleInput.getText().toString();
                content = contentInput.getText().toString();

                bundle.putString("title",title);
                bundle.putString("content", content);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Button discardNote = (Button) findViewById(R.id.discard_button);
        discardNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void defineButtons() {
        findViewById(R.id.save_button).setOnClickListener(buttonClickListener);
        findViewById(R.id.discard_button).setOnClickListener(buttonClickListener);
    }
    private void changeActivity() {
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private final View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();

            if (id == R.id.save_button) {
                Toast.makeText(AddActivity.this, "Zapisano notatke", Toast.LENGTH_SHORT).show();
                changeActivity();
            } else if (id == R.id.discard_button) {
                Toast.makeText(AddActivity.this, "Porzucono zmiany", Toast.LENGTH_SHORT).show();
                changeActivity();
            }
        }
    };


}