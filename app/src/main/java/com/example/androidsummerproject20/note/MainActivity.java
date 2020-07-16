package com.example.androidsummerproject20.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummerproject20.R;
import com.example.androidsummerproject20.activityToDoList.ToDoListActivity;
import com.example.androidsummerproject20.data.NotesDB;
import com.example.androidsummerproject20.data.NotesDao;
import com.example.androidsummerproject20.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnNoteClickListener {
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NotesAdapter adapter;
    private NotesDao dao;
    private Toolbar toolbar;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.notes_title);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
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

    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        this.notes.addAll(dao.getNotes(false));
        adapter = new NotesAdapter(notes, this);
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
                setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Remove in trash", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        note.setInTrash(true);
                        dao.updateNote(note);
                        loadNotes();
                        Toast.makeText(MainActivity.this,
                                "Removed in trash", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dao.deleteNote(note);
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        loadNotes();
                    }
                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.note:
                Toast.makeText(this, "Notes", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.todo:
                startActivity(new Intent(this, ToDoListActivity.class));
                Toast.makeText(this, "ToDoList", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.trash:
                startActivity(new Intent(this, NotesTrashActivity.class));
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
