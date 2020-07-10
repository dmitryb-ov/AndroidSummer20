package com.example.androidsummerproject20.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androidsummerproject20.notes.Note;

//аннотация для базы данных с возможностью обновления базы данных без её удаления
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();
}

