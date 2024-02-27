package com.example.atlantisgroup;

import static com.example.atlantisgroup.MockData.getMockCategories;
import static com.example.atlantisgroup.MockData.getMockProducts;

import android.annotation.SuppressLint;
import android.os.Bundle;

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
import android.widget.ImageView;

import com.example.atlantisgroup.adapters.Product;
import com.example.atlantisgroup.adapters.ProductAdapter;
import com.example.atlantisgroup.adapters.ProductViewHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CatalogFragment extends Fragment {

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

        // Обработчик нажатия на кнопку корзины
        cartBtn.setOnClickListener(v -> {
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.primary_frame, new CartFragment()).commit();
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

}