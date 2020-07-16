package com.example.androidsummerproject20.activityToDoList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummerproject20.R;
import com.example.androidsummerproject20.activityToDoList.noteDetail.NoteDetailsActivity;
import com.example.androidsummerproject20.models.Task;
import com.example.androidsummerproject20.note.MainActivity;
import com.example.androidsummerproject20.note.NotesTrashActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

//класс запуска основго активити для "списка дел"
public class ToDoListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        setTitle(R.string.tasks_title);
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final TaskAdapter taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);

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
        mainViewModel.getNoteLiveData().observe(this, new Observer<List<Task>>() {
            @Override
            //будет вызван метод, когда поменяется наша таблица с заметками
            public void onChanged(List<Task> tasks) {
                taskAdapter.setItems(tasks);
            }
        });
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
                Toast.makeText(this, "ToDoList", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.trash:
                startActivity(new Intent(this, NotesTrashActivity.class));
                Toast.makeText(this, "Trash", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
