package com.example.atlantisgroup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atlantisgroup.R;
import com.example.atlantisgroup.adapters.ProfileListViewAdapter;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Надуваем макет фрагмента
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Создаем массив данных для списка
        String[] data = {"История заказов", "Избранное", "Уведомления"};

        // Получаем ссылку на элемент ListView из макета
        ListView listView = view.findViewById(R.id.profile_lv);

        // Создаем и устанавливаем адаптер для списка
        ProfileListViewAdapter adapter = new ProfileListViewAdapter(getContext(), data);
        listView.setAdapter(adapter);

        // Получаем ссылку на элемент "Настройки" из макета
        TextView settings = view.findViewById(R.id.profile_settings);

        // Устанавливаем слушатель клика на элемент "Настройки"
        settings.setOnClickListener(v -> {
            // Заменяем текущий фрагмент на фрагмент настроек
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.primary_frame, new ProfileSettingFragment()).commit();
        });

        // Устанавливаем слушатель клика на элементы списка
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Заменяем текущий фрагмент в зависимости от выбранного элемента
                replaceFragment(data[position]);
            }
        });

        // Возвращаем созданный вид
        return view;
    }

    // Метод для замены фрагмента в зависимости от выбранного элемента списка
    private void replaceFragment(String itemText) {
        // Создаем новый фрагмент в зависимости от выбранного элемента
        Fragment newFragment = null;

        if ("История заказов".equals(itemText)) {
            newFragment = new OrdersFragment(); // Замените на свой фрагмент или оставьте null, если фрагмент не требуется
        } else if ("Избранное".equals(itemText)) {
            newFragment = new FavoritesFragment();
        } else if ("Уведомления".equals(itemText)) {
            newFragment = new NotificationsFragment();
        }

        // Если новый фрагмент создан, заменяем текущий фрагмент
        if (newFragment != null) {
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.primary_frame, newFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
