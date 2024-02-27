package com.example.atlantisgroup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.atlantisgroup.Instances.CartInstance;
import com.example.atlantisgroup.Instances.ProductLocal;
import com.example.atlantisgroup.adapters.KeyValueAdapter;
import com.example.atlantisgroup.adapters.Pair;
import com.example.atlantisgroup.adapters.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyPair;
import java.util.ArrayList;


public class ProductFragment extends Fragment {

    RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Надуваем макет фрагмента
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        queue = Volley.newRequestQueue(requireContext().getApplicationContext());

        // Получаем ссылку на элементы интерфейса из макета
        LinearLayout showFeatures = view.findViewById(R.id.linearLayout);
        ImageView image = view.findViewById(R.id.pf_image_view);
        TextView title = view.findViewById(R.id.pf_product_title);
        TextView descripton = view.findViewById(R.id.pf_product_description);
        TextView model = view.findViewById(R.id.pf_product_model);
        TextView price = view.findViewById(R.id.pf_product_price);
        ListView listView = view.findViewById(R.id.features_list);
        Button addToCart = view.findViewById(R.id.pf_add_to_cart);


        // Получаем данные о продукте из переданных аргументов
        Bundle bundle = getArguments();
        if (bundle != null) {
            Product product = (Product) bundle.getSerializable("product");
            Integer id = product.getId();
            getProduct(id, new onLoadProduct() {
                @Override
                public void onLoad(ProductLocal product) {
                    title.setText(product.getTitle());
                    model.setText(product.getBrand_title());
                    Glide.with(requireContext().getApplicationContext()).load(product.getImageUrl()).into(image);
                    descripton.setText(product.getDescription());
                    price.setText(product.getPrice());
                    KeyValueAdapter adapter = new KeyValueAdapter(getContext(), product.getFeatures());
                    listView.setAdapter(adapter);

                    addToCart.setOnClickListener(v -> {
                        CartInstance instance = CartInstance.getInstance();
                        instance.addProduct(product);
                    });
                }
            });
        }

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


    private void getProduct(Integer id, onLoadProduct callback) {
        String url = String.format("https://atlantisapp.ru/products/getOne?id=%s", id);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String title = response.getString("title");
                    String image = "https://atlantisapp.ru/public/uploads/" + response.getJSONArray("images").get(0);
                    String description = response.getString("description");
                    String brandTitle = response.getJSONObject("brand").getString("title");
                    String price;
                    if(response.has("price") && !response.isNull("price")) {
                        price = "Цена: " + response.getInt("price") + " руб.";
                    } else {
                        price = "Цена: Уточняйте";
                    }
                    ArrayList<Pair> features = new ArrayList<>();
                    JSONArray featuresArray = response.getJSONArray("features");
                    for(int i=0; i<featuresArray.length(); i++) {
                        JSONObject feature = featuresArray.getJSONObject(i);
                        JSONArray items = feature.getJSONArray("items");
                        for(int k=0; k<items.length(); k++) {
                            JSONObject item = items.getJSONObject(k);
                            features.add(new Pair(item.getString("title"), item.getString("value")));
                        }
                    }
                    ProductLocal product = new ProductLocal(title, description, price, image, features, brandTitle);
                    Log.d("MockData", product.toString());
                    callback.onLoad(product);
                } catch (JSONException e) {
                    Log.d("MockData", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MockData", error.toString());
            }
        });

        queue.add(request);
    }

    private interface onLoadProduct {
        void onLoad(ProductLocal product);
    }

}

