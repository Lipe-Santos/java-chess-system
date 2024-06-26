package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.Exception.ChessException;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class ChessMatch {
    //This class will have the rules of the game
    private int turn;
    private Color currentPlayer;
    private final ArrayList<ChessPiece> piecesOnTheBoard = new ArrayList<>();
    private final ArrayList<ChessPiece> capturedPieces = new ArrayList<>();
    private boolean checkMate;
    private boolean check;
    public static final int BOARD_DIMENSIONS = 8;
    private final Board board;
    public ChessMatch() {
        board = new Board(ChessMatch.BOARD_DIMENSIONS, ChessMatch.BOARD_DIMENSIONS);
        turn = 1;
        currentPlayer = Color.WHITE;
        //When a new game is created, I have to distribute the pieces
        initialSetup();
    }
    public ArrayList<ChessPiece> getCapturedPieces() {
        return (ArrayList<ChessPiece>) capturedPieces.clone();
    }
    public int getTurn() {
        return turn;
    }
    public Color getCurrentPlayer() {
        return currentPlayer;
    }
    public boolean isCheck() {
        return check;
    }
    public boolean isCheckMate() {
        return checkMate;
    }
    public void nextTurn() {
        turn++;
        currentPlayer = currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
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
        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }
        check = testCheck(opponent(currentPlayer));
        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece piece = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        if (capturedPiece != null) {
            capturedPieces.add((ChessPiece) capturedPiece);
            piecesOnTheBoard.remove(capturedPiece);
        }
        ((ChessPiece) piece).increaseMoveCount();
        board.placePiece(piece, target);
        if (piece instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position rookSourcePosition = new Position(source.getRow(), source.getColumn() + 3);
            Position rookTargetPosition = new Position(source.getRow(), source.getColumn() + 1);
            makeMove(rookSourcePosition, rookTargetPosition);
        }

        if (piece instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position rookSourcePosition = new Position(source.getRow(), source.getColumn() - 4);
            Position rookTargetPosition = new Position(source.getRow(), source.getColumn() - 1);
            makeMove(rookSourcePosition, rookTargetPosition);
        }
        return capturedPiece;
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position");
        }
        if (!playerTurn(position)) {
            throw new ChessException("The chosen piece is not yours");
        }
        if(!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        Piece p = board.removePiece(target);
        board.placePiece(p, source);
        ((ChessPiece) p).decreaseMoveCount();
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add((ChessPiece) capturedPiece);
        }

        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position rookSourcePosition = new Position(source.getRow(), source.getColumn() + 3);
            Position rookTargetPosition = new Position(source.getRow(), source.getColumn() + 1);
            undoMove(rookTargetPosition, rookSourcePosition, null);
        }

        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position rookSourcePosition = new Position(source.getRow(), source.getColumn() - 4);
            Position rookTargetPosition = new Position(source.getRow(), source.getColumn() - 1);
            undoMove(rookTargetPosition, rookSourcePosition, null);
        }
    }

    private boolean playerTurn(Position position) {
        return ((ChessPiece) board.piece(position)).getColor() == currentPlayer;
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).canMove(target)) {
            throw new ChessException("The chosen piece can't move to the target position");
        }
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        Position position = new ChessPosition(column, row).toPosition();
        board.placePiece(piece, position);
        piecesOnTheBoard.add(piece);
    }

    private Color opponent(Color color) {
        return color == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color) {
        ChessPiece king = piecesOnTheBoard.stream().filter(piece -> piece.getColor() == color && piece instanceof King).findFirst().orElse(null);
        if (king == null) {
            throw new IllegalArgumentException("There is no " + color + " king on the board");
        }
        return king;
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<ChessPiece> opponentPieces = piecesOnTheBoard.stream().filter(piece -> piece.getColor() == opponent(color)).toList();
        for (ChessPiece piece : opponentPieces) {
            boolean[][] mat = piece.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()])  return true;
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) return false;
        List<ChessPiece> pieces = piecesOnTheBoard.stream().filter(piece -> piece.getColor() == color).toList();
        for (ChessPiece piece : pieces) {
            boolean[][] matrix = piece.possibleMoves();
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j]) {
                        Position source = piece.getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) return false;
                    }
                }
            }
        }
        return true;
    }

    private void initialSetup() {
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));

        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));
    }
}
