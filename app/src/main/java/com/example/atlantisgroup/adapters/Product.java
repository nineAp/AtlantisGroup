package com.example.atlantisgroup.adapters;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Класс Product представляет объект товара или категории.
 * Реализует интерфейс Serializable для возможности передачи объекта между компонентами приложения.
 */
public class Product implements Serializable {

    private String documentId;
    private String imageResource;  // Ресурс изображения товара или категории
    private String productName; // Название товара или категории
    private String model;        // Модель товара (для товара) или пустая строка (для категории)
    private String price;        // Цена товара (для товара) или информация о количестве товаров (для категории)
    private boolean isCategory;  // Флаг, указывающий, является ли объект категорией (true) или товаром (false)

    /**
     * Конструктор класса Product, инициализирует объект с заданными параметрами.
     *
     * @param imageResource Ресурс изображения товара или категории
     * @param productName   Название товара или категории
     * @param model         Модель товара (для товара) или пустая строка (для категории)
     * @param price         Цена товара (для товара) или информация о количестве товаров (для категории)
     * @param isCategory    Флаг, указывающий, является ли объект категорией (true) или товаром (false)
     */
    public Product(String imageResource, String productName, String model, String price, boolean isCategory, String documentId) {
        this.documentId = documentId;
        this.imageResource = imageResource;
        this.productName = productName;
        this.model = model;
        this.price = price;
        this.isCategory = isCategory;
    }

    /**
     * Метод для получения ресурса изображения товара или категории.
     *
     * @return Ресурс изображения
     */
    public String getImageResource() {
        return imageResource;
    }

    /**
     * Метод для получения названия товара или категории.
     *
     * @return Название товара или категории
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Метод для получения модели товара.
     *
     * @return Модель товара или пустая строка (для категории)
     */
    public String getModel() {
        return model;
    }

    /**
     * Метод для получения цены товара или информации о количестве товаров в категории.
     *
     * @return Цена товара или информация о количестве товаров
     */
    public String getPrice() {
        return price;
    }

    /**
     * Метод для проверки, является ли объект категорией.
     *
     * @return true, если объект является категорией; false, если объект - товар
     */
    public boolean getIsCategory() {
        return isCategory;
    }

    public String getDocumentid() { return documentId; }
}
