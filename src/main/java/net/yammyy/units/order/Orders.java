package net.yammyy.units.order;

import net.yammyy.units.goods.Good;

import java.util.*;

public class Orders {
    int id;
    Map<Integer, Order> goodsList;
    Delivery delivery;
    List<OrderStatus> statuses;

    public Orders(int id) {
        this.id = id;
        goodsList = new TreeMap<>();
        statuses = new ArrayList<>();
    }

    public int getID() {return id;}

    public List<Order> getOrder() {return new ArrayList<>(goodsList.values());}

    public OrderStatus getLastStatus() {
        if (statuses.size()==0) {return null;}
        int maxStatus = 0;
        for (int i = 0; i < statuses.size(); i++) {
            int statID = statuses.get(i).getID();
            if (maxStatus > statID) {
                maxStatus = statID;
            }
        }
        return statuses.get(maxStatus);
    }

    public List<OrderStatus> getAllStatuses() {return statuses;}

    public void addGood(Good good, int quantity) {
        Order order = goodsList.get(good.getID());
        if (order != null) {
            order.setQuantity(order.getQuantity()+quantity);
        } else {
            goodsList.put(good.getID(), new Order(good, quantity));
        }
    }
    public void addOrder(Order order)
    {
        Order oldOrder = goodsList.get(order.getGood().getID());
        if (oldOrder != null) {
            oldOrder.setQuantity(order.getQuantity());
        } else {
            goodsList.put(order.getGood().getID(), order);
        }
    }

    public int cartHasMoreGood(Good good, int quantity) {
        Order order = goodsList.get(good.getID());
        if (order==null){return 0;}
        int newOrderQuantity = order.getQuantity() - quantity;
        if (newOrderQuantity <= 0) {
            return 0;
        }
        return newOrderQuantity;
    }

    public void removeGood(Good good, int quantity) {
        Order order = goodsList.get(good.getID());
        if (order==null){return;}
        int orderQuantity = order.getQuantity();
        int newOrderQuantity = orderQuantity - quantity;
        if (newOrderQuantity <= 0) {
            goodsList.remove(good.getID());
        } else {
            order.setQuantity(newOrderQuantity);
        }
    }

    public void clear() {
        goodsList.clear();
    }

    public void setStatus(Status status, Date statusDate) {
        statuses.add(new OrderStatus(status,statusDate));
    }
}
