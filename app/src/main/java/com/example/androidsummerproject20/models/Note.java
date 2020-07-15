package com.example.androidsummerproject20.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "text")
    private String noteText;

    @ColumnInfo(name = "date")
    private String noteDate;

    @ColumnInfo(name = "inTrash")
    private boolean inTrash;

    public Note() {
    }

    public Note(String noteText, String noteDate, boolean inTrash) {
        this.noteText = noteText;
        this.noteDate = noteDate;
        this.inTrash = inTrash;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInTrash() {
        return inTrash;
    }

    public void setInTrash(boolean inTrash) {
        this.inTrash = inTrash;
    }
}
