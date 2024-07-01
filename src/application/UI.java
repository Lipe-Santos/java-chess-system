package application;

import boardGame.Piece;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {
    //text colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\u001B[37m";
    //background colors
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

    public static String printMatch(ChessMatch chessMatch, ArrayList<ChessPiece> capturedPieces) {
        return printBoard(chessMatch.pieces()) +
                "\n\n" +
                printCapturedPieces(capturedPieces) +
                "\n\nTurn: " +
                chessMatch.getTurn()  +
                "\n" +
                (chessMatch.isCheckMate() ?
                        "CHECKMATE!\nWinner " + chessMatch.getCurrentPlayer():
                        "Waiting player: " + chessMatch.getCurrentPlayer() +
                                (chessMatch.isCheck() ? "\nCHECK!" : ""));
    }

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

    private static String printCapturedPieces(ArrayList<ChessPiece> captured) {
        String capturedPieces = "Captured pieces: ";
        List<ChessPiece> whites =  captured.stream().filter(piece -> piece.getColor() == Color.WHITE).toList();
        List<ChessPiece> blacks =  captured.stream().filter(piece -> piece.getColor() == Color.BLACK).toList();
        capturedPieces += "\nWhite: " + ANSI_WHITE + whites + ANSI_RESET + "\n" + ANSI_YELLOW + "Black: " +  blacks + ANSI_RESET;
        return capturedPieces;
    }
}
