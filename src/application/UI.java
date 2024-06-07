package application;

import chess.ChessPiece;

public class UI {
    public static String printBoard(ChessPiece[][] pieces) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pieces.length; i++) {
            sb.append(pieces.length - i).append(" ");
            for (int j = 0; j < pieces[0].length; j++) {
                sb.append(pieces[i][j] != null ? pieces[i][j] : "-").append(" ");
            }
            sb.append("\n");
        }
        sb.append("  ");
        //print the letters on the board
        for (int i = 0; i < pieces.length; i++) {
            sb.append((char) ('a' + i)).append(" ");
        }
        return sb.toString();
    }
}
