package chess;

import boardGame.Board;

public class ChessMatch {
    //This class will have the rules of the game
    private static final int boardDimensions = 8;
    private final Board board;
    public ChessMatch() {
        board = new Board(ChessMatch.boardDimensions, ChessMatch.boardDimensions);
    }
    public ChessPiece[][] pieces() {
        ChessPiece[][] chessPieces = new ChessPiece[ChessMatch.boardDimensions][ChessMatch.boardDimensions];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                chessPieces[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return chessPieces;
    }
}
