package ru.croc.task19.dao.implementations;

import ru.croc.task18.dao.implementations.UserDaoImplementation;
import ru.croc.task18.shopelements.Order;
import ru.croc.task18.shopelements.User;
import ru.croc.task19.dao.CourierDao;
import ru.croc.task19.dao.UpdatedOrderDao;
import ru.croc.task19.dao.UpdatedUserDao;
import ru.croc.task19.updatedshopelements.DeliveryOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UpdatedUserDaoImplementation implements UpdatedUserDao {
    private final String databasePath;
    private final String databaseUsername;
    private final String databasePassword;
    private final Connection connection;

    public UpdatedUserDaoImplementation(String databasePath, String databaseUsername,
                                        String databasePassword, Connection connection) {
        this.databasePath = databasePath;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.connection = connection;
    }

    @Override
    public int findUserId(String userName) {
        return new UserDaoImplementation(databasePath, databaseUsername, databasePassword, connection)
                .findUserId(userName);
    }

    @Override
    public User findUser(String userName) {
        try {
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

            List<Order> orders = new ArrayList<>();

            UpdatedOrderDao orderDao = new UpdatedOrderDaoImplementation(databasePath,
                    databaseUsername, databasePassword, connection);

            while (ordersSet.next()) {
                int currentOrderNumber = ordersSet.getInt("order_number");
                orders.add(orderDao.findOrderByOrderNumber(currentOrderNumber));
            }

            user.setOrders(orders);

            CourierDao courierDao = new CourierDaoImplementation(databasePath, databaseUsername,
                    databasePassword, connection);

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
        try {
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
