package com.example.androidsummerproject20.note;

import com.example.androidsummerproject20.models.Note;

public interface OnItemClickListener {

    void onClick(Note note);

    void onLongClick(Note note);
}
