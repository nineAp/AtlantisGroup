package com.example.atlantisgroup.adapters;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.atlantisgroup.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;             // Изображение товара или категории
    public TextView textProduct;            // Название товара или категории
    public TextView textModel;              // Модель товара (если применимо)
    public TextView textPrice;              // Цена товара (если применимо)

    // Интерфейс для обработки события нажатия на элемент списка
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    /**
     * Конструктор ViewHolder.
     *
     * @param itemView View, представляющий элемент списка
     */
    public ProductViewHolder(View itemView) {
        super(itemView);
        // Инициализация элементов интерфейса
        imageView = itemView.findViewById(R.id.imageView);
        textProduct = itemView.findViewById(R.id.textProduct);
        textModel = itemView.findViewById(R.id.textModel);
        textPrice = itemView.findViewById(R.id.textPrice);
    }

    /**
     * Метод bind, который привязывает данные объекта Product к ViewHolder.
     *
     * @param product  Объект Product, содержащий данные для отображения
     * @param listener Объект слушателя OnItemClickListener для обработки события нажатия
     */
    public void bind(Product product, final OnItemClickListener listener) {
        // Установка значений из объекта Product в соответствующие элементы ViewHolder
        textProduct.setText(product.getProductName());
        textModel.setText(product.getModel());
        textPrice.setText(product.getPrice());

        // Установка слушателя для обработки события нажатия на элемент списка
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Передача позиции элемента слушателю
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
}