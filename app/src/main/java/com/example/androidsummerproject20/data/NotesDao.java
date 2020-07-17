package com.example.androidsummerproject20.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidsummerproject20.models.Note;

import java.util.List;

@Dao
public interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("SELECT * from notes WHERE inTrash LIKE :trash")
    List<Note> getNotes(boolean trash);

    @Query("SELECT * from notes WHERE id LIKE :noteId")
    Note getNoteById(int noteId);

    @Query("DELETE FROM notes WHERE id LIKE :noteId")
    void deleteNoteById(int noteId);

    @Query("SELECT COUNT(id) FROM notes")
    int getCountNotes();
}
