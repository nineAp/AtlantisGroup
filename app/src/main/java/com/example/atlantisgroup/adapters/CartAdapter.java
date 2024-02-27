package com.example.atlantisgroup.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atlantisgroup.Instances.CartInstance;
import com.example.atlantisgroup.Instances.ProductLocal;
import com.example.atlantisgroup.R;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<ProductLocal> productList;
    private Context context;

    public interface OnCartChangedListener {
        void onCartChanged();
    }

    private OnCartChangedListener listener;

    public void setOnCartChangedListener(OnCartChangedListener listener) {
        this.listener = listener;
    }


    public CartAdapter(List<ProductLocal> productList, Context context) {
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
        ProductLocal product = productList.get(position);

        // Установка данных в элементы макета
        holder.textName.setText(product.getTitle());
        holder.textModel.setText(product.getBrand_title());

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
        holder.textTotal.setText(String.format("%.2f", calculateTotal(product, currentQuantity.get())));

        // Обработка нажатия на кнопку "plus"
        holder.btnPlus.setOnClickListener(v -> {
            currentQuantity.incrementAndGet();
            holder.textQuantity.setText(String.valueOf(currentQuantity.get()));
            holder.textTotal.setText(String.format("%.2f", calculateTotal(product, currentQuantity.get())));
        });

        // Обработка нажатия на кнопку "minus"
        holder.btnMinus.setOnClickListener(v -> {
            CartInstance instance = CartInstance.getInstance();
            if (currentQuantity.get() > 1) {
                currentQuantity.decrementAndGet();
                holder.textQuantity.setText(String.valueOf(currentQuantity.get()));
                holder.textTotal.setText(String.format("%.2f", calculateTotal(product, currentQuantity.get())));
            } else {
                instance.removeProduct(product);
                notifyItemRemoved(position);
                if(listener != null) {
                    listener.onCartChanged();
                }
            }
        });
        Glide.with(context).load(product.getImageUrl()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Дополнительный метод для расчета общей суммы товара на основе количества
    private double calculateTotal(ProductLocal product, int quantity) {
        String price = product.getPrice();

        // Удаляем все символы, кроме цифр и точки
        String numericPrice = price.replaceAll("[^0-9]", "");

        // Преобразуем строку в число с плавающей точкой
        double numericValue = Double.parseDouble(numericPrice);

        // Вычисляем общую стоимость
        return numericValue * quantity;
    }


}