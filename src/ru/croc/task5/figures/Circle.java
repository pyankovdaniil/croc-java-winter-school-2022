package ru.croc.task5.figures;

public class Circle extends Figure {
    private final Point centerPoint;
    private final double radius;

    public Circle(Point centerPoint, double radius) throws IllegalArgumentException {
        if (Double.compare(radius, 0.0) <= 0) {
            throw new IllegalArgumentException("Radius should be > 0!");
        }

        this.centerPoint = centerPoint;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle (" + centerPoint.getX() + ", " + centerPoint.getY() +
                "), " + radius;
    }

    @Override
    public void move(int dx, int dy) {
        centerPoint.move(dx, dy);
    }

    @Override
    public boolean contains(Point point) {
        return Double.compare(UtilFiguresOperations.calculateDistance(centerPoint,
                point), radius) <= 0;
    }
}
