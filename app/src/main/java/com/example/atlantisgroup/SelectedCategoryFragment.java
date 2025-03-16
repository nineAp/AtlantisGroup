package com.example.atlantisgroup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.atlantisgroup.MockData.getMockProducts;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.atlantisgroup.adapters.Product;
import com.example.atlantisgroup.adapters.ProductAdapter;
import com.example.atlantisgroup.adapters.ProductViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SelectedCategoryFragment extends Fragment {

    RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Надуваем макет фрагмента
        View view = inflater.inflate(R.layout.fragment_selected_category, container, false);
        queue = Volley.newRequestQueue(requireContext().getApplicationContext());

        // Получаем ссылки на элементы макета
        RecyclerView rv = view.findViewById(R.id.selected_category_rv);
        TextView categoryTitle = view.findViewById(R.id.selected_category_title);

        // Получаем данные из аргументов фрагмента
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Получаем выбранную категорию из аргументов и устанавливаем ее название
            Product product = (Product) bundle.getSerializable("product");
            categoryTitle.setText(product.getProductName());
            getProducts(product.getDocumentid(), new OnProductLoaded() {
                @Override
                public void onLoad(List<Product> productList) {
                    Log.d("CatalogData", productList.toString());
                    ProductAdapter adapter = new ProductAdapter(getContext(), productList);
                    adapter.setOnItemClickListener(new ProductViewHolder.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            // Обработка клика на элементе списка продуктов
                            Product selectedProduct = productList.get(position);
                            if (selectedProduct != null) {
                                // Заменяем текущий фрагмент на фрагмент выбранного продукта
                                FragmentManager manager = requireActivity().getSupportFragmentManager();
                                ProductFragment fragment = new ProductFragment();
                                Bundle productBundle = new Bundle();
                                productBundle.putSerializable("product", selectedProduct);
                                fragment.setArguments(productBundle);
                                manager.beginTransaction().replace(R.id.primary_frame, fragment).commit();
                            }
                        }
                    });

                    // Настраиваем RecyclerView с использованием сетки
                    rv.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                    rv.setAdapter(adapter);
                }
            });
        }

        // Получаем макет данных для RecyclerView


        // Создаем и настраиваем адаптер для RecyclerView


        // Возвращаем созданный вид
        return view;
    }

    private interface OnProductLoaded {
        void onLoad(List<Product> productList);
    }

    private void getProducts(String id, OnProductLoaded callback) {
        String url = String.format("https://atlantis-cms.ru/api/categories/%s?populate=products&populate=products.images", id);
        List<Product> productList = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Получаем массив продуктов из ответа
                            JSONObject data = response.getJSONObject("data");
                            JSONArray productsArray = data.getJSONArray("products");

                            // Обрабатываем каждый продукт в массиве
                            for (int i = 0; i < productsArray.length(); i++) {
                                JSONObject productObject = productsArray.getJSONObject(i);

                                // Получаем данные о продукте
                                String productId = productObject.getString("documentId");
                                String title = productObject.getString("title");
                                String brand = productObject.getString("title");

                                // Получаем изображения
                                JSONArray imagesArray = productObject.getJSONArray("images");
                                String imageUrl = "";
                                if (imagesArray.length() > 0) {
                                    JSONObject imageObject = imagesArray.getJSONObject(0); // Берем первое изображение
                                    imageUrl = "https://atlantis-cms.ru" + imageObject.getString("url");
                                }

                                // Получаем цену
                                String price;
                                if (productObject.has("price") && !productObject.isNull("price")) {
                                    price = "Цена: " + productObject.getInt("price") + " руб.";
                                } else {
                                    price = "Цена: Уточняйте";
                                }

                                // Создаем объект продукта
                                Product product = new Product(imageUrl, title, brand, price, false, productId);
                                productList.add(product);
                            }

                            // Передаем полученный список продуктов в callback
                            callback.onLoad(productList);
                        } catch (JSONException e) {
                            Log.e("CatalogError", "Ошибка парсинга данных: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("CatalogError", error.toString());
                    }
                });

        // Отправляем запрос
        queue.add(request);
    }

}
