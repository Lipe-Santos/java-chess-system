package chess;

import boardGame.Position;
import chess.Exception.ChessException;

public class ChessPosition {
    //It will obey the chess coordinates
    private final char column;
    private final int row;
    public ChessPosition(char column, int row) {
        if (!rowIsValid(row)) {
            throw new ChessException("Error instantiating chessPosition. Row must be between 1 - " + ChessMatch.BOARD_DIMENSIONS);
        }

        if (!columnIsValid(column)) {
            throw new ChessException("Error instantiating chessPosition. Column must be between a - " + (char) ('a' + ChessMatch.BOARD_DIMENSIONS));
        }

        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public char getColumn() {
        return column;
    }

    protected Position toPosition() {
        int row = toMatrixRow();
        int column = toMatrixColumn();
        return new Position(row, column);
    }

    protected static ChessPosition fromPosition(Position position) {
        int row = toChessRow(position.getRow());
        char column = toChessColumn(position.getColumn());
        return new ChessPosition(column, row);
    }

    private int toMatrixRow() {
        return ChessMatch.BOARD_DIMENSIONS - row;
    }

    private int toMatrixColumn() {
        return Character.isUpperCase(column) ? column - 'A' : column - 'a';
    }

    private static int toChessRow(int row) {
        return ChessMatch.BOARD_DIMENSIONS - row;
    }

    private static char toChessColumn(int column) {
        return (char) ('a' + column);
    }

    private boolean rowIsValid(int row) {
        return row > 0 && row <= ChessMatch.BOARD_DIMENSIONS;
    }

    private boolean columnIsValid(int column) {
        return column >= 'a' && column <= 'a' + ChessMatch.BOARD_DIMENSIONS || column >= 'A' && column <= 'A' + ChessMatch.BOARD_DIMENSIONS;
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}
