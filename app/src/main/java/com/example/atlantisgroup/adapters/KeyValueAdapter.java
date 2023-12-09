package com.example.atlantisgroup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.atlantisgroup.R;
import com.example.atlantisgroup.adapters.Pair;

import java.util.ArrayList;

public class KeyValueAdapter extends ArrayAdapter<Pair> {

    public KeyValueAdapter(Context context, ArrayList<Pair> keyValueList) {
        super(context, 0, keyValueList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Получение данных для текущей позиции
        Pair pair = getItem(position);

        // Используем макет элемента списка
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        // Настройка TextView для отображения ключа
        TextView textKey = convertView.findViewById(R.id.textKey);
        textKey.setText(pair.getKey() + ": ");

        // Настройка TextView для отображения значения
        TextView textValue = convertView.findViewById(R.id.textValue);
        textValue.setText(pair.getValue());

        return convertView;
    }
}