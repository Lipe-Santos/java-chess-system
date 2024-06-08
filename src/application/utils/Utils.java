package application.utils;

import chess.ChessMatch;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {
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
