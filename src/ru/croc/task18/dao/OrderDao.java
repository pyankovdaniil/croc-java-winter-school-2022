package ru.croc.task18.dao;

import ru.croc.task18.exceptions.NoSuchUserException;
import ru.croc.task18.shopelements.Order;
import ru.croc.task18.shopelements.Product;

import java.util.List;

public interface OrderDao {
    int findOrderIdByOrderNumber(int orderNumber);
    List<Product> findOrderProducts(int orderNumber);
    List<Order> findUserOrders(String userName) throws NoSuchUserException;
    Order createOrder(String userName, List<Product> products) throws NoSuchUserException;
}
