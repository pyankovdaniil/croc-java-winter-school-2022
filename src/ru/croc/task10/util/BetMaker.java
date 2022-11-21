package ru.croc.task10.util;

import java.math.BigDecimal;

public class BetMaker {
    private BetMaker() {
    }

    public static void makeBets(AuctionLot lot, String username, BigDecimal startPrice, BigDecimal additionalPrice) {
        BigDecimal price = startPrice;
        while (!Thread.currentThread().isInterrupted()) {
            lot.acceptBet(price, username);
            price = price.add(additionalPrice);
        }
    }
}
