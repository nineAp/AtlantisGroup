package com.example.atlantisgroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    // Получение менеджера фрагментов
    FragmentManager fragmentManager = getSupportFragmentManager();

    // Создание экземпляра фрагмента для ввода номера телефона
    Fragment numberFragment = new LoginFragmentNumber();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Вызов метода onCreate базового класса Activity
        super.onCreate(savedInstanceState);

        // Установка макета для активности
        setContentView(R.layout.activity_main);

        // Начало транзакции фрагмента для замены содержимого контейнера (FrameLayout)
        fragmentManager.beginTransaction()
                .replace(R.id.login_framelayout, numberFragment)  // Замена фрагмента
                .commit();  // Применение транзакции
    }

    @Override
    protected void onPause() {
        fragmentManager.beginTransaction()
                .replace(R.id.login_framelayout, numberFragment)  // Замена фрагмента
                .commit();  // Применение транзакции
        super.onPause();
    }
}
