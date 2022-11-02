package ru.croc.task5.figures;

public class UtilFiguresOperations {
    private UtilFiguresOperations() {
    }

    public static double calculateDistance(Point first, Point second) {
        return Math.sqrt(Math.pow((first.getX() - second.getX()), 2) +
                Math.pow((first.getY() - second.getY()), 2));
    }
}
