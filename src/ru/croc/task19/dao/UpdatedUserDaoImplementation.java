package ru.croc.task19.dao;

import ru.croc.task18.dao.UserDaoImplementation;
import ru.croc.task18.shopelements.Order;
import ru.croc.task18.shopelements.User;
import ru.croc.task19.updatedshopelements.DeliveryOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UpdatedUserDaoImplementation implements UpdatedUserDao {
    private final String databasePath;
    private final String databaseUsername;
    private final String databasePassword;

    public UpdatedUserDaoImplementation(String databasePath, String databaseUsername, String databasePassword) {
        this.databasePath = databasePath;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }

    @Override
    public int findUserId(String userName) {
        return new UserDaoImplementation(databasePath, databaseUsername, databasePassword).findUserId(userName);
    }

    @Override
    public User findUser(String userName) {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
            int userId = findUserId(userName);

            if (userId == -1) {
                return null;
            }

            PreparedStatement userStatement = connection.prepareStatement("select * from `user` where name=?");
            userStatement.setString(1, userName);

            ResultSet userSet = userStatement.executeQuery();

            User user;
            if (userSet.next()) {
                user = new User(userId, userSet.getString("name"), null);
            } else return null;

            PreparedStatement statement = connection.prepareStatement("select * from `order` where user_id=?");
            statement.setInt(1, userId);

            ResultSet ordersSet = statement.executeQuery();

            List<Integer> foundedOrders = new ArrayList<>();
            List<Order> orders = new ArrayList<>();

            UpdatedOrderDao orderDao = new UpdatedOrderDaoImplementation(databasePath,
                    databaseUsername, databasePassword);

            while (ordersSet.next()) {
                int currentOrderNumber = ordersSet.getInt("order_number");
                if (!foundedOrders.contains(currentOrderNumber)) {
                    orders.add(orderDao.findOrderByOrderNumber(currentOrderNumber));
                    foundedOrders.add(currentOrderNumber);
                }
            }

            user.setOrders(orders);
            CourierDao courierDao = new CourierDaoImplementation(databasePath, databaseUsername, databasePassword);

            for (Order order : orders) {
                order.setUser(user);
                if (order instanceof DeliveryOrder deliveryOrder) {
                    deliveryOrder.setCourier(courierDao.findCourierNameByOrderNumber(order.getOrderNumber()));
                }
            }

            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public User getUserNameByOrderNumber(int orderNumber) {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
            PreparedStatement statement = connection.prepareStatement("select * from `order` where order_number=?");
            statement.setInt(1, orderNumber);
            ResultSet resultSet = statement.executeQuery();
            int userId = 0;
            if (resultSet.next()) {
                userId = resultSet.getInt("user_id");
            } else return null;

            statement = connection.prepareStatement("select * from `user` where id=?");
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(0, resultSet.getString("name"), null);
            } else return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<DeliveryOrder> getUserOrders(String userName) {
        List<Order> userOrders = findUser(userName).getOrders();
        List<DeliveryOrder> deliveryOrders = new ArrayList<>();

        for (Order order : userOrders) {
            deliveryOrders.add((DeliveryOrder) order);

        }
        return deliveryOrders;
    }
}
