package ru.croc.task17.database.implementations;

import ru.croc.task17.database.DatabaseInserter;

import java.sql.*;
import java.util.List;

public class ShopDatabaseInserter implements DatabaseInserter {
    private final Connection connection;

    public ShopDatabaseInserter(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertLines(String databasePath, String username, String password,
                            List<String> lines) {
        try {
            Statement statement = connection.createStatement();

            statement.execute("create table if not exists `user`(id int primary key" +
                    " auto_increment, name varchar not null)");
            statement.execute("create table if not exists `order`(id int primary key" +
                    " auto_increment, order_number int not null, user_id int not null, " +
                    "foreign key (user_id) references `user`(id))");
            statement.execute("create table if not exists `product`(id int primary key" +
                    " auto_increment, vendor_code varchar not null, name varchar not null," +
                    "price int not null)");
            statement.execute("alter table `product` add constraint if not exists vendor_code_unique " +
                    "unique(vendor_code)");
            statement.execute("create table if not exists `order_product`(id int primary key" +
                    " auto_increment, order_id int not null, foreign key(order_id) references `order`(id)," +
                    "product_id int not null, foreign key(product_id) references `product`(id))");

            statement.execute("delete from `order_product`; delete from `order`;" +
                    "delete from `user`; delete from `product`");

            for (String line : lines) {
                String[] elements = line.split(",");

                int orderNumber = Integer.parseInt(elements[0]);
                String userName = elements[1];
                String vendorCode = elements[2];
                String productName = elements[3];
                int productPrice = Integer.parseInt(elements[4]);


                PreparedStatement userStatement = connection.prepareStatement(
                        "select * from `user` where name=?");
                userStatement.setString(1, userName);

                ResultSet userSet = userStatement.executeQuery();
                if (!userSet.next()) {
                    PreparedStatement addUserStatement =
                            connection.prepareStatement("insert into `user`(name) values (?)");
                    addUserStatement.setString(1, userName);
                    addUserStatement.execute();
                }

                PreparedStatement productStatement =
                        connection.prepareStatement("select * from `product`" +
                                " where vendor_code=?");
                productStatement.setString(1, vendorCode);
                ResultSet productSet = productStatement.executeQuery();

                if (!productSet.next()) {
                    PreparedStatement addProductStatement = connection.prepareStatement(
                            "insert into `product` (vendor_code, name, price)" +
                                    " values (?, ?, ?)");
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

                PreparedStatement orderStatement = connection.prepareStatement(
                        "select * from `order` where order_number=?"
                );
                orderStatement.setInt(1, orderNumber);
                ResultSet orderSet = orderStatement.executeQuery();

                if (!orderSet.next()) {
                    PreparedStatement addOrderStatement = connection.prepareStatement(
                            "insert into `order`(order_number, user_id) values (?, ?)"
                    );
                    addOrderStatement.setInt(1, orderNumber);
                    addOrderStatement.setInt(2, userId);
                    addOrderStatement.execute();
                }

                int orderId = 0;
                orderSet = orderStatement.executeQuery();
                if (orderSet.next()) {
                    orderId = orderSet.getInt("id");
                }

                PreparedStatement orderProductStatement = connection.prepareStatement(
                        "insert into `order_product`(order_id, product_id) values (?, ?)"
                );
                orderProductStatement.setInt(1, orderId);
                orderProductStatement.setInt(2, productId);
                orderProductStatement.execute();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
