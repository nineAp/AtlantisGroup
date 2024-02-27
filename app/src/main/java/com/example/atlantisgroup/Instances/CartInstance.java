package com.example.atlantisgroup.Instances;

import com.example.atlantisgroup.adapters.Pair;

import java.util.ArrayList;
import java.util.List;

public class CartInstance {

    private static CartInstance instance;
    private List<ProductLocal> productList;


    private CartInstance() {
        this.productList = new ArrayList<ProductLocal>();
    }
    public static CartInstance getInstance() {
        if (instance == null) {
            instance = new CartInstance();
        }
        return instance;
    }

    public void addProduct(ProductLocal productLocal) {
        this.productList.add(productLocal);
    }

    public void resetProducts() {
        this.productList = new ArrayList<ProductLocal>();
    }

    public List<ProductLocal> getProductList() {
        return productList;
    }

    public void removeProduct(ProductLocal productLocal) {
        this.productList.remove(productLocal);
    }
}

