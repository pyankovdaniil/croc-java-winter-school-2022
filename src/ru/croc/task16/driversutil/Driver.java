package ru.croc.task16.driversutil;

import ru.croc.task16.positioning.Position;

public class Driver {
    private String fullName;
    private String phoneNumber;
    private Car car;
    private Position position;

    public Driver(String fullName, String phoneNumber, Car car, Position position) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.car = car;
        this.position = position;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Car getCar() {
        return new Car(car);
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Position getPosition() {
        return new Position(position);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return fullName + ", " + phoneNumber + ", " + car;
    }
}
