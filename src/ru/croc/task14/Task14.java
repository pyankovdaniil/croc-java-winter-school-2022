package ru.croc.task14;

import ru.croc.task12.collectionsutil.CollectionPrinter;
import ru.croc.task14.commentsutil.BlackListFilter;
import ru.croc.task14.commentsutil.StringBlackListFilter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class Task14 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader =new BufferedReader(new FileReader(
                new File("src\\ru\\croc\\task14\\comments.txt").getAbsolutePath()));

        List<String> comments = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            comments.add(line);
        }

        System.out.println("Comments before modifying:");
        CollectionPrinter.printCollection(comments);

        BlackListFilter<String> blackListFilter = new StringBlackListFilter();
        Predicate<String> checkLength = comment -> comment.length() < 20;
        Collection<String> filteredComments = blackListFilter.filterComments(checkLength, comments);

        System.out.println("Comments after modifying:");
        CollectionPrinter.printCollection(filteredComments);
    }
}
