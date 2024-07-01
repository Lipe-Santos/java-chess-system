package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;

public abstract class ChessPiece extends Piece {
    private final Color color;
    private int moveCount;
    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }
    public int getMoveCount() {
        return moveCount;
    }
    public Color getColor() {
        return color;
    }
    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(this.position);
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
    public void checkDirection(boolean[][] matrix, int rowIncrement, int colIncrement) {
        Position p = new Position(this.position.getRow() + rowIncrement, this.position.getColumn() + colIncrement);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() + colIncrement);
            p.setRow(p.getRow() + rowIncrement);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }
    }

    public boolean verifyPosition(Position position) {
        return getBoard().positionExists(position) && (isThereOpponentPiece(position) || !getBoard().thereIsAPiece(position));
    }

    public void increaseMoveCount() {
        moveCount++;
    }
    public void decreaseMoveCount() {
        moveCount--;
    }
}
