package chess.pieces;

import boardGame.Board;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {
    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[ChessMatch.BOARD_DIMENSIONS][ChessMatch.BOARD_DIMENSIONS];
        //above
        checkDirection(matrix, -1, 0);
        //right
        checkDirection(matrix, 0, 1);
        //left
        checkDirection(matrix, 0, -1);
        //below
        checkDirection(matrix, 1, 0);
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
        return "Q";
    }
}
