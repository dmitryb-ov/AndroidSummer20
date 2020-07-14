package com.example.androidsummerproject20.activityToDoList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidsummerproject20.data.App;
import com.example.androidsummerproject20.models.Task;

import java.util.List;

public class MainViewModel extends ViewModel {
    //хранятся в виде списка (списки дел)
    //синглтон
    private LiveData<List<Task>> noteLiveData = App.getInstance().getTaskDao().getAllLiveData();

    public LiveData<List<Task>> getNoteLiveData() {
        return noteLiveData;
    }
}
