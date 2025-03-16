package com.example.atlantisgroup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.atlantisgroup.R;

import java.util.List;

import java.util.List;

public class NotificationsListViewAdapter extends BaseAdapter {

    private List<String> itemList;  // Список элементов, которые будут отображаться в ListView
    private Context context;        // Контекст, необходимый для доступа к ресурсам и службам Android

    // Конструктор адаптера
    public NotificationsListViewAdapter(Context context, List<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    // Возвращает количество элементов в списке
    @Override
    public int getCount() {
        return itemList.size();
    }

    // Возвращает элемент списка по указанной позиции
    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    // Возвращает идентификатор элемента списка по указанной позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Метод, вызываемый для отображения каждого элемента списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Проверка, создан ли уже макет для элемента списка
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.notifications_list_view, parent, false);

            // Создание нового ViewHolder и связывание его с элементами макета
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.textViewItem);
            holder.checkBox = convertView.findViewById(R.id.checkBoxItem);

            // Сохранение ViewHolder внутри элемента списка
            convertView.setTag(holder);
        } else {
            // Если макет уже создан, используем сохраненный ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        // Получение текущего элемента из списка
        String currentItem = (String) getItem(position);

        // Проверка, чтобы избежать NullPointerException
        if (currentItem != null) {
            // Установка текста в TextView на основе текущего элемента
            holder.text.setText(currentItem);
        }

        return convertView;
    }

    // Внутренний класс для хранения элементов макета ListView
    private static class ViewHolder {
        TextView text;    // TextView для отображения текста элемента
        CheckBox checkBox; // CheckBox для отображения состояния элемента
    }
}

