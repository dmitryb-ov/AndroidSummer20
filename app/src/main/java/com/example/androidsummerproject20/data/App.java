package com.example.androidsummerproject20.data;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {
    private AppDataBase database; //база данных
    private NoteDao noteDao; //

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    //создаётся до отображения приложение, то есть выполняется с наивысшем приоритетом
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        //создается база данных приложения, то есть конкретная реализация абстрактного класса
        database = Room.databaseBuilder(getApplicationContext(),
                AppDataBase.class, "app-db-name")
                .allowMainThreadQueries() //запросы идут с основного потока, а не background
                .build();

        //data access object - то есть с помощью этого объекта мы получаем доступ к данным
        //нашего приложения
        noteDao = database.noteDao();
    }

    public AppDataBase getDatabase() {
        return database;
    }

    public void setDatabase(AppDataBase database) {
        this.database = database;
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }
}
