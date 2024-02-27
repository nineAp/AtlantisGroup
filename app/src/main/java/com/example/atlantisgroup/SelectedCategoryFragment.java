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
            getProducts(product.getId(), new OnProductLoaded() {
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

    private void getProducts(Integer id, OnProductLoaded callback) {
        String url = String.format("https://atlantisapp.ru/products?options=[{\"by\": \"brand_id\", \"value\": [%s]}]", id);
        List<Product> productList = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("products");
                            for(int i=0; i<data.length(); i++) {
                                JSONObject object = data.getJSONObject(i);
                                Integer id = object.getInt("id");
                                String title = object.getString("title");
                                String brand = object.getString("brand_title");
                                String image = "https://atlantisapp.ru/public/uploads/" + (String) object.getJSONArray("images").get(0);
                                String price;
                                if(object.has("price") && !object.isNull("price")) {
                                    price = "Цена: " + String.valueOf(object.getInt("price")) + " руб.";
                                } else {
                                    price = "Цена: Уточняйте";
                                }
                                Product product = new Product(image, title, brand, price, false, id);
                                productList.add(product);
                            }
                            callback.onLoad(productList);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("CatalogError", error.toString());
                    }
                });
        queue.add(request);
    }
}
