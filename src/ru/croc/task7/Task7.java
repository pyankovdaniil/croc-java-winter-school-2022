package ru.croc.task7;

import ru.croc.task7.exceptions.IllegalMoveException;
import ru.croc.task7.exceptions.IllegalPositionException;
import ru.croc.task7.util.ChessboardMovesChecker;
import ru.croc.task7.util.ChessboardPosition;
import ru.croc.task7.util.FigureMovesChecker;
import ru.croc.task7.util.KnightMoveChecker;

public class Task7 {
    public static void main(String[] args) {
        try {
            ChessboardPosition[] positions = new ChessboardPosition[args.length];
            for (int i = 0; i < args.length; i++) {
                positions[i] = ChessboardPosition.parse(args[i]);
            }
            FigureMovesChecker knightMoveChecker = new KnightMoveChecker();
            ChessboardMovesChecker.checkMoves(positions, knightMoveChecker);
            System.out.println("OK");
        } catch (IllegalPositionException | IllegalMoveException e) {
            System.out.println(e.getMessage());
        }
    }
}
