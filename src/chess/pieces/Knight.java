package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {
    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[ChessMatch.BOARD_DIMENSIONS][ChessMatch.BOARD_DIMENSIONS];
        checkKnightMoves(matrix);
        return matrix;
    }

    private void checkKnightMoves(boolean[][] matrix) {
        Position p = new Position(position);
        int[][] possibleMoves = {{2, 1}, {2, -1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {1, -2}, {-1, -2}};
        for (int[] move : possibleMoves) {
            p.setRow(position.getRow() + move[0]);
            p.setColumn(position.getColumn() + move[1]);
            if (verifyPosition(p)) {
                matrix[p.getRow()][p.getColumn()] = true;
            }
        }
    }
    @Override
    public String toString() {
        return "N";
    }
}
