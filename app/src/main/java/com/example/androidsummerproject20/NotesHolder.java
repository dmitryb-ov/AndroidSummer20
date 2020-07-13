package com.example.androidsummerproject20;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class NotesHolder extends RecyclerView.ViewHolder {
    protected TextView noteText, noteDate;

    public NotesHolder(@NonNull View itemView) {
        super(itemView);
        noteText = itemView.findViewById(R.id.note_text);
        noteDate = itemView.findViewById(R.id.note_time);
    }
}

