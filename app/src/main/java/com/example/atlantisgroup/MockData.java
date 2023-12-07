package com.example.atlantisgroup;

import com.example.atlantisgroup.adapters.Product;

import java.util.ArrayList;
import java.util.List;

public class MockData {
    public static List<Product> getMockProducts() {
        List<Product> productList = new ArrayList<>();

        productList.add(new Product(R.drawable.product_mock, "Товар 1", "Модель 123", "$50.00", false));
        productList.add(new Product(R.drawable.product_mock, "Товар 2", "Модель 456", "$30.00", false));
        productList.add(new Product(R.drawable.product_mock, "Товар 3", "Модель 789", "$25.00", false));
        productList.add(new Product(R.drawable.product_mock, "Товар 4", "Модель 123", "$50.00", false));
        productList.add(new Product(R.drawable.product_mock, "Товар 5", "Модель 456", "$30.00", false));
        productList.add(new Product(R.drawable.product_mock, "Товар 6", "Модель 789", "$25.00", false));

        return productList;
    }

    public static List<Product> getMockCategories() {
        List<Product> productList = new ArrayList<>();

        productList.add(new Product(R.drawable.product_mock, "Категория 1", "", "52 товара", true));
        productList.add(new Product(R.drawable.product_mock, "Категория 2", "", "34 товара", true));
        productList.add(new Product(R.drawable.product_mock, "Категория 3", "", "102 товара", true));
        productList.add(new Product(R.drawable.product_mock, "Категория 4", "", "93 товара", true));

        return productList;
    }


}
