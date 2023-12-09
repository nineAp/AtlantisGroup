package com.example.atlantisgroup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atlantisgroup.adapters.KeyValueAdapter;
import com.example.atlantisgroup.adapters.Pair;
import com.example.atlantisgroup.adapters.Product;

import java.util.ArrayList;


public class ProductFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Надуваем макет фрагмента
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        // Получаем ссылку на элементы интерфейса из макета
        LinearLayout showFeatures = view.findViewById(R.id.linearLayout);
        TextView title = view.findViewById(R.id.pf_product_title);
        TextView model = view.findViewById(R.id.pf_product_model);
        TextView price = view.findViewById(R.id.pf_product_price);
        ListView listView = view.findViewById(R.id.features_list);

        // Получаем данные о продукте из переданных аргументов
        Bundle bundle = getArguments();
        if (bundle != null) {
            Product product = (Product) bundle.getSerializable("product");
            title.setText(product.getProductName());
            model.setText(product.getModel());
            price.setText(product.getPrice());
        }

        // Создаем и настраиваем адаптер для списка ключ-значение
        KeyValueAdapter adapter = new KeyValueAdapter(getContext(), createMockData());
        listView.setAdapter(adapter);

        // Скрываем список по умолчанию
        listView.setVisibility(View.INVISIBLE);

        // Устанавливаем слушатель клика на элемент "Показать характеристики"
        showFeatures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переключаем видимость списка
                if (listView.getVisibility() == View.INVISIBLE) {
                    listView.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // Возвращаем созданный вид
        return view;
    }

    // Метод для создания моковых данных в виде списка ключ-значение
    private static ArrayList<Pair> createMockData() {
        ArrayList<Pair> keyValueList = new ArrayList<>();
        keyValueList.add(new Pair("Характеристика 1", "Значение 1"));
        keyValueList.add(new Pair("Характеристика 2", "Значение 2"));
        keyValueList.add(new Pair("Характеристика 3", "Значение 3"));
        keyValueList.add(new Pair("Характеристика 4", "Значение 4"));
        keyValueList.add(new Pair("Характеристика 5", "Значение 5"));
        keyValueList.add(new Pair("Характеристика 6", "Значение 6"));
        keyValueList.add(new Pair("Характеристика 7", "Значение 7"));

        return keyValueList;
    }
}

