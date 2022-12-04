package ru.croc.task16;

import ru.croc.task16.driverexceptions.NoMatchingDriversException;
import ru.croc.task16.positioning.Position;
import ru.croc.task16.taxiutil.DriverFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task16 {
    public static void main(String[] args) {
        Position customerPosition = new Position(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
        String comfortClass = args[2];

        List<String> specialWishList =
                new ArrayList<>(Arrays.asList(Arrays.copyOfRange(args, 3, args.length)));

        try {
            System.out.println("Found a driver for you: " +
                    DriverFinder.findClosestDriver(customerPosition, comfortClass, specialWishList));
        } catch (NoMatchingDriversException e) {
            System.out.println(e.getMessage());
        }
    }
}
