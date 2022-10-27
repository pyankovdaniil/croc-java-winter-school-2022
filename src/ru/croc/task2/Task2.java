package ru.croc.task2;

import ru.croc.task2.util.ArithmeticProgressionAccumulator;

public class Task2 {
    public static void main(String[] args) {
        try {
            if (args.length != 3) {
                throw new IllegalArgumentException("Number of arguments should be = 3");
            }

            double firstMember = Double.parseDouble(args[0]);
            double difference = Double.parseDouble(args[1]);
            int numberOfMembers = Integer.parseInt(args[2]);

            double sum = ArithmeticProgressionAccumulator.accumulate(firstMember, difference, numberOfMembers);

            String sumString = String.valueOf(sum);
            System.out.print("Sum: ");
            if (sumString.substring(sumString.length() - 2).equals(".0")) {
                System.out.println(sumString.substring(0, sumString.length() - 2));
            } else {
                System.out.println(sum);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
