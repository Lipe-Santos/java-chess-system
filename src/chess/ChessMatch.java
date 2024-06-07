package chess;

import boardGame.Board;
import boardGame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
    //This class will have the rules of the game
    private static final int boardDimensions = 8;
    private final Board board;
    public ChessMatch() {
        board = new Board(ChessMatch.boardDimensions, ChessMatch.boardDimensions);
        //When a new game is created, I have to distribute the pieces
        initialSetup();
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
    private void initialSetup() {
        board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
        board.placePiece(new King(board, Color.BLACK), new Position(7, 4));
        board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
    }
}
