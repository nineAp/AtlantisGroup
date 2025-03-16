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
            System.out.println((product));
            String id = product.getDocumentid();
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


    private void getProduct(String id, onLoadProduct callback) {
        String url = String.format("https://atlantis-cms.ru/api/products/%s?populate=images&populate=brand&populate=features", id);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    JSONObject product_res = response.getJSONObject("data");
                    System.out.println((product_res));
                    String title = product_res.getString("title");
                    String image = "";
                    if(!product_res.isNull("images")) {
                        image = "https://atlantis-cms.ru" + product_res.getJSONArray("images").getJSONObject(0).getString("url");
                        System.out.println(image);
                    }
                    String description = product_res.getString("description");
                    String brandTitle = product_res.getJSONObject("brand").getString("title");
                    String price;
                    if(product_res.has("price") && !product_res.isNull("price")) {
                        price = "Цена: " + product_res.getInt("price") + " руб.";
                    } else {
                        price = "Цена: Уточняйте";
                    }
                    ArrayList<Pair> features = new ArrayList<>();
                    JSONArray featuresArray = product_res.getJSONArray("features");
                    if(featuresArray.length() > 0) {
                        for(int i=0; i<featuresArray.length(); i++) {
                            JSONObject feature = featuresArray.getJSONObject(i);
                            System.out.println(feature);
                            features.add(new Pair(feature.getString("title"), feature.getString("value")));
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

