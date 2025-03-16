package com.example.atlantisgroup.Instances;

import java.util.ArrayList;
import java.util.List;

public class FavoritesInstance {

    private static FavoritesInstance instance;
    private List<ProductLocal> productList;


    private FavoritesInstance() {
        this.productList = new ArrayList<ProductLocal>();
    }
    public static FavoritesInstance getInstance() {
        if (instance == null) {
            instance = new FavoritesInstance();
        }
        return instance;
    }

    public void setProductList(List<ProductLocal> productList) {
        this.productList = productList;
    }
    public List<ProductLocal> getProductList() {
        return productList;
    }

}
