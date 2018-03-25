package com.sweetdeveloper.instacoffee.models;


import java.util.ArrayList;

public class DBOrder {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String total;
    private ArrayList<Order> orderItems = new ArrayList<Order>();

    public DBOrder() {
    }

    public DBOrder(String name, String email, String phone, String address, String total, ArrayList<Order> orderItems) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.orderItems = orderItems;
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<Order> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<Order> orderItems) {
        this.orderItems = orderItems;
    }
}
