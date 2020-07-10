package com.example.androidsummerproject20.notes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

//интерфейс Parcelabe для того, чтобы передавать Note между активити(serializable)
@Entity
public class Note implements Parcelable {

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

    public Note() {
    }

    protected Note(Parcel in) {
        id = in.readInt();
        text = in.readString();
        time = in.readLong();
        active = in.readByte() != 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id &&
                time == note.time &&
                active == note.active &&
                Objects.equals(text, note.text);
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
