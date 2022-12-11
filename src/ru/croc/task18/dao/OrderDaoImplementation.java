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
    private final String databasePath;
    private final String databaseUsername;
    private final String databasePassword;

    public OrderDaoImplementation(String databasePath, String databaseUsername, String databasePassword) {
        this.databasePath = databasePath;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }

    @Override
    public List<Product> findOrderProducts(int orderNumber) {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
            PreparedStatement orderStatement =
                    connection.prepareStatement("select * from `order` where order_number=?");
            orderStatement.setInt(1, orderNumber);

            ResultSet orderSet = orderStatement.executeQuery();
            List<Product> products = new ArrayList<>();

            ProductDao productDao = new ProductDaoImplementation(databasePath, databaseUsername, databasePassword);

            while (orderSet.next()) {
                products.add(productDao.findProductById(orderSet.getInt("product_id")));
            }

            return products;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Order> findUserOrders(String userName) throws NoSuchUserException {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
            int userId = new UserDaoImplementation(databasePath, databaseUsername,
                    databasePassword).findUserId(userName);

            PreparedStatement orderStatement =
                    connection.prepareStatement("select * from `order` where user_id=?");
            orderStatement.setInt(1, userId);

            ResultSet orderSet = orderStatement.executeQuery();
            List<Order> orders = new ArrayList<>();

            List<Integer> savedOrdersNumbers = new ArrayList<>();
            while (orderSet.next()) {
                int currentOrderNumber = orderSet.getInt("order_number");
                if (!savedOrdersNumbers.contains(currentOrderNumber)) {
                    orders.add(new Order(orderSet.getInt("order_number"), null,
                            findOrderProducts(orderSet.getInt("order_number"))));

                    savedOrdersNumbers.add(currentOrderNumber);
                }
            }

            return orders;
        } catch (SQLException e) {
            return null;
        }
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

            user.setOrders(findUserOrders(userName));

            return new Order(currentOrderNumber, user, correctProducts);
        } catch (SQLException e) {
            return null;
        }
    }
}
