package ru.croc.task10;

import ru.croc.task10.util.AuctionLot;
import ru.croc.task10.util.BetMaker;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Task10 {
    public static void main(String[] args) throws InterruptedException {
        AuctionLot lot = new AuctionLot(BigDecimal.valueOf(100),
                LocalDateTime.now().plus(1, ChronoUnit.MINUTES));

        System.out.println(lot);

        Thread firstThread = new Thread(() -> {
            BetMaker.makeBets(lot, "Bob", BigDecimal.valueOf(100), BigDecimal.valueOf(100));
        });

        Thread secondThread = new Thread(() -> {
            BetMaker.makeBets(lot, "Rick", BigDecimal.valueOf(1000), BigDecimal.valueOf(200));
        });

        Thread thirdThread = new Thread(() -> {
            BetMaker.makeBets(lot, "Kate", BigDecimal.valueOf(1), BigDecimal.valueOf(1));
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
