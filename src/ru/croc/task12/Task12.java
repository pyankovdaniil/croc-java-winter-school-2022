package ru.croc.task12;

import ru.croc.task12.collectionsutil.CollectionPrinter;
import ru.croc.task12.collectionsutil.CollectionReader;
import ru.croc.task12.commentsutil.BlackListFilter;
import ru.croc.task12.commentsutil.BlackListFilterImplementation;

import java.io.*;
import java.util.*;

public class Task12 {
    public static void main(String[] args) throws IOException {
        List<String> commentList = new ArrayList<>();
        Set<String> blackList = new HashSet<>();

        CollectionReader.readCollectionFromFile(commentList, "src\\ru\\croc\\task12\\comments.txt");
        CollectionReader.readCollectionFromFile(blackList, "src\\ru\\croc\\task12\\blackList.txt");

        System.out.println("Black list: ");
        CollectionPrinter.printCollection(blackList);
        System.out.println("Comments before modifying: ");
        CollectionPrinter.printCollection(commentList);

        BlackListFilter filter = new BlackListFilterImplementation();
        filter.filterComments(commentList, blackList);

        System.out.println("Comments after modifying: ");
        CollectionPrinter.printCollection(commentList);
    }
}
