package ru.croc.task18.shopelements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private int orderNumber;
    private User user;
    private List<Product> products;

    public Order(int orderNumber, User user, List<Product> products) {
        this.orderNumber = orderNumber;
        this.user = user;
        this.products = products;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getOrderNumber() == order.getOrderNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrderNumber());
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
