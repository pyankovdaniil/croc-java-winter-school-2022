package ru.croc.task15;

import ru.croc.task15.ageutil.AgeGroup;
import ru.croc.task15.ageutil.AgeGroupFilterImplementation;
import ru.croc.task15.personutil.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Task15 {
    public static void main(String[] args) {
        List<AgeGroup> groups = new ArrayList<>();
        int currentLowerBound = -1;
        for (String arg : args) {
            groups.add(new AgeGroup(currentLowerBound + 1, Integer.parseInt(arg)));
            currentLowerBound = Integer.parseInt(arg);
        }
        groups.add(new AgeGroup(currentLowerBound + 1, AgeGroup.MAX_AGE + 1));
        System.out.println(groups);

        Scanner scanner = new Scanner(System.in);
        List<Person> personList = new ArrayList<>();
        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            personList.add(Person.parse(line));
        }

        Map<AgeGroup, List<Person>> filteredPersons =
                new AgeGroupFilterImplementation().filter(groups, personList);

        for (Map.Entry<AgeGroup, List<Person>> entry : filteredPersons.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            List<Person> currentPersonList = entry.getValue();
            for (int i = 0; i < currentPersonList.size() - 1; i++) {
                System.out.print(currentPersonList.get(i) + ", ");
            }
            System.out.println(currentPersonList.get(currentPersonList.size() - 1));
        }
    }
}
