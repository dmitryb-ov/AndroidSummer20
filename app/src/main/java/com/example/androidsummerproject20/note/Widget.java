package com.example.androidsummerproject20.note;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.example.androidsummerproject20.R;
import com.example.androidsummerproject20.data.NotesDB;
import com.example.androidsummerproject20.data.NotesDao;

import java.util.Arrays;
import java.util.Random;

public class Widget extends AppWidgetProvider {

    final static String LOG_TAG = "myLogs";
    private NotesDao dao;
    private TextView textView;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(LOG_TAG, "onEnabled");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d(LOG_TAG, "onUpdate " + Arrays.toString(appWidgetIds));
        dao = NotesDB.getInstance(context).notesDao();
        Random random = new Random();
        int notesCount = dao.getCountNotes();
        if (notesCount != 0) {
            int randVal = getRandVal(dao);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            remoteViews.setTextViewText(R.id.tv, dao.getNoteById(randVal).getNoteText());
            for (int appWidgetId : appWidgetIds) {
                appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
            }
        }
        SharedPreferences sp = context.getSharedPreferences(
                ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE);
        for (int id : appWidgetIds) {
            updateWidget(context, appWidgetManager, sp, id);
        }
    }

    private int getRandVal(NotesDao dao) {
        Random random = new Random();
        int randVal = random.nextInt(dao.getCountNotes());
        if (randVal != 0) {
            return randVal;
        } else {
            return getRandVal(dao);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));

        // Удаляем Preferences
        Editor editor = context.getSharedPreferences(
                ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE).edit();
        for (int widgetID : appWidgetIds) {
            editor.remove(ConfigActivity.WIDGET_TEXT + widgetID);
            editor.remove(ConfigActivity.WIDGET_COLOR + widgetID);
        }
        editor.commit();
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(LOG_TAG, "onDisabled");
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager,
                             SharedPreferences sp, int widgetID) {
        Log.d(LOG_TAG, "updateWidget " + widgetID);

        // Читаем параметры Preferences
        String widgetText = sp.getString(ConfigActivity.WIDGET_TEXT + widgetID, null);
        if (widgetText == null) return;
        int widgetColor = sp.getInt(ConfigActivity.WIDGET_COLOR + widgetID, 0);

        // Настраиваем внешний вид виджета
        RemoteViews widgetView = new RemoteViews(context.getPackageName(),
                R.layout.widget);
        widgetView.setTextViewText(R.id.tv, widgetText);
        widgetView.setInt(R.id.tv, "setBackgroundColor", widgetColor);

        // Обновляем виджет
        appWidgetManager.updateAppWidget(widgetID, widgetView);
    }
}
