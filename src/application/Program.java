package application;

import application.utils.Utils;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Exception.ChessException;

import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        startMatch();
    }

    public static void startMatch() {
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        while (!chessMatch.isCheckMate()) {
            System.out.println(UI.printMatch(chessMatch, chessMatch.getCapturedPieces()));
            performChessMove(chessMatch, sc);
        }
        System.out.println(UI.printMatch(chessMatch, chessMatch.getCapturedPieces()));
    }

    public static void performChessMove(ChessMatch chessMatch, Scanner sc) {
        boolean flag = true;
        do {
            System.out.println();
            ChessPosition source = readChessPosition(sc, "source: ");
            try {
                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                System.out.println(UI.printBoard(chessMatch.pieces(), possibleMoves));
                ChessPosition target = readChessPosition(sc, "target: ");
                chessMatch.performChessMove(source, target);
                flag = false;
            } catch (ChessException err) {
                System.out.println(err.getMessage());
            }
        } while (flag);
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
