package ru.croc.task15.ageutil;

import ru.croc.task15.personutil.Person;

import java.util.List;
import java.util.Map;

public interface AgeGroupFilter {
    Map<AgeGroup, List<Person>> filter(Iterable<AgeGroup> groups, Iterable<Person> persons);
}
