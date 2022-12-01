package ru.croc.task15.personutil;

public class Person {
    private final int age;
    private final String fullName;

    private Person(int age, String fullName) {
        this.age = age;
        this.fullName = fullName;
    }

    public static Person parse(String personString) {
        String fullName = personString.substring(0, personString.indexOf(','));
        int age = Integer.parseInt(personString.substring(personString.indexOf(',') + 1));
        return new Person(age, fullName);
    }

    public int getAge() {
        return age;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return fullName + " (" + age + ")";
    }
}
