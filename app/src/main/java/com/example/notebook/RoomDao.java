package com.example.notebook;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomDao {
    @Insert
    void insertAll(Note... notes);

    @Delete
    void delete(Note note);
    @Query("SELECT * FROM room_note")
    LiveData<List<Note>> getAll();
    @Query("DELETE FROM room_note")
    void deleteAll();

    @Update
    void update(Note note);
}
