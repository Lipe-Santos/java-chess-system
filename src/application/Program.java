package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Exception.ChessException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        startMatch();
    }

    public static void startMatch() {
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        while (true) {
            UI.clearScreen();
            System.out.println(UI.printBoard(chessMatch.pieces()));
            ChessPiece captured = performChessMove(chessMatch, sc);
        }
    }

    public static ChessPiece performChessMove(ChessMatch chessMatch, Scanner sc) {
        do {
            System.out.println();
            ChessPosition source = readChessPosition(sc, "source: ");
            ChessPosition target = readChessPosition(sc, "target: ");
            try {
                return chessMatch.performChessMove(source, target);
            } catch (ChessException err) {
                System.out.println(err.getMessage());
            }
        } while (true);
    }

    public static ChessPosition readChessPosition(Scanner sc, String label) {
        ChessPosition chessPosition = null;
        do {
        System.out.print(label);
            try {
                String userInput = readString(sc);
                char column = readColumn(userInput);
                int row = readRow(userInput);
                chessPosition = new ChessPosition(column, row);
            } catch (RuntimeException err) {
                System.out.println(err.getMessage());
            }
        } while (chessPosition == null);
        System.out.println();
        return chessPosition;
    }

    public static char readColumn(String userInput) {
        return userInput.charAt(0);
    }

    public static int readRow(String userInput) {
        if (!Character.isDigit(userInput.charAt(1))) {
            throw new InputMismatchException("Error row must be a number between 1 - " + ChessMatch.BOARD_DIMENSIONS);
        }
        return Integer.parseInt(userInput.substring(1));
    }

   public static String readString(Scanner sc) {
        String userInput = sc.nextLine();
        if (userInput.length() != 2) {
            throw new InputMismatchException("Error reading ChessPosition. Valid values from a1 to h8");
        }
        return userInput;
   }
}
