package com.example.notebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class dbManager extends SQLiteOpenHelper {
    public dbManager(Context context) {
        super(context, "notebook.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE NOTES(" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "TITLE CHAR(100)," +
                        "CONTENT CHAR(1000));" +
                        "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        db.insertOrThrow("notes", null, values);
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] arg = {"" + id};
        db.delete("notes", "id=?", arg);
    }

    public void editNote(Note note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        String args[]={String.valueOf(note.getId())};
        db.update("notes", values,"id=?", args);
    }

    public List<Note> showAllNotes(){
        List<Note> notes = new LinkedList<Note>();
        String[] columns={"id","title","content"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =db.query("notes",columns,null,null,null,null,null);
        while(cursor.moveToNext()){
            Note note = new Note();
            note.setId(cursor.getLong(0));
            note.setTitle(cursor.getString(1));
            note.setContent(cursor.getString(2));
            notes.add(note);
        }
        return notes;
    }

    public Note showNote(int id){
        Note note=new Note();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns={"id","title","content"};
        String args[]={id+""};
        Cursor cursor=db.query("notes",columns," id=?",args,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
            note.setId(cursor.getLong(0));
            note.setTitle(cursor.getString(1));
            note.setContent(cursor.getString(2));
        }
        return note;
    }
}
