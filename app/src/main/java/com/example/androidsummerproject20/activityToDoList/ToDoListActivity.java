package com.example.androidsummerproject20.activityToDoList;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummerproject20.R;
import com.example.androidsummerproject20.activityToDoList.noteDetail.NoteDetailsActivity;
import com.example.androidsummerproject20.notes.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

//класс запуска основго активити для "списка дел"
public class ToDoListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        recyclerView = findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //запуск активи через метод, который мы уже создали заранее(для запуска активити)
                NoteDetailsActivity.start(ToDoListActivity.this, null);
            }
        });

        //ViewModelProviders - позволяет получить экземпляр класса ViewModel
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        //отписываемся, когда активити умирает, то есть отключаемся
        mainViewModel.getNoteLiveData().observe(this, new Observer<List<Note>>() {
            @Override
            //будет вызван метод, когда поменяется наша таблица с заметками
            public void onChanged(List<Note> notes) {
                adapter.setItems(notes);
            }
        });
    }
}
