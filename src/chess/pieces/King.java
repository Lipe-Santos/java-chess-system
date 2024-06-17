package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }
    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[ChessMatch.BOARD_DIMENSIONS][ChessMatch.BOARD_DIMENSIONS];
        checkSurroundingSpaces(matrix);
        return matrix;
    }

    private void checkSurroundingSpaces(boolean[][] matrix) {
        Position initialPosition = new Position(position);

        //Represents the 8 directions
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            initialPosition.setRow(position.getRow() + rowOffsets[i]);
            initialPosition.setColumn(position.getColumn() + colOffsets[i]);
            if (verifyPosition(initialPosition)) {
                matrix[initialPosition.getRow()][initialPosition.getColumn()] = true;
            }
        }
    }

    private boolean verifyPosition(Position position) {
        return getBoard().positionExists(position) && (isThereOpponentPiece(position) || !getBoard().thereIsAPiece(position));
    }
}
