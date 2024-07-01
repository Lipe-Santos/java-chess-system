package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {
    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[ChessMatch.BOARD_DIMENSIONS][ChessMatch.BOARD_DIMENSIONS];
        //top right
        checkDirection(matrix, -1, 1);
        //top left
        checkDirection(matrix, -1, -1);
        //bottom right
        checkDirection(matrix, 1, 1);
        //bottom left
        checkDirection(matrix, 1, -1);
        return matrix;
    }
    @Override
    public String toString() {
        return "B";
    }
}
