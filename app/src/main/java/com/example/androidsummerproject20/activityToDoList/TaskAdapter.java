package com.example.androidsummerproject20.activityToDoList;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.androidsummerproject20.R;
import com.example.androidsummerproject20.activityToDoList.noteDetail.NoteDetailsActivity;
import com.example.androidsummerproject20.data.App;
import com.example.androidsummerproject20.models.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.NoteViewHolder> {

    //SortedList(работает на основе рефлексии)
    //предназначен для того, чтобы автоматически определять изменения внутри себя
    //и выдавать соответствующие команды, что в нём обновилось на RecycleView, который
    //в свою очередь будем понимать какие именно элементы изменились и как их теперь
    //отображать
    private SortedList<Task> sortedList;

    public TaskAdapter() {

        sortedList = new SortedList<>(Task.class, new SortedList.Callback<Task>() {
            //сортировка элементов отображения(заметок)
            //сначала отображается несделанные дела, потом сделанные
            //при этом все сделанные/не сделанные дела между собой
            //сортируются по дате создания/редактирования
            @Override
            public int compare(Task o1, Task o2) {
                if (!o2.active && o1.active) {
                    return 1;
                }
                if (o2.active && !o1.active) {
                    return -1;
                }
                return (int) (o2.time - o1.time);
            }

            //точным образом произведёт обновление отдельных элементов, не трогая остальные
            //и если нужно поменяет местами
            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            //если два элемента(заметки) полностью равны, то есть не только id равны, но
            //и их содержимое
            @Override
            public boolean areContentsTheSame(Task oldItem, Task newItem) {
                return oldItem.equals(newItem);
            }

            //если два элемента имеют одинаковый id, но при этом разное содержимое
            //тот же самый элемент(но после обновления содержимого заметки)
            //возвращаем сравнение по id
            @Override
            public boolean areItemsTheSame(Task item1, Task item2) {
                return item1.id == item2.id;
            }

            //делегирум в Adaptor
            //три следующих метода сообщают о добавлении, изменении и перемещении заметок
            //сообщают всё в Adaptor
            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    //создание нового ViewHolder
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_list, parent, false));
    }

    //привязка к конкретной заметки ViewHolder'a
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    //размер SortedList, сколько в нём элементов
    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    //метод обновления списка заметок, содержимого Adaptor
    //добавление, а именно замена новых заметок
    //передаём новый список(изменённый) заметок, который мы хотим, чтобы
    //отобразился в Adaptor
    public void setItems(List<Task> tasks) {
        //SortedList сравнит содержимое старого листа и нового, найдёт разницу
        //и применив свои функции добавления, изменения и перемещения заметок,
        //выдаст новый список со всеми нужными отображениями.
        sortedList.replaceAll(tasks);
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        //текст заметки
        TextView noteText;
        //сделано/не сделано
        CheckBox completed;
        //кнопка удалить(только нажатие - используется как view)
        View delete;

        //заметка, которая в текущий момент отображается
        Task task;

        //флаг для "зачёркнутости" заметки
        boolean silentUpdate;

        //предназначен, чтобы хранить ссылки на view, чтобы не доставить их каждый раз
        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);

            noteText = itemView.findViewById(R.id.note_task);
            completed = itemView.findViewById(R.id.completed);
//            delete = itemView.findViewById(R.id.delete);

            //обработчик для всей view, по нему будем вызывать редактирование заметки
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoteDetailsActivity.start((Activity) itemView.getContext(), task);
                }
            });

            //обработчик кнопки "удалить" заметку
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //удаляем заметку в базе данных
//                    App.getInstance().getTaskDao().delete(task);
//                }
//            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Delete")
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    App.getInstance().getTaskDao().delete(task);
                                }
                            }).create().show();
                    return true;
                }
            });


            //обработчик кнопки "завершено"
            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    //обновляем заметку, необходимо сохранить изменение в заметке
                    if (!silentUpdate) {
                        task.active = checked;
                        App.getInstance().getTaskDao().update(task);
                    }
                    //обновляем показатель зачёркнутости строки
                    //то есть зачёркнутость на незачёркнутость
                    //        незачёркнутость на зачёркнутость
                    updateStrokeOut();
                }
            });

        }

        //метод, который отображает значение полей заметки на наши view
        public void bind(Task task) {
            this.task = task;

            noteText.setText(task.text);
            //вызов метода отображения зачёркнутости
            updateStrokeOut();

            silentUpdate = true;
            //задаём значение
            completed.setChecked(task.active);
            //наш флаг становится false
            silentUpdate = false;
        }

        //функция для отображения зачёркнутости заметки, когда она выполнена
        //то есть при нажатии на кнопку "выполнено" отображается зачёркивание
        private void updateStrokeOut() {
            //если выполнена
            if (task.active) {
                //операция для установки нужных флагов с двоичной операцией(отображение такое)
                noteText.setPaintFlags(noteText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                //операция для отрисовывания без зачеркивания(при "и" четвертая позиция становится равна нулю)
                noteText.setPaintFlags(noteText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}