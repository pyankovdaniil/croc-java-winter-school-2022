package ru.croc.task18.dao;

import ru.croc.task18.exceptions.IllegalProductVendorCodeException;
import ru.croc.task18.exceptions.NoSuchUserException;
import ru.croc.task18.shopelements.Order;
import ru.croc.task18.shopelements.Product;
import ru.croc.task18.shopelements.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImplementation implements OrderDao {
    private String databasePath;
    private String databaseUsername;
    private String databasePassword;

    public OrderDaoImplementation(String databasePath, String databaseUsername, String databasePassword) {
        this.databasePath = databasePath;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }

    @Override
    public Order createOrder(String userName, List<Product> products) throws NoSuchUserException {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
            UserDao userDao = new UserDaoImplementation(databasePath, databaseUsername, databasePassword);
            User user = userDao.findUser(userName);

            if (user == null) {
                throw new NoSuchUserException("Can not find user with username " + userName + " in database");
            }

            if (products == null) {
                throw new IllegalArgumentException("List of products can not be null");
            }

            ProductDao productDao = new ProductDaoImplementation(databasePath, databaseUsername, databasePassword);
            List<Product> correctProducts = new ArrayList<>();
            for (Product product : products) {
                Product productToSave = productDao.findProduct(product.getVendorCode());
                if (productToSave == null) {
                    try {
                        correctProducts.add(productDao.createProduct(product));
                    } catch (IllegalProductVendorCodeException e) {
                        e.printStackTrace();
                    }
                } else {
                    correctProducts.add(productToSave);
                }
            }


            int currentOrderNumber = 0;
            PreparedStatement allOrders = connection.prepareStatement("select * from `order`");
            ResultSet allOrdersSet = allOrders.executeQuery();
            while (allOrdersSet.next()) {
                int orderNumber = allOrdersSet.getInt("order_number");
                if (orderNumber > currentOrderNumber) {
                    currentOrderNumber = orderNumber;
                }
            }
            currentOrderNumber++;

            PreparedStatement statement = connection.prepareStatement("insert into `order` values (?, ?, ?)");
            for (Product product : correctProducts) {
                statement.setInt(1, currentOrderNumber);
                statement.setInt(2, user.getId());
                statement.setInt(3, product.getId());
                statement.execute();
            }

            return new Order(currentOrderNumber, user, correctProducts);
        } catch (SQLException e) {
            return null;
        }
    }
}
