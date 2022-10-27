package ru.croc.task3.util;

public class Triangle extends Shape {
    private final Point[] points = new Point[3];

    public Triangle(Point p1, Point p2, Point p3) {
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        checkCorrectCoordinates(p1, p2, p3);
    }

    private void checkCorrectCoordinates(Point p1, Point p2, Point p3) throws IllegalArgumentException {
        // Check if got some equal points
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3)) {
            throw new IllegalArgumentException("Can not build a triangle on these points (got equal points)!");
        }

        // Check if all points are on the same line (it wil be a line, not a triangle)
        double line12 = (points[2].getX() - points[0].getX()) * (points[1].getY() - points[0].getY()) -
                (points[1].getX() - points[0].getX()) * (points[2].getY() - points[0].getY());
        double line13 = (points[1].getX() - points[0].getX()) * (points[2].getY() - points[0].getY()) -
                (points[2].getX() - points[0].getX()) * (points[1].getY() - points[0].getY());
        double line23 = (points[0].getX() - points[1].getX()) * (points[2].getY() - points[1].getY()) -
                (points[2].getX() - points[1].getX()) * (points[0].getY() - points[1].getY());

        if (Double.compare(line12, 0.0) == 0 || Double.compare(line13, 0.0) == 0 || Double.compare(line23, 0.0) == 0) {
            throw new IllegalArgumentException("Can not build a triangle on these points " +
                    "(points are on the same line)!");
        }
    }

    @Override
    public double getArea() {
        double side1 = Math.sqrt(Math.pow((points[0].getX() - points[1].getX()), 2) +
                Math.pow((points[0].getY() - points[1].getY()), 2));
        double side2 = Math.sqrt(Math.pow((points[0].getX() - points[2].getX()), 2) +
                Math.pow((points[0].getY() - points[2].getY()), 2));
        double side3 = Math.sqrt(Math.pow((points[1].getX() - points[2].getX()), 2) +
                Math.pow((points[1].getY() - points[2].getY()), 2));

        double halfOfPerimeter = (side1 + side2 + side3) / 2.0;

        return Math.sqrt(halfOfPerimeter * (halfOfPerimeter - side1) * (halfOfPerimeter - side2) *
                (halfOfPerimeter - side3));
    }
}
