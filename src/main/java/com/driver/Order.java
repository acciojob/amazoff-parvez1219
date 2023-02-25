package com.driver;

public class Order {


    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        this.deliveryTime = toMins(deliveryTime);
    }

    private int toMins(String deliveryTime) {
        String[] hourMin =deliveryTime.split(":");
        int hour =Integer.parseInt(hourMin[0]);
        int mins=Integer.parseInt(hourMin[1]);
        int hoursInMins=hour*60;
        return hoursInMins+mins;
    }


    public String getId() {

        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}