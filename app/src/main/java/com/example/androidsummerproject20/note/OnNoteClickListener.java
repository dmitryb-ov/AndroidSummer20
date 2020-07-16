package com.example.androidsummerproject20.note;

import com.example.androidsummerproject20.models.Note;

public interface OnNoteClickListener {

    void onClick(Note note);

    void onLongClick(Note note);
}
