package ru.croc.task4.figures;

public class Rectangle extends Figure {
    private final Point leftDownPoint;
    private final Point rightUpPoint;

    public Rectangle(Point leftDownPoint, Point rightUpPoint) throws IllegalArgumentException {
        checkForCorrectPoints(leftDownPoint, rightUpPoint);

        this.leftDownPoint = leftDownPoint;
        this.rightUpPoint = rightUpPoint;
    }

    private void checkForCorrectPoints(Point leftDownPoint, Point rightUpPoint) {
        if (leftDownPoint.equals(rightUpPoint) ||
                !(Double.compare(leftDownPoint.getX(), rightUpPoint.getX()) < 0 &&
                        Double.compare(leftDownPoint.getY(), rightUpPoint.getY()) < 0)) {
            throw new IllegalArgumentException("Incorrect points for rectangle!");
        }
    }

    @Override
    public String toString() {
        return "Rectangle (" + leftDownPoint.getX() + ", " + leftDownPoint.getY() +
                "), (" + rightUpPoint.getX() + ", " + rightUpPoint.getY() + ")";
    }
}
