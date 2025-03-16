package com.example.atlantisgroup.adapters;

public class OrderStatusItem {
    private String status;
    private String orderNumber;
    private String startDate;
    private int lineImage;
    private String endDate;
    private int image1;
    private int image2;

    public OrderStatusItem(String status, String orderNumber, String startDate, int lineImage, String endDate, int image1, int image2) {
        this.status = status;
        this.orderNumber = orderNumber;
        this.startDate = startDate;
        this.lineImage = lineImage;
        this.endDate = endDate;
        this.image1 = image1;
        this.image2 = image2;
    }

    // Геттеры
    public String getStatus() {
        return status;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getLineImage() {
        return lineImage;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getImage1() {
        return image1;
    }

    public int getImage2() {
        return image2;
    }

    // Сеттеры
    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setLineImage(int lineImage) {
        this.lineImage = lineImage;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }
}