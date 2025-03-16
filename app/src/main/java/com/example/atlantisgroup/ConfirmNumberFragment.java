package com.example.atlantisgroup;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class ConfirmNumberFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Создание представления фрагмента из макета
        View view = inflater.inflate(R.layout.fragment_confirm_number, container, false);
        // Получение ссылки на кнопку входа из макета
        ImageButton loginBtn = view.findViewById(R.id.login_btn);

        // Установка слушателя клика на кнопку входа
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создание намерения для перехода к PrimaryActivity
                Intent intent = new Intent(getActivity(), PrimaryActivity.class);
                // Запуск активности с использованием намерения
                startActivity(intent);

            }
        });

        // Возвращение созданного представления для отображения фрагмента
        return view;
    }
}
