package com.example.atlantisgroup.adapters;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.atlantisgroup.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView textProduct;
    public TextView textModel;
    public TextView textPrice;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public ProductViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        textProduct = itemView.findViewById(R.id.textProduct);
        textModel = itemView.findViewById(R.id.textModel);
        textPrice = itemView.findViewById(R.id.textPrice);
    }

    public void bind(Product product, final OnItemClickListener listener) {
        // Здесь устанавливайте значения из объекта Product в ваш ViewHolder
        imageView.setImageResource(product.getImageResource());
        textProduct.setText(product.getProductName());
        textModel.setText(product.getModel());
        textPrice.setText(product.getPrice());

        // Установите слушатель для обработки события нажатия
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
}