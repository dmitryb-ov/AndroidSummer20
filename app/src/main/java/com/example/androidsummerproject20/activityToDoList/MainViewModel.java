package com.example.androidsummerproject20.activityToDoList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidsummerproject20.data.App;
import com.example.androidsummerproject20.notes.Note;

import java.util.List;

public class MainViewModel extends ViewModel {
    //хранятся в виде списка (списки дел)
    //синглтон
    private LiveData<List<Note>> noteLiveData = App.getInstance().getNoteDao().getAllLiveData();

    public LiveData<List<Note>> getNoteLiveData() {
        return noteLiveData;
    }
}
