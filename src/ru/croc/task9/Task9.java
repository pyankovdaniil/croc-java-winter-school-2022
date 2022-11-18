package ru.croc.task9;

import ru.croc.task9.util.HashCodeGenerator;
import ru.croc.task9.util.PasswordConverter;
import ru.croc.task9.util.PasswordFinder;

import java.util.concurrent.ExecutionException;

public class Task9 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int numberOfThreads = Integer.parseInt(args[0]);
        String passwordHash = args[1];

        // abababa, B34A35CED46287CEE03A93C99D03006C
        PasswordFinder finder = new PasswordFinder(numberOfThreads, passwordHash);
        System.out.println("Found the password: " + finder.findPassword());
    }
}
