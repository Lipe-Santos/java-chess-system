package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;

public abstract class ChessPiece extends Piece {
    private final Color color;
    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }
    public Color getColor() {
        return color;
    }
    //I don't want to allow the color of the piece to be changed. For that reason there is no set for color
    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece chessPiece = (ChessPiece) getBoard().piece(position);
        return chessPiece != null && chessPiece.color != color;
    }
    protected boolean isThereOpponentPiece(int row, int col) {
        ChessPiece chessPiece = (ChessPiece) getBoard().piece(row, col);
        return chessPiece != null && chessPiece.color != color;
    }
}
