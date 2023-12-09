package com.example.atlantisgroup.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlantisgroup.R;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Product> productList;
    private Context context;

    public CartAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recycle_layout, parent, false);
        return new CartViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = productList.get(position);

        // Установка данных в элементы макета
        holder.imageView.setImageResource(product.getImageResource());
        holder.textName.setText(product.getProductName());
        holder.textModel.setText(product.getModel());

        // Извлечение начального значения количества из TextView
        int initialQuantity = 1;
        try {
            initialQuantity = Integer.parseInt(holder.textQuantity.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Использование AtomicInteger для хранения значения
        AtomicInteger currentQuantity = new AtomicInteger(initialQuantity);

        holder.textQuantity.setText(String.valueOf(currentQuantity.get()));
        holder.textTotal.setText(String.format("$%.2f", calculateTotal(product, currentQuantity.get())));

        // Обработка нажатия на кнопку "plus"
        holder.btnPlus.setOnClickListener(v -> {
            currentQuantity.incrementAndGet();
            holder.textQuantity.setText(String.valueOf(currentQuantity.get()));
            holder.textTotal.setText(String.format("$%.2f", calculateTotal(product, currentQuantity.get())));
        });

        // Обработка нажатия на кнопку "minus"
        holder.btnMinus.setOnClickListener(v -> {
            if (currentQuantity.get() > 1) {
                currentQuantity.decrementAndGet();
                holder.textQuantity.setText(String.valueOf(currentQuantity.get()));
                holder.textTotal.setText(String.format("$%.2f", calculateTotal(product, currentQuantity.get())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Дополнительный метод для расчета общей суммы товара на основе количества
    private double calculateTotal(Product product, int quantity) {
        return Float.parseFloat(product.getPrice().replace("$", "")) * quantity;
    }


}