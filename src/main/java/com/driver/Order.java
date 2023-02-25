package com.driver;

// child
public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
        this.id = id;
        String [] arr = deliveryTime.split(":");
        this.deliveryTime = Integer.parseInt(arr[0])*60 + Integer.parseInt(arr[1]);
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}