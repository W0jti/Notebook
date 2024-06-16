package com.example.notebook;

import android.content.Context;
import android.view.View;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class}, version = 1)
public abstract class RoomNoteDatabase extends RoomDatabase {
    public abstract RoomDao roomDao();

    private static volatile RoomNoteDatabase INSTANCE;

    public static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RoomNoteDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (RoomNoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomNoteDatabase.class, "notes")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
