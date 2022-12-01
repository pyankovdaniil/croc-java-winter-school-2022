package ru.croc.task15.ageutil;

import ru.croc.task15.comparators.AgeGroupDescendingComparator;
import ru.croc.task15.comparators.PersonAgeDescNameAscComparator;
import ru.croc.task15.personutil.Person;

import java.util.*;

public class AgeGroupFilterImplementation implements AgeGroupFilter {
    @Override
    public Map<AgeGroup, List<Person>> filter(Iterable<AgeGroup> groups, Iterable<Person> persons) {
        Map<AgeGroup, List<Person>> filteredPersons = new TreeMap<>((group1, group2) ->
                new AgeGroupDescendingComparator().compare(group1, group2));

        for (Person person : persons) {
            for (AgeGroup group : groups) {
                if (group.containsPerson(person)) {
                    filteredPersons.computeIfAbsent(group, k -> new ArrayList<>()).add(person);
                    break;
                }
            }
        }

        for (List<Person> personsInGroup : filteredPersons.values()) {
            personsInGroup.sort((person1, person2) ->
                    new PersonAgeDescNameAscComparator().compare(person1, person2));
        }

        return filteredPersons;
    }
}
