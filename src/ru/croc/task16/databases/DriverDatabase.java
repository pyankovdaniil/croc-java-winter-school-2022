package ru.croc.task16.databases;

import ru.croc.task16.taxielements.Car;
import ru.croc.task16.taxielements.Driver;
import ru.croc.task16.positioning.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DriverDatabase {
    private final static List<Driver> drivers;

    static {
        drivers = new ArrayList<>();

        drivers.add(new Driver("IvanovIvan", "79123585693", new Car("Comfort",
                Set.of("Baby chair", "6 places")), new Position(60.009574, 30.369200)));

        drivers.add(new Driver("PetrovPetr", "79123585357", new Car("Business",
                Set.of("Video screen")), new Position(60.015343, 30.387845)));

        drivers.add(new Driver("SokolovDmitriy", "79138964598", new Car("Comfort",
                Set.of("Wi-Fi")), new Position(60.007772, 30.392326)));

        drivers.add(new Driver("KrasilnikovSergey", "79563488543", new Car("Comfort",
                Set.of("Radio", "Baby chair", "6 places")), new Position(60.008041, 30.369569)));
    }

    private DriverDatabase() {
    }

    public static List<Driver> getDriverDatabase() {
        return new ArrayList<>(drivers);
    }
}
