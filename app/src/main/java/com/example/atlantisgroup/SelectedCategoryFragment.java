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
        View view = inflater.inflate(R.layout.fragment_selected_category, container, false);
        RecyclerView rv = view.findViewById(R.id.selected_category_rv);
        TextView categoryTitle = view.findViewById(R.id.selected_category_title);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Product product = (Product) bundle.getSerializable("product");
            categoryTitle.setText(product.getProductName());
        }

        List<Product> productList = getMockProducts();
        ProductAdapter adapter = new ProductAdapter(getContext(), productList);
        adapter.setOnItemClickListener(new ProductViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Product selectedProduct = productList.get(position);
                if (selectedProduct != null) {
                    FragmentManager manager = requireActivity().getSupportFragmentManager();
                    ProductFragment fragment = new ProductFragment();
                    Bundle productBundle = new Bundle();
                    productBundle.putSerializable("product", selectedProduct);
                    fragment.setArguments(productBundle);
                    manager.beginTransaction().replace(R.id.primary_frame, fragment).commit();
                }
            }
        });
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        rv.setAdapter(adapter);

        return view;
    }
}