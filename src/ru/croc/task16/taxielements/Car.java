package ru.croc.task16.taxielements;

import java.util.HashSet;
import java.util.Set;

public class Car {
    public static final int MAX_NUMBER_OF_FEATURES = 5;

    private String comfortClass;
    private Set<String> additionalFeatures;

    public Car(String comfortClass, Set<String> additionalFeatures) {
        this.comfortClass = comfortClass;
        this.additionalFeatures = additionalFeatures;
    }

    public Car(Car car) {
        this(car.comfortClass, car.additionalFeatures);
    }

    public boolean checkRequirements(Set<String> requirements, String comfortClass) {
        if (!this.comfortClass.equals(comfortClass)) {
            return false;
        }

        if (requirements.isEmpty()) {
            return true;
        }

        return additionalFeatures.containsAll(requirements);
    }

    public String getComfortClass() {
        return comfortClass;
    }

    public void setComfortClass(String comfortClass) {
        this.comfortClass = comfortClass;
    }

    public Set<String> getAdditionalFeatures() {
        return new HashSet<>(additionalFeatures);
    }

    public void setAdditionalFeatures(Set<String> additionalFeatures) {
        this.additionalFeatures = additionalFeatures;
    }
}
