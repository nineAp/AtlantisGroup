package com.example.atlantisgroup;

import android.os.Bundle;
import static com.example.atlantisgroup.MockData.getMockProducts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atlantisgroup.adapters.Product;
import com.example.atlantisgroup.adapters.ProductAdapter;

import java.util.List;


public class FavoritesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Создание представления фрагмента из макета
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        // Получение ссылки на RecyclerView из макета
        RecyclerView recyclerView = view.findViewById(R.id.favorites_rv);

        // Получение списка продуктов-заглушек
        List<Product> productList = getMockProducts();

        // Настройка менеджера компоновки для RecyclerView (2 столбца в виде сетки)
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        // Создание и установка адаптера для RecyclerView
        ProductAdapter adapter = new ProductAdapter(getContext(), productList);
        recyclerView.setAdapter(adapter);

        // Возвращение созданного представления для отображения фрагмента
        return view;
    }
}