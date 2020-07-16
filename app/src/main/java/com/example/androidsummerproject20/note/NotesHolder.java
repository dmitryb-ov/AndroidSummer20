package com.example.androidsummerproject20.note;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummerproject20.R;

class NotesHolder extends RecyclerView.ViewHolder {
    protected TextView noteText, noteDate;

    public NotesHolder(@NonNull View itemView) {
        super(itemView);
        noteText = itemView.findViewById(R.id.note_task);
        noteDate = itemView.findViewById(R.id.note_date);
    }
}

