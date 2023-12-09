package com.example.atlantisgroup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static com.example.atlantisgroup.MockData.getMockProducts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atlantisgroup.adapters.Product;
import com.example.atlantisgroup.adapters.ProductAdapter;
import com.example.atlantisgroup.adapters.ProductViewHolder;

import java.util.List;
import java.util.Objects;


public class SelectedCategoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Надуваем макет фрагмента
        View view = inflater.inflate(R.layout.fragment_selected_category, container, false);

        // Получаем ссылки на элементы макета
        RecyclerView rv = view.findViewById(R.id.selected_category_rv);
        TextView categoryTitle = view.findViewById(R.id.selected_category_title);

        // Получаем данные из аргументов фрагмента
        Bundle bundle = getArguments();
        if (bundle != null) {
            // Получаем выбранную категорию из аргументов и устанавливаем ее название
            Product product = (Product) bundle.getSerializable("product");
            categoryTitle.setText(product.getProductName());
        }

        // Получаем макет данных для RecyclerView
        List<Product> productList = getMockProducts();

        // Создаем и настраиваем адаптер для RecyclerView
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

        // Возвращаем созданный вид
        return view;
    }
}
