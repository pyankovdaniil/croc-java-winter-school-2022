package ru.croc.task17;

import ru.croc.task17.database.DatabaseInserter;
import ru.croc.task17.database.DatabasePrinter;
import ru.croc.task17.database.implementations.ShopDatabaseInserter;
import ru.croc.task17.database.implementations.ShopDatabasePrinter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Task17 {
    public static void main(String[] args) throws IOException, SQLException {
        String databasePath = "jdbc:h2:/DatabaseFiles/H2/shop_db";
        String databaseUsername = "sa";
        String databasePassword = "";

        Connection connection = DriverManager.getConnection(databasePath, databaseUsername, databasePassword);

        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        List<String> databaseLines = reader.lines().toList();
        DatabaseInserter databaseInserter = new ShopDatabaseInserter(connection);
        databaseInserter.insertLines(databasePath, databaseUsername, databasePassword, databaseLines);

        DatabasePrinter databasePrinter = new ShopDatabasePrinter(connection);
        databasePrinter.printDatabase(databasePath, databaseUsername, databasePassword);

        connection.close();
    }
}
