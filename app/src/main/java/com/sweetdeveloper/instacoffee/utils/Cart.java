package com.sweetdeveloper.instacoffee.utils;


import com.sweetdeveloper.instacoffee.models.Order;

import java.util.ArrayList;

import static com.sweetdeveloper.instacoffee.utils.Cart.orders;

public class Cart {

    public static ArrayList<Order> orders = new ArrayList<>();

    public static double getTotal(ArrayList<Order> orders){
        double total =0.00;
        for (int i = 0; i <= orders.size() - 1; i++) {
            total += Double.parseDouble(orders.get(i).getPrice());
        }
        return total;
    }
}
