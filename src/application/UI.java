package application;

import boardGame.Piece;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Scanner;

public class UI {
    //text colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    //background colors
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";


    public static String printBoard(ChessPiece[][] pieces) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pieces.length; i++) {
            sb.append(pieces.length - i).append(" ");
            for (int j = 0; j < pieces[0].length; j++) {
                sb.append(getPiece(pieces[i][j], false)).append(" ");
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

    public static String printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pieces.length; i++) {
            sb.append(pieces.length - i).append(" ");
            for (int j = 0; j < pieces[0].length; j++) {
                sb.append(getPiece(pieces[i][j], possibleMoves[i][j])).append(" ");
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
    private static String getPiece(ChessPiece piece, boolean background) {
        String s = "";
        if (background) {
            s += ANSI_BLUE_BACKGROUND;
        }
        if (piece != null) {
            //I'll use yellow instead of black for the terminal background
            s += piece.getColor() == Color.BLACK ? ANSI_YELLOW + piece : ANSI_WHITE + piece;
        } else {
            s += "-" ;
        }
        s += ANSI_RESET;
        return s;
    }
}
