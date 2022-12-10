package ru.croc.task18.dao;

import ru.croc.task18.shopelements.User;

import java.sql.*;

public class UserDaoImplementation implements UserDao {
    private String databasePath;
    private String databaseUsername;
    private String databasePassword;

    public UserDaoImplementation(String databasePath, String databaseUsername, String databasePassword) {
        this.databasePath = databasePath;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
    }

    @Override
    public User findUser(String userName) {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword)) {
            PreparedStatement productStatement =
                    connection.prepareStatement("select * from `user` where name=?");
            productStatement.setString(1, userName);

            ResultSet productSet = productStatement.executeQuery();
            if (productSet.next()) {
                return new User(productSet.getInt("id"), productSet.getString("name"));
            } else return null;
        } catch (SQLException e) {
            return null;
        }
    }
}
