package com.example.atlantisgroup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PrimaryActivity extends AppCompatActivity {

    // Менеджер фрагментов для управления фрагментами в активности
    FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Вызов метода onCreate базового класса Activity
        super.onCreate(savedInstanceState);

        // Установка макета для активности
        setContentView(R.layout.activity_primary);

        // Получение ссылки на BottomNavigationView из макета
        BottomNavigationView bottomNavigationView = findViewById(R.id.bnv);

        // Начало транзакции фрагмента для замены содержимого контейнера (FrameLayout)
        manager.beginTransaction().replace(R.id.primary_frame, new CatalogFragment()).commit();

        // Установка слушателя для обработки выбора пунктов меню в BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                // Проверка выбранного пункта меню и создание соответствующего фрагмента
                if (item.getItemId() == R.id.favorites) {
                    selectedFragment = new FavoritesFragment();
                }
                if (item.getItemId() == R.id.catalog) {
                    selectedFragment = new CatalogFragment();
                }
                if (item.getItemId() == R.id.chat) {
                    selectedFragment = new ChatFragment();
                }
                if (item.getItemId() == R.id.profile) {
                    selectedFragment = new ProfileFragment();
                }

                // Замена текущего фрагмента выбранным фрагментом
                if (selectedFragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.primary_frame, selectedFragment);
                    transaction.commit();
                }

                // Возвращение true для указания обработки события выбора пункта меню
                return true;
            }
        });
    }
}
