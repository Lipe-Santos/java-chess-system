package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
    private ChessMatch chessMatch;
    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }
    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[ChessMatch.BOARD_DIMENSIONS][ChessMatch.BOARD_DIMENSIONS];
        checkSurroundingSpaces(matrix);
        castling(matrix);
        return matrix;
    }

    private boolean testRookCastling(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }

    private void castling(boolean[][] matrix) {
        kingSideRook(matrix);
        queenSideRook(matrix);
    }

    private void kingSideRook(boolean[][] matrix) {
        if (getMoveCount() == 0 && !chessMatch.isCheck()) {
            Position position1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(position1)) {
                if (!getBoard().thereIsAPiece(position.getRow(), position.getColumn() + 1) && !getBoard().thereIsAPiece(position.getRow(), position.getColumn() + 2)) {
                    matrix[position.getRow()][position.getColumn() + 2] = true;
                }
            }
        }
    }

    private void queenSideRook(boolean[][] matrix) {
        if (getMoveCount() == 0 && !chessMatch.isCheck()) {
            Position position1 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(position1)) {
                if (!getBoard().thereIsAPiece(position.getRow(), position.getColumn() - 1) && !getBoard().thereIsAPiece(position.getRow(), position.getColumn() - 2) && !getBoard().thereIsAPiece(position.getRow(), position.getColumn() - 3)) {
                    matrix[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }
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
}
