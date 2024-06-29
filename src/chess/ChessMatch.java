package chess;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.Exception.ChessException;
import chess.pieces.King;
import chess.pieces.Rook;

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
        board.placePiece(piece, target);
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
        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add((ChessPiece) capturedPiece);
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
        placeNewPiece('a', 8, new King(board, Color.BLACK));
        placeNewPiece('b', 8, new Rook(board, Color.BLACK));

        placeNewPiece('b', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));
        placeNewPiece('h', 7, new Rook(board, Color.WHITE));
    }
}
