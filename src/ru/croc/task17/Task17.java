package ru.croc.task17;

import ru.croc.task17.database.DatabaseInserter;
import ru.croc.task17.database.ShopDatabaseInserter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Task17 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        List<String> databaseLines = reader.lines().toList();
        DatabaseInserter databaseInserter = new ShopDatabaseInserter();
        databaseInserter.insertLines("jdbc:h2:/DatabaseFiles/H2/shop_db",
                "sa", "", databaseLines);
    }
}
