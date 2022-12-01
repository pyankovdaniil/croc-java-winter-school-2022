package ru.croc.task15.comparators;

import ru.croc.task15.ageutil.AgeGroup;

import java.util.Comparator;

public class AgeGroupDescendingComparator implements Comparator<AgeGroup> {
    @Override
    public int compare(AgeGroup group1, AgeGroup group2) {
        return group2.lowerBound() - group1.lowerBound();
    }
}
