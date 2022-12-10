package ru.croc.task17.database;

import java.util.List;

public interface DatabaseInserter {
    void insertLines(String databasePath, String username, String password, List<String> lines);
}
