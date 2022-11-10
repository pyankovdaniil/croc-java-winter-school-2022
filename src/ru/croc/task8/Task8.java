package ru.croc.task8;

import ru.croc.task8.util.PriceLocalizer;

import java.util.Scanner;

public class Task8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter locale: ");
        String locale = scanner.nextLine();
        System.out.print("Enter a double: ");
        String stringValue = scanner.nextLine();

        try {
            PriceLocalizer priceLocalizer = new PriceLocalizer(stringValue, locale);
            System.out.println(priceLocalizer.getLocalizedPrice());

            priceLocalizer.setLocale("usa");
            priceLocalizer.setValue("23984729837");

            System.out.println(priceLocalizer.getLocalizedPrice());
        } catch (NumberFormatException e) {
            System.out.println("Can not convert string: \"" + stringValue
                    + "\" to double");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
