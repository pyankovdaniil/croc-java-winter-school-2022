package ru.croc.task18.shopelements;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String userName;
    private List<Order> orders;

    public User(int id, String name, List<Order> orders) {
        this.id = id;
        this.userName = name;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(" {\n\tid = ");
        stringBuilder.append(id).append(",\n\tuserName = ").append(userName).append(",\n\torders = {\n");
        for (Order order : orders) {
            stringBuilder.append("\t\t").append(order.getOrderNumber()).append(": ").
                    append(order.getProducts()).append(",\n");
        }
        return stringBuilder.append("\t}\n}").toString();
    }
}
