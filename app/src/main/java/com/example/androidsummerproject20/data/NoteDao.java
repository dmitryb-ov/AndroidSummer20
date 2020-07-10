package com.example.androidsummerproject20.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidsummerproject20.notes.Note;

import java.util.List;

@Dao
public interface NoteDao {

    //возвращает список
    @Query("SELECT * FROM Note")
    List<Note> getAll();

    //каждый раз когда заметки будут меняться, ливдата будет обновлять данные
    @Query("SELECT * FROM Note")
    LiveData<List<Note>> getAllLiveData();

    //":noteIds" - означает автоматическую подстановку с таким именем
//    @Query("SELECT * FROM Note WHERE id IN (:noteIds)")
//    List<Note> loadAllByIds(int[] noteIds);

    //выборка по id
    @Query("SELECT * FROM Note WHERE id = :id LIMIT 1")
    Note findById(int id);

    //вставка
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    //обновление
    @Update
    void update(Note note);

    //удаление
    @Delete
    void delete(Note note);
}
