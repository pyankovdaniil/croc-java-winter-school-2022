package ru.croc.task18.shopelements;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int orderNumber;
    private User user;
    private List<Product> products;

    public Order(int orderId, User user, List<Product> products) {
        this.orderNumber = orderId;
        this.user = user;
        this.products = products;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", user=" + user +
                ", products=" + products +
                '}';
    }
}
