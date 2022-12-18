package ru.croc.task18.dao.implementations;

import ru.croc.task18.dao.UserDao;
import ru.croc.task18.exceptions.NoSuchUserException;
import ru.croc.task18.shopelements.Order;
import ru.croc.task18.shopelements.User;

import java.sql.*;
import java.util.List;

public class UserDaoImplementation implements UserDao {
    private final String databasePath;
    private final String databaseUsername;
    private final String databasePassword;
    private final Connection connection;

    public UserDaoImplementation(String databasePath, String databaseUsername, String databasePassword,
                                 Connection connection) {
        this.databasePath = databasePath;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.connection = connection;
    }

    @Override
    public int findUserId(String userName) {
        try {
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
        try {
            PreparedStatement productStatement =
                    connection.prepareStatement("select * from `user` where name=?");
            productStatement.setString(1, userName);

            ResultSet userSet = productStatement.executeQuery();
            if (userSet.next()) {
                List<Order> userOrders = new OrderDaoImplementation(databasePath,
                        databaseUsername, databasePassword, connection).findUserOrders(userName);


                User user = new User(userSet.getInt("id"),
                        userSet.getString("name"), userOrders);

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
