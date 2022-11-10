package ru.croc.task7.util;

import ru.croc.task7.exceptions.IllegalPositionException;

import java.util.Objects;

public class ChessboardPosition {
    private char x;
    private char y;

    public ChessboardPosition(char x, char y) {
        if (!(checkXCoordinate(x) && checkYCoordinate(y))) {
            throw new IllegalPositionException(x, y);
        }
        this.x = x;
        this.y = y;
    }

    public static ChessboardPosition parse(String position) {
        if (position.length() != 2) {
            throw new IllegalPositionException(position);
        }
        return new ChessboardPosition(position.charAt(0), position.charAt(1));
    }

    private boolean checkXCoordinate(char x) {
        return Character.compare(x, 'a') >= 0 &&
                Character.compare(x, 'h') <= 0;
    }

    private boolean checkYCoordinate(char y) {
        return Character.compare(y, '1') >= 0 &&
                Character.compare(y, '8') <= 0;
    }

    public char getX() {
        return x;
    }

    public int getRawIntX() {
        return (int) x - 97;
    }

    public void setX(char x) {
        if (!checkXCoordinate(x)) {
            throw new IllegalPositionException("x", x);
        }
        this.x = x;
    }

    public char getY() {
        return y;
    }

    public int getRawIntY() {
        return (int) y - 49;
    }

    public void setY(char y) {
        if (!checkYCoordinate(y)) {
            throw new IllegalPositionException("y", y);
        }
        this.y = y;
    }

    @Override
    public String toString() {
        return String.valueOf(x) + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessboardPosition position)) return false;
        return getX() == position.getX() && getY() == position.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
