package ru.croc.task15.ageutil;

import ru.croc.task15.personutil.Person;

import java.util.*;

public class AgeGroupFilterImplementation implements AgeGroupFilter {
    public Comparator<AgeGroup> ageGroupComparator = (group1, group2) -> group2.lowerBound() - group1.lowerBound();
    public Comparator<Person> personComparator = (person1, person2) -> {
        if (person1.getAge() == person2.getAge()) {
            return person1.getFullName().compareTo(person2.getFullName());
        }
        return person2.getAge() - person1.getAge();
    };

    @Override
    public Map<AgeGroup, List<Person>> filter(Iterable<AgeGroup> groups, Iterable<Person> persons) {
        Map<AgeGroup, List<Person>> filteredPersons = new TreeMap<>(ageGroupComparator);

        for (Person person : persons) {
            for (AgeGroup group : groups) {
                if (group.containsPerson(person)) {
                    filteredPersons.computeIfAbsent(group, k -> new ArrayList<>()).add(person);
                    break;
                }
            }
        }

        for (List<Person> personsInGroup : filteredPersons.values()) {
            personsInGroup.sort(personComparator);
        }

        return filteredPersons;
    }
}
