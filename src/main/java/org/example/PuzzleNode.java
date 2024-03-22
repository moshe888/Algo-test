import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;

public class PuzzleNode {
    private static final int[][] goal15 = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
    private static final int[][] goal24 = {{1,2,3,4,5},{6,7,8,9,10}, {11,12,13,14,15}, {16,17,18,19,20}, {21,22,23,24,0}};
    public enum Color {
        WHITE, GRAY, BLACK
    }
    private int[][] board;
    
    private static int length;
    int emptyRow, emptyCol;

    // New attributes for BFS
    private Color color;
    private int distance;
    private PuzzleNode predecessor;

    // New attributes for AStar
    private int heuristic;
    private int h;
    private int g;
    private int f;

    public PuzzleNode(int[][] boardConfiguration, int heuristic, int moves) {
        setLength(boardConfiguration.length);
        this.board = new int[getLength()][getLength()];
        this.board = copyBoard(boardConfiguration);
        if(moves == 0)
        {
            int[] temp = getEmptyTilePosition();
            if (temp[0] != -1 && temp[1] != -1) {
                emptyRow = temp[0];
                emptyCol = temp[1];
            } 
        }
        // moves > 0
        else
        {
            randomBoard2(moves);
        }
        // Initialize BFS attributes
        this.color = Color.WHITE;
        this.distance = Integer.MAX_VALUE;
        this.predecessor = null;
        setHeuristic(heuristic);
    }

    public int[][] getGoal() {
        if(getLength() == 4)
            return goal15;
        return goal24;
    }
    // Getter and setter methods for BFS attributes
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
    public void setHeuristic(int heuristic)
    {
        this.heuristic = heuristic;
    }
    public void setH(int h) {
        this.h = h;;
    }
    public void setG(int g) {
        this.g = g;
    }
    public void setF(int f) {
        this.f = f;
    }
    public int getHeuristic()
    {
        return this.heuristic;
    }
    public int getH() {
        return this.h;
    }
    public int getG() {
        return this.g;
    }
    public int getF() {
        return this.f;
    }
    public PuzzleNode getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(PuzzleNode predecessor) {
        this.predecessor = predecessor;
    }
    public void setBoardConfiguration(PuzzleNode node) {
        copyBoard(node.board);
    }
    public int[][] getBoardConfiguration() {
        return board;
    }

    public void setLength(int len)
    {
        length = len;
    }
    public int getLength()
    {
        return length;
    }
   
    // Recieveing all boards as hashset so that there wont be duplicate boards
    public Queue<PuzzleNode> getNodeNeighbors(HashSet<int[][]> allBoards)
    {
        Queue<PuzzleNode> neighbors = new ArrayDeque<>();
        int[] emptyTilePosition = getEmptyTilePosition();
        int emptyRow = emptyTilePosition[0];
        int emptyCol = emptyTilePosition[1];

            // Possible moves: up, down, left, right
            int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

            for (int[] move : moves) {
                int newRow = emptyRow + move[0];
                int newCol = emptyCol + move[1];

                if (this.isValidMove(newRow, newCol)) {
                    // Create a new puzzle node by swapping the empty tile with the neighboring tile
                    int[][] newConfiguration = copyBoard(this.getBoardConfiguration());
                    swapTiles(newConfiguration, emptyRow, emptyCol, newRow, newCol);
                    if(!allBoards.contains(newConfiguration));
                    {
                        PuzzleNode neighbor = new PuzzleNode(newConfiguration, heuristic, 0);
                        neighbors.add(neighbor);
                        allBoards.add(neighbor.getBoardConfiguration());
                    }
                }

            }
            return neighbors;
                 
    }
    public int[] getEmptyTilePosition() {
        int len = getLength(); // You need to implement the 'getLength()' method
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (board[i][j] == 0) { // Assuming 0 represents the empty tile
                    return new int[]{i, j};
                }
            }
        }
        // If the empty tile is not found, return an indication, e.g., {-1, -1}
        return new int[]{-1, -1};
    }
    
    

    public  boolean isBoardEqual(int[][] node1, int[][] node2) {
        int len = getLength();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if(node1[i][j] != node2[i][j])
                    return false;
            }
        }
        return true;
    }
    // Other necessary functions (e.g., swapTiles)...
    public  int[][] copyBoard(int[][] original) {
        int len = getLength();
        int[][] copy = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }

    public String getHashKey() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : this.board) {
            for (int i : row) {
                sb.append(i);
            }
        }
        return sb.toString();
    }
    public void swapTiles(int[][] board, int row1, int col1, int row2, int col2) {
        int temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
    }

    public boolean isGoal()
    {
        for(int i = 0; i < length; i++)
        {
            for(int j = 0; j < length; j++)
            {
                if(getGoal()[i][j] != board[i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }

@Override
    public int hashCode() {
        // Compute a hash code for the current state
        // This should be a unique identifier for the state
        // You can use Arrays.hashCode or another method based on your puzzle representation
        //return Arrays.deepHashCode(this.board);
        return this.getDistance();
    }

    @Override
    public boolean equals(Object obj) {
        // Implement the equals method to compare PuzzleNode objects
        // based on their board configuration
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        PuzzleNode other = (PuzzleNode) obj;
        return isBoardEqual(this.board, other.board);
        //return Arrays.deepEquals(this.board, other.board);
    }
   
    public void printBoard(int[][] board) 
    {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
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

    public int calculateRandomHeuristic() {
        Random rand = new Random();
        int dis = calculateManhattanDistance() + rand.nextInt(20) + 5 ;
        return dis;
    }
    public void randomBoard2(int n)
    {
        Random random = new Random();
                        // UP      Down     Left     Right
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int[] lastMove = moves[3];
        for(int i = 0 ; i < n; i++)
        {
            int[] emptyTilePosition = getEmptyTilePosition();
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
                    {
                        if(!isOppositeMove(move, lastMove))
                            flag = true;
                    }
            } while (!flag);
            lastMove = move;
            System.out.printf("Move %d: %d at (%d,%d) changed position with %d at (%d,%d)\n", 
            i + 1, this.board[emptyRow][emptyCol], emptyRow, emptyCol, this.board[newRow][newCol], newRow, newCol);
            swapTiles(this.getBoardConfiguration(), emptyRow, emptyCol, newRow, newCol);
        }
        System.out.println("Starting board after random is: ");
        printBoard(this.getBoardConfiguration());
        this.h = this.calculateManhattanDistance();
        int[] temp = this.getEmptyTilePosition();
        if (temp[0] != -1 && temp[1] != -1) {
            this.emptyRow = temp[0];
            this.emptyCol = temp[1];
        }     
    }
    public void randomBoard(int n)
    {
        Random random = new Random();
                        // UP      Down     Left     Right
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for(int i = 0 ; i < n; i++)
        {
            int[] emptyTilePosition = getEmptyTilePosition();
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
        if(isGoal())
            randomBoard(n);
        this.h = this.calculateManhattanDistance();
        int[] temp = this.getEmptyTilePosition();
        if (temp[0] != -1 && temp[1] != -1) {
            this.emptyRow = temp[0];
            this.emptyCol = temp[1];
        }     
    }

     private boolean isValidMove(int row, int col) {
        // Check if the new position is within the bounds of the puzzle board
        return row >= 0 && row < getLength() && col >= 0 && col < getLength();
    }
    private boolean isOppositeMove(int[] move1, int[] move2) {
        return move1[0] * (-1) == move2[0] && move1[1] * (-1) == move2[1];
    }
}


