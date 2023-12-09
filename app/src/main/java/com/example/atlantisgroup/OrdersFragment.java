package com.example.atlantisgroup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.atlantisgroup.adapters.OrderRecyclerAdapter;
import com.example.atlantisgroup.adapters.OrderStatusItem;

import java.util.ArrayList;
import java.util.List;


public class OrdersFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.orders_rv);

        // Устанавливаем менеджер макета для RecyclerView (используем GridLayoutManager с одной колонкой).
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        // Создаем список элементов OrderStatusItem.
        List<OrderStatusItem> orderStatusList = createOrderStatusList();

        // Инициализируем адаптер.
        OrderRecyclerAdapter orderStatusAdapter = new OrderRecyclerAdapter(getContext(), orderStatusList);

        // Устанавливаем адаптер для RecyclerView.
        recyclerView.setAdapter(orderStatusAdapter);

        return view;
    }

    private List<OrderStatusItem> createOrderStatusList() {
        List<OrderStatusItem> orderStatusList = new ArrayList<>();

        // Добавляем элементы OrderStatusItem в список.
        orderStatusList.add(new OrderStatusItem("Выполнен", "A-000001", "15 сен \nдата заказа", R.drawable.status_line, "15 сен \nвыполнен", R.drawable.product_mock, R.drawable.product_mock));
        orderStatusList.add(new OrderStatusItem("В пути", "A-000002", "15 сен \nдата заказа", R.drawable.status_line, "17 сен \nвыполнен", R.drawable.product_mock, R.drawable.product_mock));
        // Добавьте другие элементы, если необходимо.

        return orderStatusList;
    }
}
