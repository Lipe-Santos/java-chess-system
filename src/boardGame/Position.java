package boardGame;

public class Position {
    private int row;
    private int column;
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    //I will not allow the position to be freely changed
    @Override
    public String toString() {
        return "Position: " + row + ", " + column;
    }
}
