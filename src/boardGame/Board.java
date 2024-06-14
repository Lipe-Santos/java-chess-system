package boardGame;

import boardGame.exception.BoardException;

public class Board {
    private final int rows;
    private final int columns;
    private final Piece[][] pieces;
    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
        }

        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }
    public int getRows() {
        return rows;
    }
    public int getColumns() {
        return columns;
    }
    //It makes no sense to change the number of columns or rows once the board has been created
    public Piece piece(int row, int column) {
        if (!positionExists(row, column)) {
            throw new BoardException("Position not on the board");
        }
        return pieces[row][column];
    }
    public Piece piece(Position position) {
        return piece(position.getRow(), position.getColumn());
    }
    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a piece on position: " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }
    public Piece removePiece(Position position) {
        if (!thereIsAPiece(position)) return null;
        Piece removed = piece(position);
        pieces[position.getRow()][position.getColumn()] = null;
        return removed;
    }
    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }
    public boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }
    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }
        return piece(position) != null;
    }

    public boolean thereIsAPiece(int row, int col) {
        if (!positionExists(row, col)) {
            throw new BoardException("Position not on the board");
        }
        return piece(row, col) != null;
    }
}
