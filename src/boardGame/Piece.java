package boardGame;

public abstract class Piece {
    protected Position position;
    private final Board board;
    public Piece(Board board) {
        //when a piece is created, it starts out as null
        this.board = board;
    }
    protected Board getBoard() {
        //I don't want the board to be accessible from the chess layer, this is an internal use of the board layer
        return board;
    }
    //It will return a Boolean array with the places the piece can move to
    public abstract boolean[][] possibleMoves();
    public boolean canMove(Position position) {
        //Hook method, a concrete method is using an abstract method
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    public boolean isThereAnyPossibleMove() {
        boolean[][] matrix = possibleMoves();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j]) return true;
            }
        }
        return false;
    }
}
