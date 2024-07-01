package chess.pieces;

import boardGame.Board;
import boardGame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[ChessMatch.BOARD_DIMENSIONS][ChessMatch.BOARD_DIMENSIONS];
        Position p = new Position(position);
        for (int i = 1; i <= (getMoveCount() == 0 ? 2 : 1); i++) {
            p.setRow(getColor() == Color.WHITE ? p.getRow() - 1: p.getRow() + 1);
            if (!getBoard().positionExists(p)) {
                break;
            }

            if (!getBoard().thereIsAPiece(p)) {
                matrix[p.getRow()][p.getColumn()] = true;
            } else if (isThereOpponentPiece(p)) {
                matrix[p.getRow()][p.getColumn()] = true;
                break;
            }

            if (i == 1) {
                if (getBoard().positionExists(p.getRow(), p.getColumn() - 1) && isThereOpponentPiece(p.getRow(), p.getColumn() - 1)) {
                    matrix[p.getRow()][p.getColumn() - 1] = true;
                }
                if (getBoard().positionExists(p.getRow(), p.getColumn() + 1) && isThereOpponentPiece(p.getRow(), p.getColumn() + 1)) {
                    matrix[p.getRow()][p.getColumn() + 1] = true;
                }
            }
        }

        return  matrix;
    }

    @Override
    public String toString() {
        return "P";
    }
}
