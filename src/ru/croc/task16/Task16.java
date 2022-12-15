package ru.croc.task16;

import ru.croc.task16.exceptions.NoMatchingDriversException;
import ru.croc.task16.taxielements.Car;
import ru.croc.task16.positioning.Position;
import ru.croc.task16.taxilogic.DriverFinder;

import java.util.*;

public class Task16 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] customerPositionCoordinates = scanner.nextLine().split(", ");

        Position customerPosition = new Position(Double.parseDouble(customerPositionCoordinates[0]),
                Double.parseDouble(customerPositionCoordinates[1]));

        String comfortClass = scanner.nextLine();

        String specialWishesLine = scanner.nextLine();
        String[] specialWishes = specialWishesLine.split(", ");

        Set<String> specialWishSet;
        if (specialWishesLine.isEmpty()) {
            specialWishSet = new HashSet<>();
        } else if (specialWishes.length > Car.MAX_NUMBER_OF_FEATURES) {
            specialWishSet = Set.of(Arrays.copyOfRange(specialWishes, 0, Car.MAX_NUMBER_OF_FEATURES));
        } else {
            specialWishSet = Set.of(specialWishes);
        }

        DriverFinder driverFinder = new DriverFinder();
        try {
            System.out.println(driverFinder.findClosestDriver(customerPosition, comfortClass, specialWishSet));
        } catch (NoMatchingDriversException e) {
            System.out.println(e.getMessage());
        }
    }
}
