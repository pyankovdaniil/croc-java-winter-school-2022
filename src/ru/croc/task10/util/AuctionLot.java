package ru.croc.task10.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReentrantLock;

public class AuctionLot {
    private static final ReentrantLock lock = new ReentrantLock();
    private BigDecimal currentPrice;
    private String winnerName;
    private LocalDateTime auctionEndTime;

    public AuctionLot(BigDecimal currentPrice, LocalDateTime auctionEndTime) {
        this.currentPrice = currentPrice;
        this.auctionEndTime = auctionEndTime;
    }

    public void acceptBet(BigDecimal newPrice, String userName) {
        LocalDateTime now = LocalDateTime.now();
        if (!(now.isBefore(auctionEndTime)) || userName == null) {
            return;
        }
        lock.lock();
        try {
            if (newPrice.compareTo(currentPrice) < 0) {
                return;
            }
            currentPrice = newPrice;
            this.winnerName = userName;
        } finally {
            lock.unlock();
        }
    }

    public String getWinnerName() {
        return "The winner is: " + winnerName + " with the price: " + currentPrice;
    }

    @Override
    public String toString() {
        return "AuctionLot{" +
                "currentPrice=" + currentPrice +
                ", userName='" + winnerName + '\'' +
                ", auctionEndTime=" + auctionEndTime +
                '}';
    }
}
