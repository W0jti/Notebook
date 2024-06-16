package com.example.notebook;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "room_note")
public class Note {

    @PrimaryKey
    Long id;

    String title;

    String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Note() {
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return title + '\n' + content + '\n';
    }
}
