package com.example.androidsummerproject20.notesDB;

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
    public void insertNote(Note note);

    @Delete
    public void deleteNote(Note note);

    @Update
    public void updateNote(Note note);

    @Query("SELECT * from notes")
    List<Note> getNotes();

    @Query("SELECT * from notes WHERE id LIKE :noteId")
    Note getNoteById(int noteId);

    @Query("DELETE FROM notes WHERE id LIKE :noteId")
    void deleteNoteById(int noteId);
}
