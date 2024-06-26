package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
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
        return matrix;
    }
}
