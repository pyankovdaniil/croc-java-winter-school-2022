package ru.croc.task2.util;

public class ArithmeticProgressionAccumulator {
    private ArithmeticProgressionAccumulator() {
    }

    /**
     * Finds a sum of the arithmetic progression with given parameters
     * @param firstMember value of first member in progression
     * @param difference difference between next and previous members
     * @param numberOfMembers number of members to sum
     * @return sum of the arithmetic progression with given parameters
     * @throws IllegalArgumentException if number of members < 0
     */
    public static double accumulate(double firstMember, double difference, int numberOfMembers)
            throws IllegalArgumentException {
        if (numberOfMembers < 0) {
            throw new IllegalArgumentException("Number of members in progression should be >= 0!");
        }
        if (numberOfMembers == 0) {
            return 0;
        }
        double sum = firstMember;
        double currentMember = firstMember;
        for (int i = 0; i < numberOfMembers - 1; i++) {
            sum += currentMember + difference;
            currentMember += difference;
        }
        return sum;
    }
}
