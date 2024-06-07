package application;

import chess.ChessMatch;

public class Program {
    public static void main(String[] args) {
        ChessMatch chessMatch = new ChessMatch();
        System.out.println(UI.printBoard(chessMatch.pieces()));
    }
}
