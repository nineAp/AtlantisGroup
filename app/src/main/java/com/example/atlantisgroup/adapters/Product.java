package com.example.atlantisgroup.adapters;

import java.io.Serializable;

public class Product implements Serializable {
    private int imageResource;
    private String productName;
    private String model;
    private String price;

    private boolean isCategory;

    public Product(int imageResource, String productName, String model, String price, boolean isCategory) {
        this.imageResource = imageResource;
        this.productName = productName;
        this.model = model;
        this.price = price;
        this.isCategory = isCategory;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getProductName() {
        return productName;
    }

    public boolean getIsCategory() {return isCategory; }

    public String getModel() {
        return model;
    }

    public String getPrice() {
        return price;
    }
}