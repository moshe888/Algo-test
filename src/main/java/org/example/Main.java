package org.example;

import java.util.Random;
//אופציה להכניס לוח ידני
//הרצת 5 לוחות רנדומלים עם ערבוב 10 פעמים 15 \24
//ממןצע הרצת 50 לוחות רנדומלים 24\15

public class Main {
    public static final int[][] goalBoard15 = {
               {1, 2, 3, 4},
               {5, 6, 7, 8},
               {9, 10, 11, 12},
               {13, 14, 15, 0}
       };
    public static final int[][] goalBoard24 = {
               {1,  2,   3, 4,  5 },
               {6,  7,  8,  9,  10},
               {11, 12, 13, 14, 15},
               {16, 17, 18, 19, 20},
               {21, 22, 23, 24, 0 }

       };



    // Starting board that all the algorithms will use
    public PuzzleNode startingBoard;

    // Variables for time calculating and board length
    public long startTime;
    public long endTime;
    public double durationInSeconds;
    public int len;

    // Variables of BFS, Dijakstra, A* ad

    public PuzzleNode bfsBoard;
    public PuzzleGraph puzzleGraph;
    public BFS bfs15;
    public int nodeAmountBfs;
    public int nodeAvgBfs = 0;
    public int moveCountBfs;
    public int moveCountAvgBfs = 0;
    public double durationInSecondsBfs;
    public double durationInSecondsAvgBfs = 0;

    public PuzzleNode aStarDijakstraBoard;
    public PuzzleGraph aStarDijakstraPuzzleGraph;
    public AStar AStarDijakstra;
    public int nodeAmountDijakstra;
    public int nodeAvgDijakstra = 0;
    public int moveCountDijakstra;
    public int moveCountAvgDijakstra = 0;
    public double durationInSecondsDijakstra;
    public double durationInSecondsAvgDijakstra = 0;

    public PuzzleNode aStarBoard;
    public PuzzleGraph aStarPuzzleGraph;
    public AStar AStar;
    public int nodeAmountAStar;
    public int nodeAvgAStar = 0;
    public int moveCountAStar;
    public int moveCountAvgAStar = 0;
    public double durationInSecondsAStar;
    public double durationInSecondsAvgAStar = 0;

    public PuzzleNode aStarRandomHeuristicBoard;
    public PuzzleGraph aStarRandomHeuristicPuzzleGraph;
    public AStar aStarRandomHeuristic;
    public int nodeAmountAStar2;
    public int nodeAvgAStar2 = 0;
    public int moveCountAStar2;
    public int moveCountAvgAStar2 = 0;
    public double durationInSecondsAStar2;
    public double durationInSecondsAvgAStar2 = 0;


    // Variables Reset for the 50 puzzle average test
    public void resetVariables()
    {
        durationInSecondsAvgBfs = 0;
        nodeAvgBfs = 0;
        moveCountAvgBfs = 0;

        durationInSecondsAvgDijakstra = 0;
        nodeAvgDijakstra = 0;
        moveCountAvgDijakstra = 0;

        durationInSecondsAvgAStar = 0;
        nodeAvgAStar = 0;
        moveCountAvgAStar = 0;

        durationInSecondsAvgAStar2 = 0;
        nodeAvgAStar2 = 0;
        moveCountAvgAStar2 = 0;
    }
    // ****************************** PRINTINGS ******************************
    public void printAvgAll()
    {
        bfsPrintAvg();
        dijakstraPrintAvg();
        aStarPrintAvg();
        aStar2PrintAvg();
    }
    public void printAll()
    {
        bfsPrintSingle();
        dijakstraPrintSingle();
        aStarPrintSingle();
        aStar2PrintSingle();
    }
    public void bfsPrintAvg()
    {
        System.out.println("-------------------- BFS Average Stats -----------------------------\n");
        printAverageResults(nodeAvgBfs, moveCountAvgBfs, durationInSecondsAvgBfs);
    }
    public void dijakstraPrintAvg()
    {
        System.out.println("-------------------- Dijakstra Average Stats --------------------------");
        printAverageResults(nodeAvgDijakstra, moveCountAvgDijakstra, durationInSecondsAvgDijakstra);
    }
    public void aStarPrintAvg()
    {
        System.out.println("-------------------- AStar Manhattan Average Stats --------------------------");
        printAverageResults(nodeAvgAStar, moveCountAvgAStar, durationInSecondsAvgAStar);
    }
    public void aStar2PrintAvg()
    {
        System.out.println("-------------------- AStar Non Admissible Heuristic Average Stats --------------------------");
        printAverageResults(nodeAvgAStar2, moveCountAvgAStar2, durationInSecondsAvgAStar2);
    }
    public void bfsPrintSingle()
    {
        System.out.println("-------------------- BFS Stats -----------------------------\n");
        printResults(nodeAmountBfs, moveCountBfs, durationInSecondsBfs);
    }
    public void dijakstraPrintSingle()
    {
        System.out.println("-------------------- Dijakstra Stats --------------------------");
        printResults(nodeAmountDijakstra, moveCountDijakstra, durationInSecondsDijakstra);
    }
    public void aStarPrintSingle()
    {
        System.out.println("-------------------- AStar Manhattan Stats --------------------------");
        printResults(nodeAmountAStar, moveCountAStar, durationInSecondsAStar);
    }
    public void aStar2PrintSingle()
    {
        System.out.println("-------------------- AStar Non Admissible Heuristic Stats --------------------------");
        printResults(nodeAmountAStar2, moveCountAStar2, durationInSecondsAStar2);
    }
    public void printResults(int nodeAmount, int moveCount, double durationInSeconds)
    {
        System.out.printf("Board Size is %d:%n",len * len - 1);
        System.out.printf("Time taken is: %s seconds%n", String.format("%.9f", durationInSeconds));
        System.out.printf("Node Amount is: %d%n", nodeAmount);
        System.out.printf("Move Count is: %d%n", moveCount);
    }
    public void printAverageResults(int nodeAvg, int moveCountAvg, double durationInSecondsAvg)
    {
        System.out.printf("Board Size is %d:%n",len * len - 1);
        System.out.printf("Average Time taken is: %s seconds%n", String.format("%.9f", durationInSecondsAvg / 50));
        System.out.printf("Average Node Amount is: %d%n", nodeAvg / 50);
        System.out.printf("Average Move Count is: %d%n",moveCountAvg / 50);
    }
        // ****************************** PRINTINGS ******************************

    // 5 Random Puzzle Test
    public void test5()
    {
        int counter = 8;  // 8 9 10 11 12
        for(int i = 0 ; i < 5; i++)
        {
            testBoard(goalBoard15, counter); // 12 possible
            printAll();
            counter++;
        }
        counter = 7; // 7 8 9 10 11
        for(int i = 0; i < 5; i++)
        {
            testBoard(goalBoard24, counter); // 11 possible
            printAll();
            counter++;
        }
    }
    // 50 Random Puzzle Test
    public void test50()
    {
        Random rand = new Random();
        for(int i = 0; i < 50; i++)
        {
            // from 10 - 11
            testBoard(goalBoard15, rand.nextInt(2) + 10); // 12 possible
        }
        printAvgAll();
        resetVariables();
        for(int i = 0 ; i < 50; i++)
        {
            // from 9 - 10
            testBoard(goalBoard24, rand.nextInt(2) + 9); // 11 possible
        }
        printAvgAll();
    }
    // Recieves a board and the moves
    public void testBoard(int[][] board, int moves)
    {
        len = board.length;
        startingBoard = new PuzzleNode(board,0, moves);
        System.out.println(startingBoard);

        // ****************************** BFS ******************************
        puzzleGraph = new PuzzleGraph();
        bfsBoard = new PuzzleNode(startingBoard.getBoardConfiguration(),0 ,0);
        bfs15 = new BFS(puzzleGraph, bfsBoard);
        durationInSecondsBfs = bfs15.getDurationInSeconds();
        durationInSecondsAvgBfs += durationInSecondsBfs;
        nodeAmountBfs = bfs15.getNodeCount();
        nodeAvgBfs += nodeAmountBfs;
        moveCountBfs = bfs15.getMoveCount();
        moveCountAvgBfs += moveCountBfs;
        //****************************** AStar Dijakstra Distance ******************************
        aStarDijakstraBoard = new PuzzleNode(startingBoard.getBoardConfiguration(),0, 0);
        aStarDijakstraPuzzleGraph = new PuzzleGraph();
        AStarDijakstra = new AStar(aStarDijakstraPuzzleGraph,aStarDijakstraBoard);
        durationInSecondsDijakstra = AStarDijakstra.getDurationInSeconds();
        durationInSecondsAvgDijakstra += durationInSecondsDijakstra;
        nodeAmountDijakstra = AStarDijakstra.getNodeAmount();
        nodeAvgDijakstra += nodeAmountDijakstra;
        moveCountDijakstra = AStarDijakstra.getMoveCount();
        moveCountAvgDijakstra += moveCountDijakstra;
        // ****************************** AStar Manhattan Distance ******************************
        aStarBoard = new PuzzleNode(startingBoard.getBoardConfiguration(),1, 0);
        aStarPuzzleGraph = new PuzzleGraph();
        AStar = new AStar(aStarPuzzleGraph, aStarBoard);
        durationInSecondsAStar = AStar.getDurationInSeconds();
        durationInSecondsAvgAStar += durationInSecondsAStar;
        nodeAmountAStar = AStar.getNodeAmount();
        nodeAvgAStar += nodeAmountAStar;
        moveCountAStar = AStar.getMoveCount();
        moveCountAvgAStar += moveCountAStar;
        // ****************************** AStar Non Admissible Heuristic Distance ******************************
        aStarRandomHeuristicBoard = new PuzzleNode(startingBoard.getBoardConfiguration(),2, 0);
        aStarRandomHeuristicPuzzleGraph = new PuzzleGraph();
        aStarRandomHeuristic = new AStar(aStarRandomHeuristicPuzzleGraph, aStarRandomHeuristicBoard);
        durationInSecondsAStar2 = aStarRandomHeuristic.getDurationInSeconds();
        durationInSecondsAvgAStar2 += durationInSecondsAStar2;
        nodeAmountAStar2 = aStarRandomHeuristic.getNodeAmount();
        nodeAvgAStar2 += nodeAmountAStar2;
        moveCountAStar2 = aStarRandomHeuristic.getMoveCount();
        moveCountAvgAStar2 += moveCountAStar2;
    }
    public static void main(String[] args) {

       Main main = new Main();
       int[] result = PuzzleInput.getManualMoves();

       if(result[0] > 0)
       {
            main.testBoard(goalBoard15, result[0]);
            main.printAll();
            main.testBoard(goalBoard24, result[0]);
            main.printAll();
       }
       else if(result[1] == 15)
       {
            int[][] initialBoard15 = PuzzleInput.getManualInput15();
            if(initialBoard15 != null)
            {
                main.testBoard(initialBoard15, 0);
                main.printAll();
            }
        }
        else if(result[1] == 24)
        {
            int[][] initialBoard24 = PuzzleInput.getManualInput24();
            if(initialBoard24 != null)
            {
                main.testBoard(initialBoard24, 0);
                main.printAll();
            }
        }
        else
        {
            System.out.println("Starting test5\n");
            main.test5();
            System.out.println("Starting test50\n");
            main.test50();
        }
       // PuzzleInput.scanner.close();
    }


}
