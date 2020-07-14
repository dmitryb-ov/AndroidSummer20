package com.example.androidsummerproject20.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.androidsummerproject20.models.Task;

//аннотация для базы данных с возможностью обновления базы данных без её удаления
@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract TaskDao noteDao();
}

