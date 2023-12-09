package com.example.atlantisgroup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.atlantisgroup.adapters.NotificationsListViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Создание представления фрагмента из макета
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        // Получение ссылки на ListView из макета
        ListView listViewNotifications = view.findViewById(R.id.listViewNotifications);

        // Создание списка элементов для отображения в ListView
        List<String> itemList = new ArrayList<>();
        itemList.add("push-уведомления");
        itemList.add("email");
        itemList.add("sms");
        // Добавьте другие элементы...

        // Создаем адаптер для связывания данных и присваиваем его ListView
        NotificationsListViewAdapter adapter = new NotificationsListViewAdapter(requireContext(), itemList);
        listViewNotifications.setAdapter(adapter);

        // Возвращение созданного представления для отображения фрагмента
        return view;
    }
}
