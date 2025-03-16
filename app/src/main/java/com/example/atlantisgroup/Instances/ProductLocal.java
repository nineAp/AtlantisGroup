package com.example.atlantisgroup.Instances;

import com.example.atlantisgroup.adapters.Pair;

import java.util.ArrayList;

public class ProductLocal {
    private String title;
    private String brand_title;
    private String description;
    private String price;
    private String imageUrl;

    private ArrayList<Pair> features;

    public ProductLocal(String title, String description, String price, String imageUrl, ArrayList<Pair> features, String brand_title) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.features = features;
        this.brand_title = brand_title;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Pair> getFeatures() {
        return features;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public String getBrand_title() {
        return brand_title;
    }
}
