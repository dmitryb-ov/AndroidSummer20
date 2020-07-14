package com.example.androidsummerproject20.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

//интерфейс Parcelabe для того, чтобы передавать Note между активити(serializable)
@Entity
public class Task implements Parcelable {

    @PrimaryKey(autoGenerate = true)  //авто-генерация уникального ключа
    //при добавлении нового элемента присвоется следующее по порядку уник значение
    public int id; // уникальный Id

    @ColumnInfo(name = "text")
    //пишем в какой колонке базы данных будет сохраняться
    public String text;  //текст

    @ColumnInfo(name = "time")
    public long time;  //время

    @ColumnInfo(name = "active")
    public boolean active; //сделано или не сделано

    public Task() {
    }

    protected Task(Parcel in) {
        id = in.readInt();
        text = in.readString();
        time = in.readLong();
        active = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                time == task.time &&
                active == task.active &&
                Objects.equals(text, task.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, time, active);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(text);
        dest.writeLong(time);
        dest.writeByte((byte) (active ? 1 : 0));
    }
}
