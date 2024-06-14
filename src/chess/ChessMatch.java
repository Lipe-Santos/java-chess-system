package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.Exception.ChessException;
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
    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        //can return a captured piece, if this is the case
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece piece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(piece, target);
        return capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position");
        }
        if(!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).canMove(target)) {
            throw new ChessException("The chosen piece can't move to the target position");
        }
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        Position position = new ChessPosition(column, row).toPosition();
        board.placePiece(piece, position);
    }

    private void initialSetup() {
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));

        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
    }
}
