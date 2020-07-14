package com.example.androidsummerproject20.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidsummerproject20.models.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NotesDB extends RoomDatabase {
    public abstract NotesDao notesDao();

    private static String DATABASE_NAME = "note_database";
    private static NotesDB instance;

    public static NotesDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, NotesDB.class, DATABASE_NAME).
                    allowMainThreadQueries().build();
        }
        return instance;
    }
}
