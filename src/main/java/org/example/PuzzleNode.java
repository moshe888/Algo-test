package org.example;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;

public class PuzzleNode {
    private static final int[][] GOAL_15 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
    private static final int[][] GOAL_24 = {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}, {16, 17, 18, 19, 20}, {21, 22, 23, 24, 0}};

    public enum Color {
        WHITE, GRAY, BLACK
    }

    private int[][] board;
    private static int length;
    private int emptyRow, emptyCol;

    private Color color;
    private int distance;
    private PuzzleNode predecessor;

    // A* attributes
    private int heuristic;
    private int estimatedDistanceToGoal; //אווירי h
    private int pathCostFromStart; // =1 g
    private int estimatedTotalCost; // h + g

    public PuzzleNode(int[][] boardConfiguration, int heuristicValue, int moves) {
        setLength(boardConfiguration.length);
        this.board = copyBoard(boardConfiguration);
        locateEmptyTile(moves);
        initializeSearchAttributes(heuristicValue);
    }

    private void locateEmptyTile(int moves) {
        if (moves == 0) {
            int[] emptyTilePosition = findEmptyTilePosition();
            if (isValidPosition(emptyTilePosition)) {
                emptyRow = emptyTilePosition[0];
                emptyCol = emptyTilePosition[1];
            }
        } else {
            shuffleBoard(moves);
        }
    }

    private boolean isValidPosition(int[] position) {
        return position[0] != -1 && position[1] != -1;
    }

    private void initializeSearchAttributes(int heuristicValue) {
        this.color = Color.WHITE;
        this.distance = Integer.MAX_VALUE;
        this.predecessor = null;
        this.heuristic = heuristicValue;
    }

    // Simplified method for determining the goal based on the board length
    public int[][] getGoal() {
        return getLength() == 4 ? GOAL_15 : GOAL_24;
    }

    // Getter and setter methods (organized and simplified)
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public int getEstimatedDistanceToGoal() {
        return estimatedDistanceToGoal;
    }

    public void setEstimatedDistanceToGoal(int estimatedDistanceToGoal) {
        this.estimatedDistanceToGoal = estimatedDistanceToGoal;
    }

    public int getPathCostFromStart() {
        return pathCostFromStart;
    }

    public void setPathCostFromStart(int pathCostFromStart) {
        this.pathCostFromStart = pathCostFromStart;
    }

    public int getEstimatedTotalCost() {
        return estimatedTotalCost;
    }

    public void setEstimatedTotalCost(int estimatedTotalCost) {
        this.estimatedTotalCost = estimatedTotalCost;
    }

    public PuzzleNode getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(PuzzleNode predecessor) {
        this.predecessor = predecessor;
    }

    public int[][] getBoardConfiguration() {
        return board;
    }

    public void setBoardConfiguration(int[][] boardConfiguration) {
        this.board = copyBoard(boardConfiguration);
    }

    // Length accessors
    public static void setLength(int len) {
        length = len;
    }

    public static int getLength() {
        return length;
    }

    // Neighbors generation with checks for duplicates

    public Queue<PuzzleNode> generateNeighbors(HashSet<int[][]> allBoards) { //
        Queue<PuzzleNode> neighbors = new ArrayDeque<>();
        int[] emptyTilePosition = findEmptyTilePosition();
        int emptyRow = emptyTilePosition[0];
        int emptyCol = emptyTilePosition[1];

        // Possible moves: up, down, left, right
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] move : moves) {
            int newRow = emptyRow + move[0];
            int newCol = emptyCol + move[1];

            if (isValidMove(newRow, newCol)) {
                int[][] newConfiguration = copyBoard(this.board);
                swapTiles(newConfiguration, emptyRow, emptyCol, newRow, newCol);

                if (!allBoards.contains(newConfiguration)) {
                    PuzzleNode neighbor = new PuzzleNode(newConfiguration, heuristic, 0);
                    neighbors.add(neighbor);
                    allBoards.add(newConfiguration);
                }
            }
        }
        return neighbors;
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < length && col >= 0 && col < length;
    }

    private int[] findEmptyTilePosition() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (board[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};  // Empty tile not found
    }

    private void swapTiles(int[][] configuration, int row1, int col1, int row2, int col2) {
        int temp = configuration[row1][col1];
        configuration[row1][col1] = configuration[row2][col2];
        configuration[row2][col2] = temp;
    }

    private int[][] copyBoard(int[][] original) {
        int[][] copy = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    public boolean isGoalState() {
        int[][] goal = getGoal();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (board[i][j] != goal[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void shuffleBoard(int moveCount) {
        Random random = new Random();
        // UP      Down     Left     Right
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for(int i = 0 ; i < moveCount; i++)
        {
            int[] emptyTilePosition = findEmptyTilePosition();
            int emptyRow = emptyTilePosition[0];
            int emptyCol = emptyTilePosition[1];
            int newRow = 0;
            int newCol = 0;
            int randomNumber;
            int[] move;
            boolean flag = false;
            do {
                randomNumber = random.nextInt(4);
                move = moves[randomNumber];
                newRow = emptyRow + move[0];
                newCol = emptyCol + move[1];
                if(isValidMove(newRow, newCol))
                    flag = true;

            } while (!flag);
            swapTiles(this.getBoardConfiguration(), emptyRow, emptyCol, newRow, newCol);

        }
        if(isGoalState())
            shuffleBoard(moveCount);
        this.estimatedDistanceToGoal = this.calculateManhattanDistance();
        int[] temp = this.findEmptyTilePosition();
        if (temp[0] != -1 && temp[1] != -1) {
            this.emptyRow = temp[0];
            this.emptyCol = temp[1];
        }         }

    // Additional methods (hashCode, equals, toString, etc.) as needed...

    @Override
    public int hashCode() {
        return java.util.Arrays.deepHashCode(this.board);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PuzzleNode)) return false;
        PuzzleNode other = (PuzzleNode) obj;
        return java.util.Arrays.deepEquals(this.board, other.board);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int value : row) {
                sb.append(value).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public int calculateManhattanDistance() {
        int distance = 0;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                int value = board[i][j];

                if (value != 0) {  // Ignore the empty tile
                    int goalRow = (value - 1) / length;  // Calculate the expected row for the value
                    int goalCol = (value - 1) % length;  // Calculate the expected column for the value

                    distance += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return distance;
    }

    public int calculateNonAdmissibleHeuristic() {
        int manhattanDistance = calculateManhattanDistance();
         return manhattanDistance * 2;
    }


}


