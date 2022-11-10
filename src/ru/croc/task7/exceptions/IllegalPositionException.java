package ru.croc.task7.exceptions;

public class IllegalPositionException extends RuntimeException {
    public IllegalPositionException(char x, char y) {
        super("Incorrect chessboard position: (" + x + ";" + y + ")");
    }

    public IllegalPositionException(String coordName, char value) {
        super("Incorrect coordinate for " + coordName + ": " + value);
    }

    public IllegalPositionException(String position) {
        super("Can not from position for input string: " + position);
    }
}
