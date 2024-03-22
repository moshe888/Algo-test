import java.util.Scanner;

public class PuzzleInput {
    public static Scanner scanner = new Scanner(System.in);
    public static int[] manualMoves()
    {
        int[] result = new int[2];
        System.out.println("If you want to generate 5 random puzzles and 50 random puzzles, Enter -1 or you can ");
        System.out.println("Enter the number of moves if you want to random the goal puzzle, Enter 0 if you have a board: ");
        result[0] = scanner.nextInt();
        if (result[0] == 0)
        {
            System.out.println("Enter the board number 15 or 24: ");
            result[1] = scanner.nextInt();
        }
        return result;
        
    }
    public static int[][] manualInput15() {
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
    public static int[][] manualInput24() {
        int[][] board = new int[5][5];

        System.out.println("Enter the initial board for Puzzle 15 (enter 0 for the empty tile):");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.printf("Enter value for tile at position (%d, %d): ", i, j);
                board[i][j] = scanner.nextInt();
            }
        }
        return board;
    
    }
}