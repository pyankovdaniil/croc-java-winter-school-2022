package ru.croc.task10;

import ru.croc.task10.util.AuctionLot;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Task10 {
    public static void main(String[] args) throws InterruptedException {
        AuctionLot lot = new AuctionLot(BigDecimal.valueOf(100),
                LocalDateTime.of(2022, 11, 18, 23, 59,
                        59, (int) Math.pow(1, 9) - 1));

        System.out.println(lot);

        Thread firstThread = new Thread(() -> {
            BigDecimal price = BigDecimal.valueOf(100);
            while (!Thread.currentThread().isInterrupted()) {
                lot.acceptBet(price, "Bob");
                price = price.add(BigDecimal.valueOf(100));
            }
        });

        Thread secondThread = new Thread(() -> {
            BigDecimal price = BigDecimal.valueOf(1000);
            while (!Thread.currentThread().isInterrupted()) {
                lot.acceptBet(price, "Rick");
                price = price.add(BigDecimal.valueOf(200));
            }
        });

        Thread thirdThread = new Thread(() -> {
            BigDecimal price = BigDecimal.valueOf(1);
            while (!Thread.currentThread().isInterrupted()) {
                lot.acceptBet(price, "Kate");
                price = price.add(BigDecimal.valueOf(1));
            }
        });

        firstThread.start();
        secondThread.start();
        thirdThread.start();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter 'stop' to stop accepting bets: ");
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("stop")) {
                firstThread.interrupt();
                secondThread.interrupt();
                thirdThread.interrupt();
                break;
            } else {
                System.out.print("Enter 'stop' to stop accepting bets: ");
            }
        }

        System.out.println(lot.getWinnerName());
    }
}
