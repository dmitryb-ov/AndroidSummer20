package com.example.androidsummerproject20.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummerproject20.R;
import com.example.androidsummerproject20.data.NotesDB;
import com.example.androidsummerproject20.data.NotesDao;
import com.example.androidsummerproject20.models.Note;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NoteDate.NotesAdapter adapter;
    private NotesDao dao;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewNote();
            }
        });
        dao = NotesDB.getInstance(this).notesDao();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.note:
                        break;
                    case R.id.todo:

                        break;
                    case R.id.trash:
                        Toast.makeText(MainActivity.this, "Trash", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        this.notes.addAll(dao.getNotes());
        adapter = new NoteDate.NotesAdapter(notes, this);
        this.adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void addNewNote() {
        startActivity(new Intent(this, EditNoteActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    @Override
    public void onClick(Note note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra("Note", note.getId());
        startActivity(intent);
    }

    @Override
    public void onLongClick(final Note note) {
        new AlertDialog.Builder(this).
                setTitle("Delete").
                setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dao.deleteNote(note);
                        loadNotes();
                    }
                }).create().show();
    }
}
