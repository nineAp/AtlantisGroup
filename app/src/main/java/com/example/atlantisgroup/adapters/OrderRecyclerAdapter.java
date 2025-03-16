package com.example.atlantisgroup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atlantisgroup.R;

import java.util.List;

/**
 * RecyclerView адаптер для отображения элементов статуса заказа.
 */
public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.OrderStatusViewHolder> {

    private Context context;
    private List<OrderStatusItem> orderStatusList;

    /**
     * Конструктор адаптера.
     *
     * @param context        Контекст приложения.
     * @param orderStatusList Список элементов статуса заказа.
     */
    public OrderRecyclerAdapter(Context context, List<OrderStatusItem> orderStatusList) {
        this.context = context;
        this.orderStatusList = orderStatusList;
    }

    /**
     * Создает новый объект {@link OrderStatusViewHolder} при необходимости.
     *
     * @param parent   Родительский ViewGroup, в который будет добавлен новый объект после создания.
     * @param viewType Тип представления создаваемого элемента.
     * @return Новый объект {@link OrderStatusViewHolder}.
     */
    @NonNull
    @Override
    public OrderStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создание нового объекта ViewHolder с использованием layout'а order_recycler_layout.
        View view = LayoutInflater.from(context).inflate(R.layout.order_recycler_layout, parent, false);
        return new OrderStatusViewHolder(view);
    }

    /**
     * Обновляет содержимое ViewHolder в соответствии с содержимым элемента списка.
     *
     * @param holder   ViewHolder, который следует обновить.
     * @param position Позиция элемента в списке данных.
     */
    @Override
    public void onBindViewHolder(@NonNull OrderStatusViewHolder holder, int position) {
        // Получение объекта OrderStatusItem для текущей позиции в списке.
        OrderStatusItem orderStatusItem = orderStatusList.get(position);

        // Привязка данных OrderStatusItem к элементам ViewHolder.
        holder.bind(orderStatusItem);
    }

    /**
     * Возвращает общее количество элементов в списке данных.
     *
     * @return Общее количество элементов в списке данных.
     */
    @Override
    public int getItemCount() {
        return orderStatusList.size();
    }

    /**
     * ViewHolder для элементов статуса заказа в RecyclerView.
     */
    public class OrderStatusViewHolder extends RecyclerView.ViewHolder {

        private TextView textStatus;
        private TextView textOrderNumber;
        private TextView textStartDate;
        private ImageView imageLine;
        private TextView textEndDate;
        private ImageView image1;
        private ImageView image2;

        /**
         * Конструктор ViewHolder.
         *
         * @param itemView View, представляющий элемент списка.
         */
        public OrderStatusViewHolder(@NonNull View itemView) {
            super(itemView);

            // Инициализация элементов интерфейса из макета order_recycler_layout.
            textStatus = itemView.findViewById(R.id.status);
            textOrderNumber = itemView.findViewById(R.id.orderNumber);
            textStartDate = itemView.findViewById(R.id.startDate);
            imageLine = itemView.findViewById(R.id.lineImage);
            textEndDate = itemView.findViewById(R.id.endDate);
            image1 = itemView.findViewById(R.id.image1);
            image2 = itemView.findViewById(R.id.image2);
        }

        /**
         * Привязывает данные OrderStatusItem к элементам ViewHolder.
         *
         * @param orderStatusItem Объект OrderStatusItem, содержащий данные для отображения.
         */
        public void bind(OrderStatusItem orderStatusItem) {
            // Установка текста и изображений в элементы интерфейса на основе данных OrderStatusItem.
            textStatus.setText(orderStatusItem.getStatus());
            textOrderNumber.setText(orderStatusItem.getOrderNumber());
            textStartDate.setText(orderStatusItem.getStartDate());
            textEndDate.setText(orderStatusItem.getEndDate());
            imageLine.setImageResource(orderStatusItem.getLineImage());
            image1.setImageResource(orderStatusItem.getImage1());
            image2.setImageResource(orderStatusItem.getImage2());
        }
    }
}

