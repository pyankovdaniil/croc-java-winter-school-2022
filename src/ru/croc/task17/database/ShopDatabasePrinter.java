package ru.croc.task17.database;

import java.sql.*;

public class ShopDatabasePrinter implements DatabasePrinter {
    @Override
    public void printDatabase(String databasePath, String databaseUsername, String databasePassword) {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername,
                databasePassword)) {
            final Statement statement = connection.createStatement();

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
                        + "������� - " + productData.getString("vendor_code") + ", "
                        + "�������� - " + productData.getString("name") + ", "
                        + "���� - " + productData.getInt("price") + "���.");
            }

            System.out.println("\n--- Order table ---");
            ResultSet orderData = statement.executeQuery("select * from `order` order by order_number");
            while (orderData.next()) {
                System.out.println("����� #" + orderData.getInt("order_number") + ": ������������ � {id="
                        + orderData.getInt("user_id") + "} ������� ����� � {id="
                        + orderData.getInt("product_id") + "}");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}