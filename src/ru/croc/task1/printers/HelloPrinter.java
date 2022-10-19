package ru.croc.task1.printers;

public class HelloPrinter {
    private HelloPrinter() {
    }

    public static void printHello(String nameToSayHello) {
        System.out.println("Hello, " + nameToSayHello + " ^^");
    }
}
