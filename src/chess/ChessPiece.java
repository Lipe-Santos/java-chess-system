package chess;

import boardGame.Board;
import boardGame.Piece;

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
}
