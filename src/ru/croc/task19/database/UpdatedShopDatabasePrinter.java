package ru.croc.task19.database;

import ru.croc.task17.database.DatabasePrinter;

import java.sql.*;

public class UpdatedShopDatabasePrinter implements DatabasePrinter {
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
                        + "vendor code - " + productData.getString("vendor_code") + ", "
                        + "name - " + productData.getString("name") + ", "
                        + "price - " + productData.getInt("price") + "rub.");
            }

            System.out.println("\n--- Order table ---");
            ResultSet orderData = statement.executeQuery("select * from `order` order by order_number");
            while (orderData.next()) {
                System.out.println("Order #" + orderData.getInt("order_number") + ": user with {id="
                        + orderData.getInt("user_id") + "} ordered product with {id="
                        + orderData.getInt("product_id") + "}. Delivery date = "
                        + orderData.getString("delivery_date"));
            }

            System.out.println("\n--- Courier table ---");
            ResultSet courierData = statement.executeQuery("select * from `courier`");
            while (courierData.next()) {
                System.out.println("{id=" + courierData.getInt("id") + "}: "
                        + courierData.getString("surname") + " " + courierData.getString("name")
                        + " {" + courierData.getString("employee_number") + "}");
            }

            System.out.println("\n--- Order_Courier table ---");
            ResultSet orderCourierData = statement.executeQuery("select * from `order_courier`");
            while (orderCourierData.next()) {
                System.out.println("Order #" + orderCourierData.getInt("order_number")
                        + " will be delivered by courier with {id = " + orderCourierData.getInt("courier_id")
                        + "}");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
