package com.example.androidsummerproject20;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidsummerproject20.models.Note;
import com.example.androidsummerproject20.notesDB.NotesDB;
import com.example.androidsummerproject20.notesDB.NotesDao;

public class EditNoteActivity extends AppCompatActivity {
    private EditText inputNote;
    private NotesDao dao;
    private Note currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        dao = NotesDB.getInstance(this).notesDao();
        inputNote = findViewById(R.id.input_note);
        dao = NotesDB.getInstance(this).notesDao();
        if (getIntent().getExtras() != null) {
            int id = getIntent().getExtras().getInt("Note", 0);
            currentNote = dao.getNoteById(id);
            inputNote.setText(currentNote.getNoteText());
        } else inputNote.setFocusable(true);
        findViewById(R.id.note_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveNote();
            }
        });
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
                Note note = new Note(text, date);
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
}