package boardGame;

public abstract class Piece {
    protected Position position;
    private Board board;
    public Piece(Board board) {
        //when a piece is created, it starts out as null
        this.board = board;
    }
    protected Board getBoard() {
        //I don't want the board to be accessible from the chess layer, this is an internal use of the board layer
        return board;
    }
}
