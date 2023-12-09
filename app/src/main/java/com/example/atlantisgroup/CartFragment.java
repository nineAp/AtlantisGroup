package com.example.atlantisgroup;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.atlantisgroup.MockData.getMockProducts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.atlantisgroup.adapters.CartAdapter;
import com.example.atlantisgroup.adapters.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Создание представления фрагмента из макета
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Получение мок-данных для списка продуктов в корзине
        List<Product> productList = getMockProducts();

        // Получение ссылок на элементы из макета
        Button sendPhoneBtn = view.findViewById(R.id.send_phone_btn);
        TextView clearCartBtn = view.findViewById(R.id.cart_clear);

        // Настройка RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Создание и установка адаптера для списка продуктов в корзине
        CartAdapter adapter = new CartAdapter(productList, getContext());
        recyclerView.setAdapter(adapter);

        // Обработчик нажатия на кнопку "Очистить корзину"
        clearCartBtn.setOnClickListener(v -> {
            deleteItemsAlert();
        });

        // Обработчик нажатия на кнопку "Отправить номер"
        sendPhoneBtn.setOnClickListener(v -> {
            sendPhoneDialog();
        });

        // Возвращение созданного представления для отображения фрагмента
        return view;
    }

    // Метод для отображения диалога "Спасибо за запрос"
    private void thanksDialog() {
        // Создание диалога с пользовательским макетом
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.send_phone_alert, null);

        // Получение ссылок на элементы из макета
        TextView title = dialogView.findViewById(R.id.send_phone_title);
        TextView description = dialogView.findViewById(R.id.send_phone_description);

        // Установка текста в диалоге
        title.setText("Спасибо за запрос!");
        description.setText("Наш менеджер свяжется с Вами в ближайшее время");

        // Скрытие ненужных элементов (номера телефона и кнопки отправки)
        EditText editTextPhoneNumber = dialogView.findViewById(R.id.editTextPhoneNumber);
        Button btnSendPhoneNumber = dialogView.findViewById(R.id.btnSendPhoneNumber);
        editTextPhoneNumber.setVisibility(View.INVISIBLE);
        btnSendPhoneNumber.setVisibility(View.INVISIBLE);

        // Установка пользовательского макета в диалог
        builder.setView(dialogView);

        // Создание и отображение диалога
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Метод для отображения диалога ввода номера телефона
    private void sendPhoneDialog() {
        // Создание диалога с пользовательским макетом
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.send_phone_alert, null);

        // Получение ссылок на элементы из макета
        EditText editTextPhoneNumber = dialogView.findViewById(R.id.editTextPhoneNumber);
        Button btnSendPhoneNumber = dialogView.findViewById(R.id.btnSendPhoneNumber);

        // Установка пользовательского макета в диалог
        builder.setView(dialogView);

        // Создание и отображение диалога
        final AlertDialog dialog = builder.create();

        // Обработчик нажатия на кнопку "Отправить номер"
        btnSendPhoneNumber.setOnClickListener(v -> {
            // Обработка нажатия на кнопку "Отправить номер"
            String phoneNumber = editTextPhoneNumber.getText().toString();
            // Ваш код для обработки номера телефона

            // Закрыть диалог
            dialog.dismiss();

            // Отображение диалога "Спасибо за запрос"
            thanksDialog();
        });

        // Отображение диалога
        dialog.show();
    }

    // Метод для отображения диалога "Удалить товары"
    private void deleteItemsAlert() {
        // Создание диалога с пользовательским макетом
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_to_favorites, null);

        // Получение ссылок на элементы из макета
        Button addToFavBtn = dialogView.findViewById(R.id.add_to_fav_btn);
        Button clearCartBtn = dialogView.findViewById(R.id.clear_cart);

        // Установка пользовательского макета в диалог
        builder.setView(dialogView);

        // Создание и отображение диалога
        final AlertDialog dialog = builder.create();

        // Обработчик нажатия на кнопку "Очистить корзину"
        clearCartBtn.setOnClickListener(v -> {
            // Закрыть диалог
            dialog.dismiss();
        });

        // Обработчик нажатия на кнопку "Добавить в избранное"
        addToFavBtn.setOnClickListener(v -> {
            // Закрыть диалог
            dialog.dismiss();
        });

        // Отображение диалога
        dialog.show();
    }
}