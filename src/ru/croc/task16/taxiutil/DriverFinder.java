package ru.croc.task16.taxiutil;

import ru.croc.task16.driverexceptions.NoMatchingDriversException;
import ru.croc.task16.driversutil.Driver;
import ru.croc.task16.positioning.Position;

import java.util.Comparator;
import java.util.List;

public class DriverFinder {
    private DriverFinder() {
    }

    public static Driver findClosestDriver(Position customerPosition, String comfortClass,
                                           List<String> specialWishList) throws NoMatchingDriversException {
        List<Driver> drivers = DriverDatabase.getDriverDatabase();

        Comparator<Driver> driverComparator = (driver1, driver2) -> {
            boolean isSuitableDriver1 = driver1.getCar().isMeetRequirements(specialWishList, comfortClass);
            boolean isSuitableDriver2 = driver2.getCar().isMeetRequirements(specialWishList, comfortClass);
            if (isSuitableDriver1 && isSuitableDriver2) {
                return Double.compare(driver1.getPosition().getDistance(customerPosition),
                        driver2.getPosition().getDistance(customerPosition));
            } else if (!isSuitableDriver1 && isSuitableDriver2) {
                return 1;
            } else if (isSuitableDriver1) {
                return -1;
            }
            return 0;
        };

        drivers.sort(driverComparator);

        if (!drivers.get(0).getCar().isMeetRequirements(specialWishList, comfortClass)) {
            throw new NoMatchingDriversException("Can not find a driver for you :(");
        }

        return drivers.get(0);
    }
}
