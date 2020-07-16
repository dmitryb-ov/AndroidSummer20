package com.example.androidsummerproject20.note;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummerproject20.R;
import com.example.androidsummerproject20.models.Note;

import java.util.ArrayList;


public class NotesAdapter extends RecyclerView.Adapter<NotesHolder> {

    private ArrayList<Note> notes;
    private Context context;
    private OnNoteClickListener itemClickListener;

    public NotesAdapter(ArrayList<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
    }

    @Override
    public NotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notes, parent, false);
        return new NotesHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesHolder holder, int position) {
        final Note note = getNote(position);
        if (note != null) {
            holder.noteText.setText(note.getNoteText());
            holder.noteDate.setText(note.getNoteDate());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onClick(note);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemClickListener.onLongClick(note);
                    return false;
                }
            });
        }


    }

    private Note getNote(int position) {
        return notes.get(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setItemClickListener(OnNoteClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
