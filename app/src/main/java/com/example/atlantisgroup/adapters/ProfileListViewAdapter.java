package com.example.atlantisgroup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atlantisgroup.R;

/**
 * Адаптер для отображения данных в списке профиля.
 */
public class ProfileListViewAdapter extends BaseAdapter {

    private Context context;
    private String[] data;

    /**
     * Конструктор адаптера.
     *
     * @param context Контекст приложения
     * @param data    Массив строк, представляющих данные для отображения в списке
     */
    public ProfileListViewAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Метод, возвращающий отображение элемента списка для каждой позиции.
     *
     * @param position    Позиция элемента в списке
     * @param convertView Вид, который может быть повторно использован для отображения данных
     * @param parent      Родительский ViewGroup, в который будет добавлен элемент списка
     * @return View, представляющий элемент списка профиля
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Проверка, повторно используется ли вид элемента списка
        if (convertView == null) {
            // Если нет, создаем новый вид из макета profile_list_view.xml
            convertView = LayoutInflater.from(context).inflate(R.layout.profile_list_view, parent, false);
        }

        // Находим TextView внутри элемента списка
        TextView textViewLeft = convertView.findViewById(R.id.textViewLeft);

        // Устанавливаем текст слева в TextView
        textViewLeft.setText(data[position]);

        return convertView;
    }
}
