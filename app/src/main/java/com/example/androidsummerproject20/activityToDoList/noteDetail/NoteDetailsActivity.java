package com.example.androidsummerproject20.activityToDoList.noteDetail;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.androidsummerproject20.R;
import com.example.androidsummerproject20.activityToDoList.noteDetail.notofocation.NotificationTimePicker;
import com.example.androidsummerproject20.data.App;
import com.example.androidsummerproject20.models.Task;

import java.util.Calendar;
import java.util.Date;

public class NoteDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE = "NoteDetailsActivity.EXTRA_NOTE";

    //заметка
    private Task task;

    //текстовое поле для ввода
    private EditText editText;

    private CheckBox checkBox;

    private Calendar notificationTime = Calendar.getInstance();

    private boolean notificationIsChecked = false;

    private Button button;

    //метод для запуска активити
    public static void start(Activity caller, Task task) {
        Intent intent = new Intent(caller, NoteDetailsActivity.class);
        //если заметка существует
        if (task != null) {
            intent.putExtra(EXTRA_NOTE, task);
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

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //добавляем кнопку назад
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Текст заголовка, который содержится в values/strings
        setTitle(getString(R.string.task_details_title));

        editText = findViewById(R.id.text);

        button = findViewById(R.id.button);

        //getIntent возвращает intent созданный при запуске активити
        //если заметка есть,то есть есть intent
        if (getIntent().hasExtra(EXTRA_NOTE)) {
            //за счёт интерфейса Parcelable достаём заметку
            task = getIntent().getParcelableExtra(EXTRA_NOTE);
            //изменяем(задаём) текст заметки
            editText.setText(task.text);
        } else {
            //если заметки нет, то создаём новую заметку
            task = new Task();
        }
    }

    //для кнопки сохранения в меню
    //создание меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu_toolbar, menu);
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
                    task.text = editText.getText().toString();
                    //дело "не завершено"
                    task.active = false;
                    //время создания
                    task.time = System.currentTimeMillis();
                    //если какая-то заметка передавалась в активити, то обновляем её
                    if (getIntent().hasExtra(EXTRA_NOTE)) {
                        App.getInstance().getTaskDao().update(task);
                    }
                    //если заметка новая, то добавляем её в список
                    else {
                        App.getInstance().getTaskDao().insert(task);
                    }
                    //происходит обновление базы данных, и автоматически livedata всё изменит,
                    //поэтому обратно передавать ничего не нужно
                    //все изменения покажутся на экране
                    if (notificationIsChecked) {

                        long time = notificationTime.getTimeInMillis();

                        Toast.makeText(getApplicationContext(),
                                "Напоминане установлено на: \n" + new Date(time).toString(),
                                Toast.LENGTH_SHORT).show();

                        scheduleNotification(getNotification(
                                task.text.substring(0, Math.min(task.text.length(), 40))), time);
                    }
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTime(View v) {
        NotificationTimePicker notificationTimePicker = new NotificationTimePicker(this, notificationTime);
        notificationTimePicker.showDialogWindow();

        button.setTextColor(Color.parseColor("#007000"));
        notificationIsChecked = true;
    }

    public void deleteTime(View view) {
        button.setTextColor(Color.parseColor("#000000"));
        notificationIsChecked = false;
    }

    private void scheduleNotification(Notification notification, long delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class)
                .putExtra(NotificationPublisher.NOTIFICATION_ID, 101)
                .putExtra(NotificationPublisher.NOTIFICATION, notification);

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(this, 0,
                        notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, delay, pendingIntent);
    }

    private Notification getNotification(String content) {
        return new Notification.Builder(this)
                .setContentTitle("Notification")
                .setContentText(content)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .build();
    }
}