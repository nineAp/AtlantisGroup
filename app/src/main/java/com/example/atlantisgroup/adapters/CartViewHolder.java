package com.example.atlantisgroup.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlantisgroup.R;

public class CartViewHolder extends RecyclerView.ViewHolder {

    // Объявление переменных для элементов макета, используемых внутри ViewHolder
    public ImageView imageView;   // Изображение товара
    public TextView textName;      // Название товара
    public TextView textModel;     // Модель товара
    public TextView textQuantity;  // Количество товара
    public Button btnPlus;         // Кнопка увеличения количества товара
    public Button btnMinus;        // Кнопка уменьшения количества товара
    public TextView textTotal;     // Общая стоимость товара

    // Конструктор ViewHolder, инициализирующий переменные для каждого элемента макета
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        textName = itemView.findViewById(R.id.textName);
        textModel = itemView.findViewById(R.id.textModel);
        textQuantity = itemView.findViewById(R.id.textQuantity);
        btnPlus = itemView.findViewById(R.id.btnPlus);
        btnMinus = itemView.findViewById(R.id.btnMinus);
        textTotal = itemView.findViewById(R.id.textTotal);
    }
}
