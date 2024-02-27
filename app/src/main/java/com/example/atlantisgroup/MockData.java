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
    public static List<Product> getMockProducts(Context context,  ProductsCallback callback) {
        List<Product> productList = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://atlantisapp.ru/products",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("products");
                            for(int i = 0; i < data.length(); i++) {
                                Product product;
                                JSONObject object = data.getJSONObject(i);
                                String title = object.getString("title");
                                String brand = object.getString("brand_title");
                                String price;
                                if(object.has("price") && !object.isNull("price")) {
                                    price = "Цена: " + String.valueOf(object.getInt("price")) + " руб.";
                                } else {
                                    price = "Цена: Уточняйте";
                                }
                                Integer id = object.getInt("id");
                                if (object.has("images") && !object.isNull("images")) {
                                    String imageUrl = "https://atlantisapp.ru/public/uploads/" + object.getJSONArray("images").get(0).toString();
                                    Log.d("MockData", imageUrl);
                                    product = new Product(imageUrl, title, brand, price, false, id);
                                    productList.add(product);
                                } else {
                                    product = new Product("", title, brand, price, false, id);
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
        queue.add(jsonArrayRequest);
        return productList;
    }

    // Метод для получения списка моковых категорий
    public static List<Product> getMockCategories(Context context,  ProductsCallback callback) {
        List<Product> productList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://atlantisapp.ru/brands/all",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0; i < response.length(); i++) {
                                Product product;
                                JSONObject object = response.getJSONObject(i);
                                String title = object.getString("title");
                                Integer id = object.getInt("id");
                                String count = "Кол-во позиций " + String.valueOf(object.getJSONArray("products").length());
                                if (object.has("images") && !object.isNull("images")) {
                                    String imageUrl = "https://atlantisapp.ru/public/uploads/" + object.getJSONArray("images").get(0).toString();
                                    product = new Product(imageUrl, title, count, "", true, id);
                                    productList.add(product);
                                } else {
                                    product = new Product("", title, "", "", true, id);
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
        queue.add(jsonArrayRequest);
        return productList;
    }
}
