package org.example;

import java.util.Scanner;

public class PuzzleInput {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to define the puzzle setup, including random moves or selecting a specific board.
     *
     * @return An array where the first element represents the number of moves and the second the board type.
     */
    public static int[] getManualMoves() {
        int[] result = new int[2];
        System.out.println("If you want to generate 5 or 50 random puzzles, enter -1.");
        System.out.println("Enter the number of moves to randomize the goal puzzle, or enter 0 if you have a specific board: ");
        result[0] = scanner.nextInt();

        if (result[0] == 0) {
            System.out.println("Enter the board type (15 or 24): ");
            result[1] = scanner.nextInt();
        }
        return result;
    }

    /**
     * Collects user input to manually define a 15-puzzle configuration.
     *
     * @return A 2D array representing the board configuration.
     */
    public static int[][] getManualInput15() {
        int[][] board = new int[4][4];
        System.out.println("Enter the initial board for Puzzle 15 (enter 0 for the empty tile):");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.printf("Enter value for tile at position (%d, %d): ", i, j);
                board[i][j] = scanner.nextInt();
            }
        }
        return board;
    }

    /**
     * Collects user input to manually define a 24-puzzle configuration.
     *
     * @return A 2D array representing the board configuration.
     */
    public static int[][] getManualInput24() {
        int[][] board = new int[5][5];
        System.out.println("Enter the initial board for Puzzle 24 (enter 0 for the empty tile):");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.printf("Enter value for tile at position (%d, %d): ", i, j);
                board[i][j] = scanner.nextInt();
            }
        }
        return board;
    }
}
