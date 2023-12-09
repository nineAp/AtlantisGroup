package com.example.atlantisgroup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

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
