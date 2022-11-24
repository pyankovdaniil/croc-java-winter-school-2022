package ru.croc.task12.collectionsutil;

import java.io.*;
import java.util.Collection;

public class CollectionReader {
    private CollectionReader() {
    }

    public static void readCollectionFromFile(Collection<String> collection, String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(new File(filePath).getAbsolutePath())
        );

        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.equals("")) {
                collection.add(line);
            }
        }
    }
}
