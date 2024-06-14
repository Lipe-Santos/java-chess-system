package boardGame;

public class Position {
    private int row;
    private int column;
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }
    public Position(Position position) {
        this(position.row, position.column);
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public int getColumn() {
        return column;
    }
    public void setPosition(int row, int column) {
        setRow(row);
        setColumn(column);
    }
    public void setPosition(Position position) {
        setPosition(position.row, position.column);
    }
    @Override
    public String toString() {
        return "Position: " + row + ", " + column;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj.getClass() != this.getClass()) return false;
        Position position = (Position) obj;
        return position.row == this.row && position.column == column;
    }
}
