package com.example.androidsummerproject20.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidsummerproject20.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    //возвращает список
    @Query("SELECT * FROM Task")
    List<Task> getAll();

    //каждый раз когда заметки будут меняться, ливдата будет обновлять данные
    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getAllLiveData();

    //":noteIds" - означает автоматическую подстановку с таким именем
//    @Query("SELECT * FROM Note WHERE id IN (:noteIds)")
//    List<Note> loadAllByIds(int[] noteIds);

    //выборка по id
    @Query("SELECT * FROM Task WHERE id = :id LIMIT 1")
    Task findById(int id);

    //вставка
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Task task);

    //обновление
    @Update
    void update(Task task);

    //удаление
    @Delete
    void delete(Task task);
}
