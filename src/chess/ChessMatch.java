package chess;

import boardGame.Board;
import boardGame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
    //This class will have the rules of the game
    public static final int BOARD_DIMENSIONS = 8;
    private final Board board;
    public ChessMatch() {
        board = new Board(ChessMatch.BOARD_DIMENSIONS, ChessMatch.BOARD_DIMENSIONS);
        //When a new game is created, I have to distribute the pieces
        initialSetup();
    }
    public ChessPiece[][] pieces() {
        ChessPiece[][] chessPieces = new ChessPiece[ChessMatch.BOARD_DIMENSIONS][ChessMatch.BOARD_DIMENSIONS];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                chessPieces[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return chessPieces;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        Position position = new ChessPosition(column, row).toPosition();
        board.placePiece(piece, position);
    }

    private void initialSetup() {
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
    }
}
