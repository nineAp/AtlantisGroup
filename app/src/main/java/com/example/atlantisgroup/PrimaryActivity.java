package com.example.atlantisgroup;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

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
                    //selectedFragment = new ChatFragment();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=Atlantisappbot"));
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.profile) {
                    selectedFragment = new ProfileFragment();
                }


                // Замена текущего фрагмента выбранным фрагментом
                if (selectedFragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.primary_frame, selectedFragment).addToBackStack(null);
                    transaction.commit();
                }

                // Возвращение true для указания обработки события выбора пункта меню
                return true;
            }
        });
    }
    private long backPressedTime; // Время последнего нажатия
    private Toast backPressedToast; // Сообщение для пользователя

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            // Если прошло меньше 2 секунд с последнего нажатия, выходим из приложения
            super.onBackPressed(); // Стандартное поведение кнопки "Назад"
        } else {
            // Если прошло больше 2 секунд, показываем сообщение
            backPressedToast = Toast.makeText(getApplicationContext(),
                    "Нажмите еще раз, чтобы выйти", Toast.LENGTH_SHORT);
            backPressedToast.show();
        }
        backPressedTime = System.currentTimeMillis(); // Обновляем время последнего нажатия
    }
}
