package ru.croc.task16.driversutil;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private String model;
    private String carNumber;
    private String comfortClass;
    private List<String> additionalFeatures;

    public Car(String model, String carNumber, String comfortClass, List<String> additionalFeatures) {
        this.model = model;
        this.carNumber = carNumber;
        this.comfortClass = comfortClass;
        this.additionalFeatures = additionalFeatures;
    }

    public Car(Car car) {
        this(car.model, car.carNumber, car.comfortClass, car.additionalFeatures);
    }

    public boolean isMeetRequirements(List<String> requirements, String comfortClass) {
        if (!this.comfortClass.equals(comfortClass)) {
            return false;
        }

        for (String requirement : requirements) {
            if (!additionalFeatures.contains(requirement)) {
                return false;
            }
        }
        return true;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getComfortClass() {
        return comfortClass;
    }

    public void setComfortClass(String comfortClass) {
        this.comfortClass = comfortClass;
    }

    public List<String> getAdditionalFeatures() {
        return new ArrayList<>(additionalFeatures);
    }

    public void setAdditionalFeatures(List<String> additionalFeatures) {
        this.additionalFeatures = additionalFeatures;
    }

    @Override
    public String toString() {
        return model + " (" + carNumber + ")";
    }
}
