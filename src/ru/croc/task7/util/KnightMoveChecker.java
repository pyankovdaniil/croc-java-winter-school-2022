package ru.croc.task7.util;

public class KnightMoveChecker implements FigureMovesChecker {
    public boolean canMove(ChessboardPosition pos1, ChessboardPosition pos2) {
        if (pos1.equals(pos2)) {
            return true;
        }

        int xDiff = Math.abs(pos1.getRawIntX() - pos2.getRawIntX());
        int yDiff = Math.abs(pos1.getRawIntY() - pos2.getRawIntY());

        return (xDiff == 2 && yDiff == 1) || (xDiff == 1 && yDiff == 2);
    }
}
