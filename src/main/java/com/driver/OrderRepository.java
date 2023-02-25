package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@org.springframework.stereotype.Repository
public class OrderRepository {
    // map of String to (map of delhivery partner to list of order_id)
    HashMap<String,HashMap<DeliveryPartner, List<String>>> delPartners = new HashMap<>();

    // map of order to delhivery_partner_id
    HashMap<String,HashMap<Order,String>> Orders = new HashMap<>();

    public void addOrder(Order order) {
        HashMap<Order,String> map = new HashMap<>();
        map.put(order,"");
        Orders.put(order.getId(),map);
    }
    public void addDelPartner(String id) {
        DeliveryPartner partner = new DeliveryPartner(id);
        HashMap<DeliveryPartner, List<String>>map = new HashMap<>();
        map.put(partner, new ArrayList<>());
        delPartners.put(id,map);
    }
    public void addOrderAndPartner(String order_id,String partner_id) {
        // delPartner updation
        HashMap<DeliveryPartner, List<String>>partner = delPartners.get(partner_id);

        for(DeliveryPartner del : partner.keySet()) {
            // number of orders increased
            int num = del.getNumberOfOrders();
            del.setNumberOfOrders(++num);

            // assign order_id in partner.get(del)'s list
            partner.get(del).add(order_id);
        }

        // change in Orders map
        HashMap<Order,String> map = Orders.get(order_id);
        for(Order od : map.keySet()) {
            map.put(od,partner_id);
        }
    }
    public Order getOrderById(String order_id) {
        HashMap<Order,String> map = Orders.get(order_id);
        Order od = null;
        for(Order o : map.keySet()) {
            od = o;
        }
        return od;
    }

    public DeliveryPartner getPartnerId(String partnerId) {
        HashMap<DeliveryPartner, List<String>>partner = delPartners.get(partnerId);

        DeliveryPartner DP = null;
        for(DeliveryPartner del : partner.keySet()) {
            DP = del;
        }
        return DP;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        HashMap<DeliveryPartner, List<String>>partner = delPartners.get(partnerId);

        int oCount = 0;
        for(DeliveryPartner del : partner.keySet()) {
            oCount = del.getNumberOfOrders();
        }
        return Integer.valueOf(oCount);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        HashMap<DeliveryPartner, List<String>>partner = delPartners.get(partnerId);

        List<String>orders = new ArrayList<>();

        for(DeliveryPartner del : partner.keySet()) {
            orders = partner.get(del);
        }
        return orders;
    }
    public List<String> getAllOrder() {
        List<String> ordersList = new ArrayList<>();

        for(String str : Orders.keySet()) {
            ordersList.add(str);
        }

        return ordersList;
    }

    public Integer getCountOfUnassignedOrder() {
        int count = 0;

        for(String str : Orders.keySet()) {
            HashMap<Order,String> map = Orders.get(str);
            for (Order o : map.keySet()) {
                if (map.get(o).equals("")) {
                    count++;
                }
            }
        }

        return Integer.valueOf(count);
    }

    public Integer getCountOfOrdersLeftAfterGivenTime(String time, String partnerId) {
        // time from string to int
        String [] arr = time.split(":");
        int totalTime = Integer.parseInt(arr[0])*60 + Integer.parseInt(arr[1]);

        // order delivered by partner
        int i = 0;

        HashMap<DeliveryPartner,List<String>>partner = delPartners.get(partnerId);

        int length = 0;

        // o(1)
        for(DeliveryPartner del : partner.keySet()) {
            length = partner.get(del).size();

            for(String oId : partner.get(del)) {
                if (totalTime <= 0) {
                    break;
                }

                // o(1)
                HashMap<Order,String> map = Orders.get(oId);
                for(Order o : map.keySet()) {
                    totalTime -= o.getDeliveryTime();
                    i++;
                }
            }
        }

        return Integer.valueOf(length-i);
    }

    public String getLastDeliveryTime(String partnerId) {
        int max = 0;

        HashMap<DeliveryPartner,List<String>>partner = delPartners.get(partnerId);

        // o(1)
        for(DeliveryPartner del : partner.keySet()) {

            // list of OrderId
            for(String oId : partner.get(del)) {

                // o(1)
                // checking delhivery time of order
                HashMap<Order,String> map = Orders.get(oId);
                for(Order o : map.keySet()) {
                    if (max < o.getDeliveryTime()) {
                        max = o.getDeliveryTime();
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int num = max/60;
        int res = max%60;
        sb.append(num);
        sb.append(":");
        sb.append(res);
        return sb.toString();
    }

    public void delByPartnerId(String partnerId) {
        HashMap<DeliveryPartner,List<String>>partner = delPartners.get(partnerId);

        // o(1)
        for(DeliveryPartner del : partner.keySet()) {

            // list of OrderId
            for(String oId : partner.get(del)) {

                // o(1)
                // checking delhivery time of order
                HashMap<Order,String> map = Orders.get(oId);
                for(Order o : map.keySet()) {
                    map.put(o,"");
                }
            }
        }
        delPartners.remove(partnerId);
    }

    public void deleteOrderById(String orderId) {
        String partnerId = "";

        // remove order
        HashMap<Order,String> map = Orders.get(orderId);

        for(Order o:map.keySet()) {
            partnerId = map.get(o);
        }
        Orders.remove(orderId);

        // remove order from delPartner
        if (!partnerId.equals("")) {
            HashMap<DeliveryPartner,List<String>>partner = delPartners.get(partnerId);

            for(DeliveryPartner del : partner.keySet()) {
                List<String>listOfOrders = partner.get(del);
                for(int i=0;i<listOfOrders.size();i++) {
                    if (listOfOrders.get(i).equals(orderId)) {
                        listOfOrders.set(i,listOfOrders.get(listOfOrders.size()-1));
                        listOfOrders.remove(listOfOrders.size()-1);
                    }
                }
            }
        }
    }
}