package com.example.androidsummerproject20.activityToDoList.noteDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidsummerproject20.R;
import com.example.androidsummerproject20.data.App;
import com.example.androidsummerproject20.notes.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE = "NoteDetailsActivity.EXTRA_NOTE";

    //заметка
    private Note note;

    //текстовое поле для ввода
    private EditText editText;

    //метод для запуска активити
    public static void start(Activity caller, Note note) {
        Intent intent = new Intent(caller, NoteDetailsActivity.class);
        //если заметка существует
        if (note != null) {
            intent.putExtra(EXTRA_NOTE, note);
        }
        //запускаем активити через intent
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //приложение читает файл разметки и создаёт файл по шаблону, данному в скобках
        //далее с этим активити можно уже работать
        setContentView(R.layout.activity_note_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //добавляем кнопку назад
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Текст заголовка, который содержится в values/strings
        setTitle(getString(R.string.note_details_title));

        editText = findViewById(R.id.text);

        //getIntent возвращает intent созданный при запуске активити
        //если заметка есть,то есть есть intent
        if (getIntent().hasExtra(EXTRA_NOTE)) {
            //за счёт интерфейса Parcelable достаём заметку
            note = getIntent().getParcelableExtra(EXTRA_NOTE);
            //изменяем(задаём) текст заметки
            editText.setText(note.text);
        } else {
            //если заметки нет, то создаём новую заметку
            note = new Note();
        }
    }

    //для кнопки сохранения в меню
    //создание меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //обработка для событий при нажатии на кнопку сохранения
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //если пользователь нажал на кнопку "назад", то просто завершить работу
            case android.R.id.home:
                finish();
                break;
            //если пользователь нажал на кнопку "сохранения"
            case R.id.action_save:
                //если пользователь что-то ввёл в заметку
                if (editText.getText().length() > 0) {
                    //сохраняем текстом то, что ввёл пользователь
                    note.text = editText.getText().toString();
                    //дело "не завершено"
                    note.active = false;
                    //время создания
                    note.time = System.currentTimeMillis();
                    //если какая-то заметка передавалась в активити, то обновляем её
                    if (getIntent().hasExtra(EXTRA_NOTE)) {
                        App.getInstance().getNoteDao().update(note);
                    }
                    //если заметка новая, то добавляем её в список
                    else {
                        App.getInstance().getNoteDao().insert(note);
                    }
                    //происходит обновление базы данных, и автоматически livedata всё изменит,
                    //поэтому обратно передавать ничего не нужно
                    //все изменения покажутся на экране
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}