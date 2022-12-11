package ru.croc.task19.database;

import ru.croc.task17.database.ShopDatabaseInserter;
import ru.croc.task19.dao.CourierDao;
import ru.croc.task19.dao.CourierDaoImplementation;

import java.sql.*;
import java.util.List;

public class UpdatedShopDatabaseInserter implements UpdatedDatabaseInserter {
    @Override
    public void insertLines(String databasePath, String databaseUsername, String databasePassword, List<String> orders,
                            List<String> deliveryDates, List<String> couriers, List<String> couriersOrders) {
        try (Connection connection = DriverManager.getConnection(databasePath, databaseUsername,
                databasePassword)) {
            new ShopDatabaseInserter().insertLines(databasePath, databaseUsername, databasePassword, orders);

            PreparedStatement orderStatement = connection.prepareStatement("alter table `order` add column if " +
                    "not exists delivery_date varchar");
            orderStatement.execute();

            for (String line : deliveryDates) {
                String[] strings = line.split(",");
                int currentOrderNumber = Integer.parseInt(strings[0]);
                String deliveryDate = strings[1];

                orderStatement = connection.prepareStatement("update `order` set delivery_date=? " +
                        "where order_number=?");
                orderStatement.setString(1, deliveryDate);
                orderStatement.setInt(2, currentOrderNumber);
                orderStatement.execute();
            }

            Statement statement = connection.createStatement();

            statement.execute("create table if not exists `courier`(id int primary key auto_increment," +
                    "surname varchar not null, name varchar not null, employee_number varchar not null)");

            statement.execute("delete from `courier`");

            for (String line : couriers) {
                String[] strings = line.split(",");

                String surname = strings[0];
                String name = strings[1];
                String employeeNumber = strings[2];

                PreparedStatement courierStatement = connection.prepareStatement("insert into `courier`(surname, " +
                        "name, employee_number) values (?, ?, ?)");
                courierStatement.setString(1, surname);
                courierStatement.setString(2, name);
                courierStatement.setString(3, employeeNumber);
                courierStatement.execute();
            }

            statement.execute("create table if not exists `order_courier`(order_number int not null," +
                    "courier_id int not null)");

            statement.execute("delete from `order_courier`");


            CourierDao courierDao = new CourierDaoImplementation(databasePath, databaseUsername, databasePassword);
            for (String line : couriersOrders) {
                String[] strings = line.split(",");

                int orderNumber = Integer.parseInt(strings[0]);
                String employeeNumber = strings[1];

                PreparedStatement courierStatement = connection.prepareStatement("insert into `order_courier` " +
                        "values (?, ?)");
                courierStatement.setInt(1, orderNumber);
                courierStatement.setInt(2, courierDao.findCourierIdByEmployeeNumber(employeeNumber));
                courierStatement.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
