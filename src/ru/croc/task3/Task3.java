package ru.croc.task3;

import ru.croc.task3.util.Point;
import ru.croc.task3.util.Shape;
import ru.croc.task3.util.Triangle;

public class Task3 {
    public static void main(String[] args) {
        try {
            if (args.length != 6) {
                throw new IllegalArgumentException("Number of arguments should be = 6");
            }

            double[] coordinates = new double[6];
            for (int i = 0; i < 6; i++) {
                coordinates[i] = Double.parseDouble(args[i]);
            }

            Point[] points = {new Point(coordinates[0], coordinates[1]), new Point(coordinates[2], coordinates[3]),
                    new Point(coordinates[4], coordinates[5])};
            Shape triangle = new Triangle(points[0], points[1], points[2]);

            double scale = Math.pow(10, 4);
            double area = Math.round(triangle.getArea() * scale) / scale;

            String areaString = String.valueOf(area);
            System.out.print("Area of the triangle: ");
            if (areaString.substring(areaString.length() - 2).equals(".0")) {
                System.out.println(areaString.substring(0, areaString.length() - 2));
            } else {
                System.out.println(area);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
