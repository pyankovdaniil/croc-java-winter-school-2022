package ru.croc.task15.comparators;

import ru.croc.task15.personutil.Person;

import java.util.Comparator;

public class PersonAgeDescNameAscComparator implements Comparator<Person> {
    @Override
    public int compare(Person person1, Person person2) {
        if (person1.getAge() == person2.getAge()) {
            return person1.getFullName().compareTo(person2.getFullName());
        }
        return person2.getAge() - person1.getAge();
    }
}
