package com.example.atlantisgroup.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.atlantisgroup.R;

import java.util.List;

/**
 * Адаптер для RecyclerView, отвечающий за отображение списка товаров или категорий.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private Context context;                // Контекст приложения
    private List<Product> productList;       // Список товаров или категорий

    // Интерфейс для обработки события нажатия на элемент списка
    private ProductViewHolder.OnItemClickListener onItemClickListener;

    /**
     * Конструктор адаптера.
     *
     * @param context      Контекст приложения
     * @param productList  Список товаров или категорий
     */
    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    /**
     * Создание нового ViewHolder при необходимости.
     *
     * @param parent   Родительский ViewGroup
     * @param viewType Тип представления (не используется в данном случае)
     * @return Новый объект ProductViewHolder
     */
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Создание нового ViewHolder, используя макет product_item_layout
        View view = LayoutInflater.from(context).inflate(R.layout.product_item_layout, parent, false);
        return new ProductViewHolder(view);
    }

    /**
     * Привязка данных к ViewHolder.
     *
     * @param holder   ViewHolder, к которому будут привязаны данные
     * @param position Позиция элемента в списке
     */
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        Glide.with(context)
                .load(product.getImageResource())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        assert e != null;
                        Log.d("MockData", e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imageView);
        // Привязка данных к ViewHolder с использованием метода bind
        holder.bind(product, onItemClickListener);
    }

    /**
     * Получение общего количества элементов в списке.
     *
     * @return Количество элементов в списке
     */
    @Override
    public int getItemCount() {
        return productList.size();
    }

    /**
     * Установка слушателя для обработки события нажатия на элемент списка.
     *
     * @param listener Объект слушателя ProductViewHolder.OnItemClickListener
     */
    public void setOnItemClickListener(ProductViewHolder.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
