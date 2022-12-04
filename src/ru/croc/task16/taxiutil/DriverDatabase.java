package ru.croc.task16.taxiutil;

import ru.croc.task16.driversutil.Car;
import ru.croc.task16.driversutil.Driver;
import ru.croc.task16.positioning.Position;

import java.util.ArrayList;
import java.util.List;

public class DriverDatabase {
    private final static List<Driver> drivers;

    static {
        drivers = new ArrayList<>();

        drivers.add(new Driver("Kader Sylla", "212-200-4661",
                new Car("Honda Civic LX", "KFS8494", "Comfort",
                        List.of("Baby chair")),
                new Position(54.73650985481675, 55.95598759046612)));

        drivers.add(new Driver("Tony Hawk", "415-256-2914",
                new Car("Infiniti M37 ", "KTH8034", "Comfort",
                        List.of("Baby chair", "6 places")),
                new Position(54.73256703279175, 55.950944646197925)));

        drivers.add(new Driver("Aaron Kyro", "947-486-2847",
                new Car("Chevrolet Malibu LS", "KCM 2195", "Cruise",
                        List.of("6 places")),
                new Position(12.3535, -30.3141)));

        drivers.add(new Driver("Jordan Carter", "802-238-24982",
                new Car("BMW6 Series 640i", "7KLH767", "Premier",
                        List.of("Burito")),
                new Position(-55.1355, 58.0185)));
    }

    private DriverDatabase() {
    }

    public static List<Driver> getDriverDatabase() {
        return new ArrayList<>(drivers);
    }
}
