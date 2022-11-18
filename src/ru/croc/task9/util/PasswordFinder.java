package ru.croc.task9.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PasswordFinder {
    private static final long NUMBER_OF_VARIANTS = (long) Math.pow(26, 7);
    private final int numberOfThreads;
    private final String md5Hash;

    public PasswordFinder(int numberOfThreads, String md5Hash) {
        this.numberOfThreads = numberOfThreads;
        this.md5Hash = md5Hash;
    }

    public String findPassword() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<String>> futureList = new ArrayList<>();

        HashCodeGenerator hashCodeGenerator = new HashCodeGenerator();
        int hashCodeToFind = hashCodeGenerator.getHashCodeOfMd5Hash(md5Hash);

        for (int i = 0; i < numberOfThreads; i++) {
            long begin = i * (NUMBER_OF_VARIANTS / numberOfThreads);
            long end = (i == (numberOfThreads - 1)) ? (NUMBER_OF_VARIANTS - 1) : (begin + Math.ceilDiv(NUMBER_OF_VARIANTS, numberOfThreads));
            futureList.add(threadPool.submit(() -> {
                HashCodeGenerator generator = new HashCodeGenerator();
                String password = null;
                for (long j = begin; j < end; j++) {
                    if (generator.getHashCodeOfPasswordNumber(j) == hashCodeToFind) {
                        password = PasswordConverter.getPasswordFromNumber(j);
                        break;
                    }
                }
                return password;
            }));
        }
        threadPool.shutdown();

        while (true) {
            for (int i = 0; i < numberOfThreads; i++) {
                if (futureList.get(i).isDone()) {
                    String password = futureList.get(i).get();
                    if (password != null) {
                        System.out.println("Found password: " + password);
                        return password;
                    }
                }
            }
        }
    }
}
