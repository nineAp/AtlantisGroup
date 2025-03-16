package com.example.atlantisgroup;

import static com.example.atlantisgroup.MockData.getMockCategories;
import static com.example.atlantisgroup.MockData.getMockProducts;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.atlantisgroup.Instances.ProductLocal;
import com.example.atlantisgroup.adapters.Product;
import com.example.atlantisgroup.adapters.ProductAdapter;
import com.example.atlantisgroup.adapters.ProductViewHolder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CatalogFragment extends Fragment {

    private RequestQueue queue;
    @Override
    public void onAttach(@NonNull Context context) {
        queue = Volley.newRequestQueue(requireContext().getApplicationContext());
        super.onAttach(context);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Создание представления фрагмента из макета
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        // Получение ссылок на элементы из макета
        RecyclerView recyclerView = view.findViewById(R.id.catalog_rv);
        Button categoryBtn = view.findViewById(R.id.category_button);
        Button catalogBtn = view.findViewById(R.id.catalog_button);
        ImageView cartBtn = view.findViewById(R.id.cart);

        ImageView banner = view.findViewById(R.id.img_banner_catalog);

        // Обработчик нажатия на кнопку корзины
        cartBtn.setOnClickListener(v -> {
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.primary_frame, new CartFragment()).commit();
        });

        banner.setOnClickListener(v -> {
            sendPhoneDialog();
        });
        getMockProducts(getContext(), new MockData.ProductsCallback() {
            @Override
            public void onProductsReceived(List<Product> productList) {
                ProductAdapter adapter = new ProductAdapter(getContext(), productList);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new ProductViewHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // Обработка клика на элементе списка
                        Product clickedProduct = productList.get(position);
                        boolean isCategory = clickedProduct.getIsCategory();
                        FragmentManager manager = requireActivity().getSupportFragmentManager();
                        if(isCategory) {
                            // Переход к фрагменту с выбранной категорией
                            SelectedCategoryFragment fragment = new SelectedCategoryFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("product", clickedProduct);
                            fragment.setArguments(bundle);
                            manager.beginTransaction().replace(R.id.primary_frame, fragment).commit();
                        } else {
                            // Переход к фрагменту с выбранным продуктом
                            ProductFragment productFragment = new ProductFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("product", clickedProduct);
                            productFragment.setArguments(bundle);
                            manager.beginTransaction().replace(R.id.primary_frame, productFragment).commit();
                        }
                    }
                });
            }
        });

        // Обработчик нажатия на кнопку "Каталог"
        catalogBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                // Изменение внешнего вида кнопок при выборе "Каталога"
                categoryBtn.setBackground(getResources().getDrawable(R.drawable.button_background_blue));
                catalogBtn.setBackground(getResources().getDrawable(R.drawable.button_background));
                // Создание адаптера для списка продуктов (или категорий)
                getMockProducts(getContext(), new MockData.ProductsCallback() {
                    @Override
                    public void onProductsReceived(List<Product> productList) {
                        ProductAdapter adapter = new ProductAdapter(getContext(), productList);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new ProductViewHolder.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                // Обработка клика на элементе списка
                                Product clickedProduct = productList.get(position);
                                boolean isCategory = clickedProduct.getIsCategory();
                                FragmentManager manager = requireActivity().getSupportFragmentManager();
                                if(isCategory) {
                                    // Переход к фрагменту с выбранной категорией
                                    SelectedCategoryFragment fragment = new SelectedCategoryFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("product", clickedProduct);
                                    fragment.setArguments(bundle);
                                    manager.beginTransaction().replace(R.id.primary_frame, fragment).commit();
                                } else {
                                    // Переход к фрагменту с выбранным продуктом
                                    ProductFragment productFragment = new ProductFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("product", clickedProduct);
                                    productFragment.setArguments(bundle);
                                    manager.beginTransaction().replace(R.id.primary_frame, productFragment).commit();
                                }
                            }
                        });
                    }
                });
            }
        });

        // Обработчик нажатия на кнопку "Категории"
        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Изменение внешнего вида кнопок при выборе "Категорий"
                catalogBtn.setBackground(getResources().getDrawable(R.drawable.button_background_blue));
                categoryBtn.setBackground(getResources().getDrawable(R.drawable.button_background));
                // Создание адаптера для списка категорий
                getMockCategories(getContext(), new MockData.ProductsCallback() {
                    @Override
                    public void onProductsReceived(List<Product> productList) {
                        ProductAdapter adapter = new ProductAdapter(getContext(), productList);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new ProductViewHolder.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                // Обработка клика на элементе списка
                                Product clickedProduct = productList.get(position);
                                System.out.println(clickedProduct.toString());
                                boolean isCategory = clickedProduct.getIsCategory();
                                FragmentManager manager = requireActivity().getSupportFragmentManager();
                                if(isCategory) {
                                    // Переход к фрагменту с выбранной категорией
                                    SelectedCategoryFragment fragment = new SelectedCategoryFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("product", clickedProduct);
                                    fragment.setArguments(bundle);
                                    manager.beginTransaction().replace(R.id.primary_frame, fragment).commit();
                                } else {
                                    // Переход к фрагменту с выбранным продуктом
                                    ProductFragment productFragment = new ProductFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("product", clickedProduct);
                                    productFragment.setArguments(bundle);
                                    manager.beginTransaction().replace(R.id.primary_frame, productFragment).commit();
                                }
                            }
                        });
                    }
                });
            }
        });

        // Настройка менеджера компоновки для RecyclerView (2 столбца в виде сетки)
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        return view;
    }

    private void thanksDialog() {
        // Создание диалога с пользовательским макетом
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.send_phone_alert, null);

        // Получение ссылок на элементы из макета
        TextView title = dialogView.findViewById(R.id.send_phone_title);
        TextView description = dialogView.findViewById(R.id.send_phone_description);

        // Установка текста в диалоге
        title.setText("Спасибо за запрос!");
        description.setText("Наш менеджер свяжется с Вами в ближайшее время");

        // Скрытие ненужных элементов (номера телефона и кнопки отправки)
        EditText editTextPhoneNumber = dialogView.findViewById(R.id.editTextPhoneNumber);
        Button btnSendPhoneNumber = dialogView.findViewById(R.id.btnSendPhoneNumber);
        editTextPhoneNumber.setVisibility(View.INVISIBLE);
        btnSendPhoneNumber.setVisibility(View.INVISIBLE);

        // Установка пользовательского макета в диалог
        builder.setView(dialogView);

        // Создание и отображение диалога
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Метод для отображения диалога ввода номера телефона
    private void sendPhoneDialog() {
        // Создание диалога с пользовательским макетом
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.send_phone_alert, null);

        // Получение ссылок на элементы из макета
        EditText editTextPhoneNumber = dialogView.findViewById(R.id.editTextPhoneNumber);
        Button btnSendPhoneNumber = dialogView.findViewById(R.id.btnSendPhoneNumber);

        // Установка пользовательского макета в диалог
        builder.setView(dialogView);

        // Создание и отображение диалога
        final AlertDialog dialog = builder.create();

        // Обработчик нажатия на кнопку "Отправить номер"
        btnSendPhoneNumber.setOnClickListener(v -> {
            // Обработка нажатия на кнопку "Отправить номер"
            String phoneNumber = editTextPhoneNumber.getText().toString();
            JSONArray array = new JSONArray();
            try {
                JSONObject object = new JSONObject();
                object.put("name", "Пользователь оформил заказ, " + phoneNumber);
                object.put("price", 0);
                JSONArray customFieldsArray = new JSONArray();
                JSONObject customFieldObject = new JSONObject();
                customFieldObject.put("field_id", 632577);

                JSONArray valuesArray = new JSONArray();
                JSONObject valuesObject = new JSONObject();
                valuesObject.put("value", "Тех поддержка");

                valuesArray.put(valuesObject);
                customFieldObject.put("values", valuesArray);

                customFieldsArray.put(customFieldObject);
                object.put("custom_fields_values", customFieldsArray);
                array.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringRequest jsonArrayRequest = new StringRequest(
                    Request.Method.POST,
                    "https://atlantiscomp.amocrm.ru/api/v4/leads",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Обработка успешного ответа от сервера
                            dialog.dismiss();
                            thanksDialog(); // Вызов метода для отображения диалога "Спасибо за запрос"
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error);
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    // Создаем заголовок с авторизацией Bearer и вашим токеном
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjhmZWI0MzUxNDk0NWYwN2I2OTk2MTM0NDUyNjc4MDJiMzAyMzA1N2ViMmYzZmQ0MjFjMTljOWVhNDFmNDkyMzcwMmU1YzE5NzdjMTMxMGIyIn0.eyJhdWQiOiI0ZWMxM2NmYy1mZjQwLTQ3MjYtODEyNS0yZGRhMmY1ZjI4ZmIiLCJqdGkiOiI4ZmViNDM1MTQ5NDVmMDdiNjk5NjEzNDQ1MjY3ODAyYjMwMjMwNTdlYjJmM2ZkNDIxYzE5YzllYTQxZjQ5MjM3MDJlNWMxOTc3YzEzMTBiMiIsImlhdCI6MTc0MjEwNTA0MiwibmJmIjoxNzQyMTA1MDQyLCJleHAiOjE3NDYyMzA0MDAsInN1YiI6Ijc3MzYwNzciLCJncmFudF90eXBlIjoiIiwiYWNjb3VudF9pZCI6MzIyOTExMTAsImJhc2VfZG9tYWluIjoiYW1vY3JtLnJ1IiwidmVyc2lvbiI6Miwic2NvcGVzIjpbImNybSIsImZpbGVzIiwiZmlsZXNfZGVsZXRlIiwibm90aWZpY2F0aW9ucyIsInB1c2hfbm90aWZpY2F0aW9ucyJdLCJoYXNoX3V1aWQiOiIxNGJkNzA5MS04MjM3LTQwZjEtYjk5OC0xM2E0MGMwMGMyMDYiLCJhcGlfZG9tYWluIjoiYXBpLWIuYW1vY3JtLnJ1In0.b5rtEXaaNuM6JT5ktuWdiOCW4LauSb60QVj8dipLH0F_vKosL_kCE_OcYbHCwhSZ5jckND8YpsaGeXr4IXESJMwA8idstEe-6528mkJBKeMzgx0xh5oWNfS-gCNdhflx_FGKWZMDAMxkqjeOZyhVjk1wt-cVyxksvgHdPYgnNu6bdjVs2k5ZdcSLpe4F-aPlWCCZcvAnUjJq51pd9ZC1RAjLMgOuhuen1ld8Ot4DHBMvbqMDNuxYQPDwh3UeglKMj2rU8M4WtgE5YzR6Kpd8KdqQTqIVbfPFnL3-v3ALKQdiKhScmQUXUjQXcHBQ9ly44x5JBkrdFnXYuEc454_6Hg");
                    return headers;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return array.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            Log.d("mydata", jsonArrayRequest.getBodyContentType());
            queue.add(jsonArrayRequest);
        });
        // Отображение диалога
        dialog.show();
    }

}