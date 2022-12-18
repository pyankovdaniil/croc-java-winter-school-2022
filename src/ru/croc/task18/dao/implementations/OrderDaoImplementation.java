package ru.croc.task18.dao.implementations;

import ru.croc.task18.dao.OrderDao;
import ru.croc.task18.dao.ProductDao;
import ru.croc.task18.dao.UserDao;
import ru.croc.task18.exceptions.NoSuchUserException;
import ru.croc.task18.shopelements.Order;
import ru.croc.task18.shopelements.Product;
import ru.croc.task18.shopelements.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImplementation implements OrderDao {
    private final String databasePath;
    private final String databaseUsername;
    private final String databasePassword;
    private final Connection connection;

    public OrderDaoImplementation(String databasePath, String databaseUsername, String databasePassword,
                                  Connection connection) {
        this.databasePath = databasePath;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.connection = connection;
    }

    @Override
    public int findOrderIdByOrderNumber(int orderNumber) {
        try {
            PreparedStatement statement = connection.prepareStatement("select * from `order` where order_number=?");
            statement.setInt(1, orderNumber);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            return -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public List<Product> findOrderProducts(int orderNumber) {
        try {
            PreparedStatement orderStatement =
                    connection.prepareStatement("select * from `order` where order_number=?");
            orderStatement.setInt(1, orderNumber);

            ResultSet orderSet = orderStatement.executeQuery();

            int orderId = 0;
            if (orderSet.next()) {
                orderId = orderSet.getInt("id");
            }

            List<Product> products = new ArrayList<>();
            ProductDao productDao = new ProductDaoImplementation(databasePath, databaseUsername, databasePassword,
                    connection);

            PreparedStatement orderProductStatement = connection.prepareStatement(
                    "select * from `order_product` where order_id=?"
            );
            orderProductStatement.setInt(1, orderId);

            ResultSet orderProductSet = orderProductStatement.executeQuery();
            while (orderProductSet.next()) {
                products.add(productDao.findProductById(orderProductSet.getInt("product_id")));
            }

            return products;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Order> findUserOrders(String userName) throws NoSuchUserException {
        try {
            int userId = new UserDaoImplementation(databasePath, databaseUsername,
                    databasePassword, connection).findUserId(userName);

            List<Integer> userOrderNumbers = new ArrayList<>();
            PreparedStatement userStatement = connection.prepareStatement(
                    "select * from `order` where user_id=?"
            );
            userStatement.setInt(1, userId);

            ResultSet userNumbersSet = userStatement.executeQuery();
            while (userNumbersSet.next()) {
                userOrderNumbers.add(userNumbersSet.getInt("order_number"));
            }

            List<Order> orders = new ArrayList<>();
            for (int orderNumber : userOrderNumbers) {
                List<Product> products = findOrderProducts(orderNumber);
                orders.add(new Order(orderNumber, null, products));
            }

            return orders;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Order createOrder(String userName, List<Product> products) throws NoSuchUserException {
        try {
            UserDao userDao = new UserDaoImplementation(databasePath, databaseUsername, databasePassword, connection);
            User user = userDao.findUser(userName);

            if (user == null) {
                throw new NoSuchUserException("Can not find user with username " + userName + " in database");
            }

            if (products == null) {
                throw new IllegalArgumentException("List of products can not be null");
            }

            ProductDao productDao = new ProductDaoImplementation(databasePath, databaseUsername, databasePassword,
                    connection);
            List<Product> correctProducts = new ArrayList<>();
            for (Product product : products) {
                Product productToSave = productDao.findProduct(product.getVendorCode());
                if (productToSave != null) {
                    correctProducts.add(productToSave);
                }
            }

            PreparedStatement addOrder = connection.prepareStatement("insert into `order`(order_number, user_id)" +
                    " values (?, ?)");
            addOrder.setInt(1, 10);
            addOrder.setInt(2, user.getId());
            addOrder.execute();

            PreparedStatement orderIdStatement = connection.prepareStatement("select * from `order` where user_id=?");
            orderIdStatement.setInt(1, user.getId());

            int orderId = 0;
            ResultSet orderIdSet = orderIdStatement.executeQuery();
            while (orderIdSet.next()) {
                orderId = orderIdSet.getInt("id");
            }

            System.out.println("OSKODKSODKSODK " + orderId);

            PreparedStatement statement = connection.prepareStatement("insert into `order_product`(order_id, " +
                    "product_id) values (?, ?)");
            for (Product product : correctProducts) {
                statement.setInt(1, orderId);
                statement.setInt(2, product.getId());
                statement.execute();
            }

            statement = connection.prepareStatement("update `order` set order_number=? where id=?");
            statement.setInt(1, orderId);
            statement.setInt(2, orderId);
            statement.execute();

            user.setOrders(findUserOrders(userName));
            return new Order(orderId, user, correctProducts);
        } catch (SQLException e) {
            return null;
        }
    }
}
