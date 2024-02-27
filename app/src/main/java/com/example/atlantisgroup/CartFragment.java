package com.example.atlantisgroup;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.atlantisgroup.Instances.CartInstance;
import com.example.atlantisgroup.Instances.FavoritesInstance;
import com.example.atlantisgroup.Instances.ProductLocal;
import com.example.atlantisgroup.adapters.CartAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartFragment extends Fragment implements CartAdapter.OnCartChangedListener {

    private RequestQueue queue;

    @Override
    public void onAttach(@NonNull Context context) {
        queue = Volley.newRequestQueue(requireContext().getApplicationContext());
        super.onAttach(context);
    }
    TextView cartItemCountText;
    TextView totalPriceText;
    CartInstance instance;
    CartAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Создание представления фрагмента из макета
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // Получение мок-данных для списка продуктов в корзине
        // Получение ссылок на элементы из макета
        Button sendPhoneBtn = view.findViewById(R.id.send_phone_btn);
        TextView clearCartBtn = view.findViewById(R.id.cart_clear);
        cartItemCountText = view.findViewById(R.id.cart_item_count);
        totalPriceText = view.findViewById(R.id.order_sum_frag);


        // Настройка RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        instance = CartInstance.getInstance();
        // Создание и установка адаптера для списка продуктов в корзине
        adapter = new CartAdapter(instance.getProductList(), getContext());
        adapter.setOnCartChangedListener(this);
        recyclerView.setAdapter(adapter);
        changeTextData();


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
            JSONArray array = new JSONArray();
            try {
                JSONObject object = new JSONObject();
                object.put("name", "Пользователь оформил заказ, " + phoneNumber);
                List<ProductLocal> products = instance.getProductList();
                int totalPrice = 0;
                StringBuilder totalItemsBuilder = new StringBuilder();
                for (ProductLocal product : products) {
                    // Удаление всех символов, кроме цифр, из строки цены
                    String cleanedPrice = product.getPrice().replaceAll("\\D", "");
                    // Парсинг строки в целое число
                    int productPrice = Integer.parseInt(cleanedPrice);
                    // Добавление цены товара к общей стоимости
                    totalPrice += productPrice;
                    totalItemsBuilder.append(product.getTitle()+"\n");
                }
                object.put("price", totalPrice);
                JSONArray customFieldsArray = new JSONArray();
                JSONObject customFieldObject = new JSONObject();
                customFieldObject.put("field_id", 681303);

                JSONArray valuesArray = new JSONArray();
                JSONObject valuesObject = new JSONObject();
                valuesObject.put("value", totalItemsBuilder.toString());

                valuesArray.put(valuesObject);
                customFieldObject.put("values", valuesArray);

                customFieldsArray.put(customFieldObject);
                object.put("custom_fields_values", customFieldsArray);
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringRequest jsonArrayRequest = new StringRequest(
                    Request.Method.POST,
                    "https://trkir21.amocrm.ru/api/v4/leads",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Обработка успешного ответа от сервера
                            dialog.dismiss();
                            thanksDialog(); // Вызов метода для отображения диалога "Спасибо за запрос"
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    // Создаем заголовок с авторизацией Bearer и вашим токеном
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjYyYmExODVkZmMxYTEwY2E1ODU0NDdlMzI5OTgwNGY4YjFkZmJmOGZmODg4YTNhMjMwZDRkMjljNGM1YjZhZDI2NDAwMTIyZDdkYWJkZGY1In0.eyJhdWQiOiJmMTAzMjBkNC04OWI4LTRjYzQtYTk0Mi1kMWFlMjVjYzQ0NzkiLCJqdGkiOiI2MmJhMTg1ZGZjMWExMGNhNTg1NDQ3ZTMyOTk4MDRmOGIxZGZiZjhmZjg4OGEzYTIzMGQ0ZDI5YzRjNWI2YWQyNjQwMDEyMmQ3ZGFiZGRmNSIsImlhdCI6MTcwODg0MjU3NSwibmJmIjoxNzA4ODQyNTc1LCJleHAiOjE3NTA0NjQwMDAsInN1YiI6IjEwNzE3MjAyIiwiZ3JhbnRfdHlwZSI6IiIsImFjY291bnRfaWQiOjMxNTkwNTUwLCJiYXNlX2RvbWFpbiI6ImFtb2NybS5ydSIsInZlcnNpb24iOjIsInNjb3BlcyI6WyJjcm0iLCJmaWxlcyIsImZpbGVzX2RlbGV0ZSIsIm5vdGlmaWNhdGlvbnMiLCJwdXNoX25vdGlmaWNhdGlvbnMiXSwiaGFzaF91dWlkIjoiYjU3ZjcxYjEtZDQ5NS00NGQ4LTg5NGYtOTVhYmIzNmY4NWFjIn0.aGGHFr-mzx5B4ZkQ2PVFlbmE53U8gKWm3bgXrNTSMpmf_uFPXsrqsFCHsrn1Ciya9LYJnIqVRBLvXODNru4e1eHjU4uAPiVph_MHmYjpFAvORCIA1JGq_2E7V9wRpyuTdgJao1TMATSyWradYDcQR28hbk1-c-aYCtsUKrCmVP2bmYYZgVCs-orOMTkDSEc08Ln6i-pIgHu_8OMZoPmMJ16J0Sb-Q3Q5cNwGI6Wl4im4pXl2ENrwHgzqUndkU6G-LvgqRkE6Xq5t6WLC9KTiN-gMwrbtIe2gMhXXnMmuto8QvmWGs4NrPshoEZYPrp9bx6YpPT1znMwlbCcukkx_xA");
                    return headers;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return array.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            Log.d("mydata", jsonArrayRequest.getBodyContentType());
            queue.add(jsonArrayRequest);
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
            instance.resetProducts();
            adapter = new CartAdapter(instance.getProductList(), getContext());
            adapter.setOnCartChangedListener(this);
            cartItemCountText.setText("Корзина/0 шт");
            totalPriceText.setText("Сумма заказа.......0 руб.");
            recyclerView.setAdapter(adapter);
            dialog.dismiss();
        });

        // Обработчик нажатия на кнопку "Добавить в избранное"
        addToFavBtn.setOnClickListener(v -> {
            FavoritesInstance favoritesInstance = FavoritesInstance.getInstance();
            favoritesInstance.setProductList(instance.getProductList());
            instance.resetProducts();
            adapter = new CartAdapter(instance.getProductList(), getContext());
            adapter.setOnCartChangedListener(this);
            cartItemCountText.setText("Корзина/0 шт");
            totalPriceText.setText("Сумма заказа.......0 руб.");
            recyclerView.setAdapter(adapter);
            dialog.dismiss();
        });

        // Отображение диалога
        dialog.show();
    }

    @Override
    public void onCartChanged() {
        changeTextData();
    }

    private void changeTextData() {
        if (!instance.getProductList().isEmpty()) {
            cartItemCountText.setText("Корзина/" + String.valueOf(instance.getProductList().size()) + " шт");
            List<ProductLocal> products = instance.getProductList();
            int totalPrice = 0;
            for (ProductLocal product : products) {
                // Удаление всех символов, кроме цифр, из строки цены
                String cleanedPrice = product.getPrice().replaceAll("\\D", "");
                // Парсинг строки в целое число
                int productPrice = Integer.parseInt(cleanedPrice);
                // Добавление цены товара к общей стоимости
                totalPrice += productPrice;
            }
            totalPriceText.setText("Сумма заказа......."+ String.valueOf(totalPrice) + " руб.");
        } else {
            cartItemCountText.setText("Корзина/0 шт");
            totalPriceText.setText("Сумма заказа.......0 руб.");
        }
    }
}