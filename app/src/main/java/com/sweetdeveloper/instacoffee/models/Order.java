package com.sweetdeveloper.instacoffee.models;


public class Order{

    private String itemName;
    private String price;
    private String quantity;


    public Order() {
    }

    public Order(String itemName, String price, String quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;

    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
