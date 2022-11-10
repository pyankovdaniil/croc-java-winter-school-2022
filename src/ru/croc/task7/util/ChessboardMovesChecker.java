package ru.croc.task7.util;

import ru.croc.task7.exceptions.IllegalMoveException;

public class ChessboardMovesChecker {
    private ChessboardMovesChecker() {
    }

    public static void checkMoves(ChessboardPosition[] positions,
                                  FigureMovesChecker movesChecker) throws IllegalMoveException {
        for (int i = 0; i < positions.length - 1; i++) {
            if (!movesChecker.canMove(positions[i], positions[i + 1])) {
                throw new IllegalMoveException(positions[i].toString(),
                        positions[i + 1].toString());
            }
        }
    }
}
