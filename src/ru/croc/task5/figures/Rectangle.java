package ru.croc.task5.figures;

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

    @Override
    public void move(int dx, int dy) {
        leftDownPoint.move(dx, dy);
        rightUpPoint.move(dx, dy);
    }

    @Override
    public boolean contains(Point point) {
        return Double.compare(point.getX(), leftDownPoint.getX()) >= 0 &&
                Double.compare(point.getX(), rightUpPoint.getX()) <= 0 &&
                Double.compare(point.getY(), leftDownPoint.getY()) >= 0 &&
                Double.compare(point.getY(), rightUpPoint.getY()) <= 0;
    }
}
