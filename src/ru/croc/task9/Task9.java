package ru.croc.task9;

import ru.croc.task9.util.PasswordFinder;

import java.util.concurrent.ExecutionException;

public class Task9 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int numberOfThreads = Integer.parseInt(args[0]);
        String passwordHash = args[1];

        PasswordFinder finder = new PasswordFinder(numberOfThreads, passwordHash);
        // answer is : passwrd
        System.out.println(finder.findPassword());
    }
}
