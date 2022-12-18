package ru.croc.task17.database.implementations;

import ru.croc.task17.database.DatabasePrinter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShopDatabasePrinter implements DatabasePrinter {
    private final Connection connection;

    public ShopDatabasePrinter(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void printDatabase(String databasePath, String databaseUsername, String databasePassword) {
        try {
            Statement statement = connection.createStatement();

            System.out.println("--- User table ---");
            ResultSet userData = statement.executeQuery("select * from `user`");
            while (userData.next()) {
                System.out.println("{id=" + userData.getInt("id") + "}: "
                        + userData.getString("name"));
            }

            System.out.println("\n--- Product table ---");
            ResultSet productData = statement.executeQuery("select * from `product`");
            while (productData.next()) {
                System.out.println("{id=" + productData.getInt("id") + "}: "
                        + "vendor code - " + productData.getString("vendor_code") + ", "
                        + "name - " + productData.getString("name") + ", "
                        + "price - " + productData.getInt("price") + "rub.");
            }

            System.out.println("\n--- Order table ---");
            ResultSet orderData = statement.executeQuery("select * from `order`");
            while (orderData.next()) {
                System.out.println("{id=" + orderData.getInt("id") + "}: order #"
                        + orderData.getInt("order_number") + " was ordered by user" +
                        " with id={" + orderData.getInt("user_id") + "}");
            }

            System.out.println("\n--- Order_Product table ---");
            ResultSet orderProductData = statement.executeQuery("select * from `order_product` order by order_id");
            while (orderProductData.next()) {
                System.out.println("{id=" + orderProductData.getInt("id") + "}: order with id={"
                        + orderProductData.getInt("order_id") + "} has product" +
                        " with id={" + orderProductData.getInt("product_id") + "}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
