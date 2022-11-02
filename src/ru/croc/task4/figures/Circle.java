package ru.croc.task4.figures;

public class Circle extends Figure {
    private final Point centerPoint;
    private final double radius;

    public Circle(Point centerPoint, double radius) throws IllegalArgumentException {
        if (Double.compare(radius, 0.0) <= 0) {
            throw new IllegalArgumentException("Radius of the circle should be > 0!");
        }

        this.centerPoint = centerPoint;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle (" + centerPoint.getX() + ", " + centerPoint.getY() +
                "), " + radius;
    }
}
