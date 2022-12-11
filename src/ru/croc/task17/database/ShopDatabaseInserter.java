package ru.croc.task17.database;

import java.sql.*;
import java.util.List;

public class ShopDatabaseInserter implements DatabaseInserter {

    @Override
    public void insertLines(String databasePath, String databaseUsername, String databasePassword, List<String> lines) {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername,
                databasePassword)) {
            final Statement statement = connection.createStatement();

            statement.execute("create table if not exists `user`(id int primary key auto_increment," +
                    "name varchar not null)");
            statement.execute("create table if not exists `product`(id int primary key auto_increment," +
                    "vendor_code varchar not null, name varchar not null, price int not null)");
            statement.execute("create table if not exists `order`(order_number int not null, user_id int not null," +
                    "product_id int not null, foreign key (user_id) references `user`(id)," +
                    "foreign key (product_id) references `product`(id))");

            statement.execute("delete from `order`; delete from `user`; delete from `product` ");

            for (String line : lines) {
                String[] elements = line.split(",");
                int orderNumber = Integer.parseInt(elements[0]);
                String userName = elements[1];
                String vendorCode = elements[2];
                String productName = elements[3];
                int productPrice = Integer.parseInt(elements[4]);

                
                PreparedStatement userStatement = connection.prepareStatement("select * from `user` where name=?");
                userStatement.setString(1, userName);
                ResultSet userSet = userStatement.executeQuery();

                if (!userSet.next()) {
                    PreparedStatement addUserStatement =
                            connection.prepareStatement("insert into `user`(name) values (?)");
                    addUserStatement.setString(1, userName);
                    addUserStatement.execute();
                }

                
                PreparedStatement productStatement =
                        connection.prepareStatement("select * from `product` where vendor_code=?");
                productStatement.setString(1, vendorCode);
                ResultSet productSet = productStatement.executeQuery();

                if (!productSet.next()) {
                    PreparedStatement addProductStatement = connection.prepareStatement(
                            "insert into `product` (vendor_code, name, price) values (?, ?, ?)");
                    addProductStatement.setString(1, vendorCode);
                    addProductStatement.setString(2, productName);
                    addProductStatement.setInt(3, productPrice);
                    addProductStatement.execute();
                }

                int userId = 0;
                int productId = 0;

                userSet = userStatement.executeQuery();
                if (userSet.next()) {
                    userId = userSet.getInt("id");
                }

                productSet = productStatement.executeQuery();
                if (productSet.next()) {
                    productId = productSet.getInt("id");
                }

                PreparedStatement orderStatement =
                        connection.prepareStatement("insert into `order`(order_number, user_id, product_id) " +
                                "values (?, ?, ?)");
                orderStatement.setInt(1, orderNumber);
                orderStatement.setInt(2, userId);
                orderStatement.setInt(3, productId);
                orderStatement.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
