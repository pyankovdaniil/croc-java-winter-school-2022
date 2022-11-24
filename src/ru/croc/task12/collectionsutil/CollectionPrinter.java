package ru.croc.task12.collectionsutil;

import java.util.Collection;

public class CollectionPrinter {
    private CollectionPrinter() {
    }

    public static void printCollection(Collection<String> collection) {
        int currentIndex = 1;
        for (String s : collection) {
            System.out.println((currentIndex++) + ". " + s);
        }
        System.out.println();
    }
}
