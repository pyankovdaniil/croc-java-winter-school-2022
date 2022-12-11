package ru.croc.task19.database;

import java.util.List;

public interface UpdatedDatabaseInserter {
    void insertLines(String databasePath, String databaseUsername, String databasePassword, List<String> orders,
                     List<String> deliveryDates, List<String> couriers, List<String> couriersOrders);
}
