package ru.croc.task16.taxilogic;

import ru.croc.task16.databases.DriverDatabase;
import ru.croc.task16.exceptions.NoMatchingDriversException;
import ru.croc.task16.taxielements.Driver;
import ru.croc.task16.positioning.Position;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class DriverFinder {
    public Driver findClosestDriver(Position customerPosition, String comfortClass,
                                    Set<String> specialWishList) throws NoMatchingDriversException {
        List<Driver> drivers = DriverDatabase.getDriverDatabase();

        if (drivers.isEmpty()) {
            throw new NoMatchingDriversException("Now we have no drivers :(");
        }

        Driver closestDriver = drivers.get(0);

        Comparator<Driver> driverComparator = (driver1, driver2) -> {
            boolean isSuitableDriver1 = driver1.getCar().checkRequirements(specialWishList, comfortClass);
            boolean isSuitableDriver2 = driver2.getCar().checkRequirements(specialWishList, comfortClass);
            if (isSuitableDriver1 && isSuitableDriver2) {
                return Double.compare(driver1.getPosition().calculateDistance(customerPosition),
                        driver2.getPosition().calculateDistance(customerPosition));
            } else if (!isSuitableDriver1 && isSuitableDriver2) {
                return 1;
            } else if (isSuitableDriver1) {
                return -1;
            }
            return 0;
        };

        for (Driver driver : drivers) {
            if (driverComparator.compare(closestDriver, driver) > 0) {
                closestDriver = driver;
            }
        }

        if (!closestDriver.getCar().checkRequirements(specialWishList, comfortClass)) {
            throw new NoMatchingDriversException("Can not find a driver for you :(");
        }

        return closestDriver;
    }
}
