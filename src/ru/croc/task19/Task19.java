package ru.croc.task19;

import ru.croc.task17.database.DatabasePrinter;
import ru.croc.task19.dao.CourierDao;
import ru.croc.task19.dao.implementations.CourierDaoImplementation;
import ru.croc.task19.dao.UpdatedUserDao;
import ru.croc.task19.dao.implementations.UpdatedUserDaoImplementation;
import ru.croc.task19.database.UpdatedDatabaseInserter;
import ru.croc.task19.database.UpdatedShopDatabaseInserter;
import ru.croc.task19.database.UpdatedShopDatabasePrinter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Task19 {
    public static void main(String[] args) throws FileNotFoundException, SQLException {
        String ordersFile = "src/ru/croc/task19/orders.txt";
        String deliveryDatesFile = "src/ru/croc/task19/deliveryDates.txt";
        String couriersFile = "src/ru/croc/task19/couriers.txt";
        String couriersOrdersFile = "src/ru/croc/task19/couriersOrders.txt";

        String databasePath = "jdbc:h2:/DatabaseFiles/H2/shop_db";
        String databaseUsername = "sa";
        String databasePassword = "";

        BufferedReader reader = new BufferedReader(new FileReader(ordersFile));
        List<String> orders = reader.lines().toList();

        reader = new BufferedReader(new FileReader(deliveryDatesFile));
        List<String> deliveryDates = reader.lines().toList();

        reader = new BufferedReader(new FileReader(couriersFile));
        List<String> couriers = reader.lines().toList();

        reader = new BufferedReader(new FileReader(couriersOrdersFile));
        List<String> couriersOrders = reader.lines().toList();

        Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword);

        UpdatedDatabaseInserter databaseInserter = new UpdatedShopDatabaseInserter(connection);
        databaseInserter.insertLines(databasePath, databaseUsername, databasePassword, orders,
                deliveryDates, couriers, couriersOrders);

        DatabasePrinter databasePrinter = new UpdatedShopDatabasePrinter(connection);
        databasePrinter.printDatabase(databasePath, databaseUsername, databasePassword);


        UpdatedUserDao userDao = new UpdatedUserDaoImplementation(databasePath, databaseUsername, databasePassword, connection);
        CourierDao courierDao = new CourierDaoImplementation(databasePath, databaseUsername, databasePassword, connection);

        System.out.println("\n\n----- Vasya's orders: -----");
        userDao.getUserOrders("vasya").forEach(System.out::println);

        System.out.println("\n\n----- Petr's orders (he is a courier): -----");
        courierDao.getCourierOrders("N2").forEach(System.out::println);

        connection.close();
    }
}
