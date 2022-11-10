package ru.croc.task7.exceptions;

public class IllegalMoveException extends Exception {
    public IllegalMoveException(String position1, String position2) {
        super("Knight can not move this way: " + position1 + " -> " + position2);
    }
}
