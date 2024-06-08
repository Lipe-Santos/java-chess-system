package application;

import application.utils.Utils;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Exception.ChessException;

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
                String userInput = Utils.readString(sc);
                char column = Utils.readColumn(userInput);
                int row = Utils.readRow(userInput);
                chessPosition = new ChessPosition(column, row);
            } catch (RuntimeException err) {
                System.out.println(err.getMessage());
            } finally {
                System.out.println();
            }
        } while (chessPosition == null);
        return chessPosition;
    }
}
