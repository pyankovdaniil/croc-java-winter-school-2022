package ru.croc.task15.ageutil;

import ru.croc.task15.personutil.Person;

import java.util.Objects;

public record AgeGroup(int lowerBound, int upperBound) {
    public static final int MAX_AGE = 123;

    public boolean containsPerson(Person person) {
        return person.getAge() >= lowerBound && person.getAge() <= upperBound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgeGroup group)) return false;
        return lowerBound == group.lowerBound && upperBound == group.upperBound;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerBound, upperBound);
    }

    @Override
    public String toString() {
        if (upperBound > MAX_AGE) {
            return lowerBound + "+";
        }
        return lowerBound + "-" + upperBound;
    }
}
