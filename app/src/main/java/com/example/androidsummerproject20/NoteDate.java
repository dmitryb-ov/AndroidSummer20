package com.example.androidsummerproject20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummerproject20.models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NoteDate {

    public static String getDate() {
        DateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy 'at' hh:mm aaa", Locale.US);
        return format.format(new Date());
    }

    public static class NotesAdapter extends RecyclerView.Adapter<NotesHolder> {

        private ArrayList<Note> notes;
        private Context context;
        private OnItemClickListener itemClickListener;

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

        public void setItemClickListener(OnItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
