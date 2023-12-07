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
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.catalog_rv);
        Button category_btn = view.findViewById(R.id.category_button);
        Button catalog_btn = view.findViewById(R.id.catalog_button);

        catalog_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                category_btn.setBackground(getResources().getDrawable(R.drawable.button_background_blue));
                catalog_btn.setBackground(getResources().getDrawable(R.drawable.button_background));
                ProductAdapter adapter = createMockAdapter(true);
                recyclerView.setAdapter(adapter);
            }
        });

        category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalog_btn.setBackground(getResources().getDrawable(R.drawable.button_background_blue));
                category_btn.setBackground(getResources().getDrawable(R.drawable.button_background));
                ProductAdapter adapter = createMockAdapter(false);
                recyclerView.setAdapter(adapter);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        ProductAdapter adapter = createMockAdapter(true);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private ProductAdapter createMockAdapter(boolean isCategory) {
        List<Product> productList;
        if(isCategory) {
            productList = getMockProducts();
        } else {
            productList = getMockCategories();
        }
        ProductAdapter adapter = new ProductAdapter(getContext(), productList);
        adapter.setOnItemClickListener(new ProductViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Product clickedProduct = productList.get(position);
                boolean isCategory = clickedProduct.getIsCategory();
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                if(isCategory) {
                    SelectedCategoryFragment fragment = new SelectedCategoryFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product", clickedProduct);
                    fragment.setArguments(bundle);
                    manager.beginTransaction().replace(R.id.primary_frame, fragment).commit();
                } else {
                    ProductFragment productFragment = new ProductFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product", clickedProduct);
                    productFragment.setArguments(bundle);
                    manager.beginTransaction().replace(R.id.primary_frame, productFragment).commit();
                }
            }
        });
        return adapter;
    }


}