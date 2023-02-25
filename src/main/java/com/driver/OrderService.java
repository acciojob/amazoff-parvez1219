package com.driver;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class OrderService {
    @Autowired
    OrderRepository repo;

    public void addOrder(Order order) {
        repo.addOrder(order);
    }
    public void addPartner(String id) {
        repo.addDelPartner(id);
    }
    public void addOrderAndPartner(String order_id,String partner_id) {
        repo.addOrderAndPartner(order_id,partner_id);
    }
    public Order getOrderById(String order_id) {
        return repo.getOrderById(order_id);
    }

    public DeliveryPartner getPartnerId(String partnerId) {
        return repo.getPartnerId(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return repo.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return repo.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrder() {
        return repo.getAllOrder();
    }

    public Integer getCountOfUnassignedOrder() {
        return repo.getCountOfUnassignedOrder();
    }

    public Integer getCountOfOrdersLeftAfterGivenTime(String time, String partnerId) {
        return repo.getCountOfOrdersLeftAfterGivenTime(time,partnerId);
    }

    public String getLastDeliveryTime(String partnerId) {
        return repo.getLastDeliveryTime(partnerId);
    }

    public void deleteByPartnerId(String partnerId) {
        repo.delByPartnerId(partnerId);
    }

    public void deleteOrderById(String orderId) {
        repo.deleteOrderById(orderId);
    }
}