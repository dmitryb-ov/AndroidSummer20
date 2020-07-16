package com.example.androidsummerproject20.note;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidsummerproject20.R;
import com.example.androidsummerproject20.data.NotesDB;
import com.example.androidsummerproject20.data.NotesDao;
import com.example.androidsummerproject20.models.Note;

public class EditNoteActivity extends AppCompatActivity {
    private EditText inputNote;
    private NotesDao dao;
    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.note_details_title);
        setContentView(R.layout.activity_edit_note);
        dao = NotesDB.getInstance(this).notesDao();
        inputNote = findViewById(R.id.input_note);
        dao = NotesDB.getInstance(this).notesDao();
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (getIntent().getExtras() != null) {
            int id = getIntent().getExtras().getInt("Note", 0);
            currentNote = dao.getNoteById(id);
            inputNote.setText(currentNote.getNoteText());
        } else {
            inputNote.setFocusable(true);
        }
    }

    private void onSaveNote() {
        String text = inputNote.getText().toString();
        if (!text.isEmpty()) {
            String date = NoteDate.getDate();
            if (currentNote != null) {
                currentNote.setNoteText(text);
                currentNote.setNoteDate(date);
                dao.updateNote(currentNote);
            } else {
                Note note = new Note(text, date, false);
                dao.insertNote(note);
            }
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //protected static void start(Activity activity, Note note) {
    //  Intent intent = new Intent(activity, EditNoteActivity.class);
    //intent.putExtra("Note", note.getNoteText());
    //intent.putExtra("Date", note.getNoteDate());
    //activity.startActivity(intent);
    //}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            onSaveNote();
        }
        return super.onOptionsItemSelected(item);
    }
}