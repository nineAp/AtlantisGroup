package com.example.atlantisgroup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class LoginFragmentNumber extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Создание представления фрагмента из макета
        View view = inflater.inflate(R.layout.fragment_login_number, container, false);

        // Получение ссылки на кнопку "Получить код"
        ImageButton btnGetCode = view.findViewById(R.id.request_btn);

        // Установка слушателя нажатия на кнопку
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создание фрагмента подтверждения номера телефона
                Fragment confirmFragment = new ConfirmNumberFragment();

                // Начало транзакции фрагмента для замены текущего фрагмента
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

                // Замена текущего фрагмента на фрагмент подтверждения номера
                transaction.replace(R.id.login_framelayout, confirmFragment)
                        .addToBackStack(null) // Добавление в стек для возможности возврата
                        .commit();
            }
        });

        // Возвращение созданного представления для отображения фрагмента
        return view;
    }
}