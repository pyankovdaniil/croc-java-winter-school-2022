package ru.croc.task18.dao;

import ru.croc.task18.exceptions.NoSuchUserException;
import ru.croc.task18.shopelements.Order;
import ru.croc.task18.shopelements.User;

import java.sql.*;
import java.util.List;

public class UserDaoImplementation implements UserDao {
    private final String databasePath;
    private final String databaseUsername;
    private final String databasePassword;

    public UserDaoImplementation(String databasePath, String databaseUsername, String databasePassword) {
        this.databasePath = databasePath;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }

    @Override
    public int findUserId(String userName) {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
            PreparedStatement productStatement =
                    connection.prepareStatement("select * from `user` where name=?");
            productStatement.setString(1, userName);

            ResultSet userSet = productStatement.executeQuery();
            if (userSet.next()) {
                return userSet.getInt("id");
            } else return -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public User findUser(String userName) {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
            PreparedStatement productStatement =
                    connection.prepareStatement("select * from `user` where name=?");
            productStatement.setString(1, userName);

            ResultSet userSet = productStatement.executeQuery();
            if (userSet.next()) {
                List<Order> userOrders = new OrderDaoImplementation(databasePath,
                        databaseUsername, databasePassword).findUserOrders(userName);
                User user = new User(userSet.getInt("id"), userSet.getString("name"), userOrders);
                for (Order order : userOrders) {
                    order.setUser(user);
                }

                return user;
            } else return null;
        } catch (SQLException | NoSuchUserException e) {
            return null;
        }
    }
}
