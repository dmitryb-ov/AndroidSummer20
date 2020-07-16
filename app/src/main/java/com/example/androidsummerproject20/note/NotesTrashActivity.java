package com.example.androidsummerproject20.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;

public class NotesTrashActivity extends AppCompatActivity implements OnNoteClickListener {
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NotesAdapter adapter;
    private NotesDao dao;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_notes_trash);
        setTitle(R.string.trash_title);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dao = NotesDB.getInstance(this).notesDao();
    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        this.notes.addAll(dao.getNotes(true));
        adapter = new NotesAdapter(notes, this);
        this.adapter.setItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
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
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Notes", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.todo:
                startActivity(new Intent(this, ToDoListActivity.class));
                Toast.makeText(this, "ToDoList", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.trash:
                Toast.makeText(this, "Trash", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onClick(final Note note) {
        new AlertDialog.Builder(this).setTitle("Trashed note").
                setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dao.deleteNote(note);
                        Toast.makeText(NotesTrashActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        loadNotes();
                    }
                })
                .setNegativeButton("Restore", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        note.setInTrash(false);
                        dao.updateNote(note);
                        Toast.makeText(NotesTrashActivity.this, "Restored", Toast.LENGTH_SHORT).show();

                        loadNotes();
                    }
                }).create().show();
    }

    @Override
    public void onLongClick(Note note) {

    }
}
