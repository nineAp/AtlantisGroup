package com.example.atlantisgroup;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.atlantisgroup.adapters.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    public interface ProductsCallback {
        void onProductsReceived(List<Product> productList);

    }

    // Метод для получения списка моковых продуктов
    public static List<Product> getMockProducts(Context context, ProductsCallback callback) {
        List<Product> productList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://atlantis-cms.ru/api/products?populate=images",  // Обратите внимание на populate для изображений
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Извлекаем массив data
                            JSONArray data = response.getJSONArray("data");

                            // Проходим по массиву и создаем объекты Product
                            for (int i = 0; i < data.length(); i++) {
                                Product product;
                                JSONObject object = data.getJSONObject(i);

                                String title = object.getString("title");
                                String description = object.getString("description");
                                int price = object.getInt("price");  // Теперь это просто число
                                String priceText = "Цена: " + price + " руб.";  // Преобразуем в строку с рублями

                                String id = object.getString("documentId");

                                // Обработка изображений (если они есть)
                                String imageUrl = "";
                                if (object.has("images") && !object.isNull("images")) {
                                    JSONArray images = object.getJSONArray("images");
                                    if (images.length() > 0) {
                                        // Берем первое изображение из массива
                                        JSONObject imageObject = images.getJSONObject(0);
                                        imageUrl = "https://atlantis-cms.ru" + imageObject.getString("url");  // Формируем полный URL
                                    }
                                }

                                // Добавляем продукт в список
                                product = new Product(imageUrl, title, description, priceText, false, id);
                                productList.add(product);
                            }

                            // Отправляем список продуктов через callback
                            callback.onProductsReceived(productList);

                        } catch (JSONException e) {
                            Log.e("MockData", "JSON Parsing error: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MockData", "Request error: " + error.getMessage()); // Выводим ошибку, если запрос не удался
                    }
                }
        );

        queue.add(jsonArrayRequest);  // Добавляем запрос в очередь
        return productList;  // Важно! Этот список не будет сразу заполнен, так как запрос асинхронный.
    }


    // Метод для получения списка моковых категорий
    public static List<Product> getMockCategories(Context context,  ProductsCallback callback) {
        List<Product> productList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://atlantis-cms.ru/api/categories?populate[products][populate]=images",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Product product;
                            JSONArray data = response.getJSONArray("data");
                            for(int i = 0; i < data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);
                                String title = object.getString("title");
                                String documentId = object.getString("documentId");
                                String count = "Кол-во позиций " + String.valueOf(object.getJSONArray("products").length());

                                // Получаем продукты категории
                                JSONArray productsArray = object.getJSONArray("products");
                                if (productsArray.length() > 0) {
                                    // Если есть хотя бы один продукт, получаем изображение первого товара
                                    JSONObject firstProduct = productsArray.getJSONObject(0);
                                    if (firstProduct.has("images") && !firstProduct.isNull("images")) {
                                        String imageUrl = "https://atlantis-cms.ru" + firstProduct.getJSONArray("images").getJSONObject(0).getString("url");
                                        product = new Product(imageUrl, title, count, "", true, documentId);
                                        productList.add(product);
                                    } else {
                                        // Если изображений нет у первого товара
                                        product = new Product("", title, count, "", true, documentId);
                                        productList.add(product);
                                    }
                                } else {
                                    // Если в категории нет товаров
                                    product = new Product("", title, count, "", true, documentId);
                                    productList.add(product);
                                }
                            }
                            callback.onProductsReceived(productList);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MockData", "Request error: " + error.getMessage()); // Вывод лога в случае ошибки запроса
                    }
                }
        );
        queue.add(jsonObjectRequest);
        return productList;

    }
}
