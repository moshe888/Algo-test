package org.example;

import java.util.Random;
import java.util.Stack;

public class Main {
    public static final int[][] goal15 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };
    public static final int[][] goal24 = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 0}
    };

    public PuzzleNode startBoard;
    public int len;

    public PuzzleNode bfsNode;
    public PuzzleGraph pGraph;
    public BFS bfsObj;
    public int nAmtBfs, nAvgBfs = 0, mCntBfs, mAvgBfs = 0;
    public double durSecBfs, durAvgBfs = 0;

    public PuzzleNode astarDNode;
    public PuzzleGraph astarDGraph;
    public AStar astarD;
    public int nAmtD, nAvgD = 0, mCntD, mAvgD = 0;
    public double durSecD, durAvgD = 0;

    public PuzzleNode astarNode;
    public PuzzleGraph astarGraph;
    public AStar astar;
    public int nAmtA, nAvgA = 0, mCntA, mAvgA = 0;
    public double durSecA, durAvgA = 0;

    public PuzzleNode astarRNode;
    public PuzzleGraph astarRGraph;
    public AStar astarR;
    public int nAmtR, nAvgR = 0, mCntR, mAvgR = 0;
    public double durSecR, durAvgR = 0;

    public void resetVars() {
        durAvgBfs = durAvgD = durAvgA = durAvgR = 0;
        nAvgBfs = nAvgD = nAvgA = nAvgR = 0;
        mAvgBfs = mAvgD = mAvgA = mAvgR = 0;
    }

    public void printAvgAll() {
        printAvg("BFS", nAvgBfs, mAvgBfs, durAvgBfs);
        printAvg("Dijkstra", nAvgD, mAvgD, durAvgD);
        printAvg("A* Manhattan", nAvgA, mAvgA, durAvgA);
        printAvg("A* Non-Admissible", nAvgR, mAvgR, durAvgR);
    }

    public void printAll() {
        printSingle("BFS", nAmtBfs, mCntBfs, durSecBfs);
        printSingle("Dijkstra", nAmtD, mCntD, durSecD);
        printSingle("A* Manhattan", nAmtA, mCntA, durSecA);
        printSingle("A* Non-Admissible", nAmtR, mCntR, durSecR);
    }

    public void printSingle(String name, int n, int m, double d) {
        System.out.println("-------------------- " + name + " Stats -----------------------------");
        printRes(len * len - 1, d, n, m);
    }

    public void printAvg(String name, int n, int m, double d) {
        System.out.println("-------------------- " + name + " Average Stats -----------------------------");
        printRes(len * len - 1, d / 50, n / 50, m / 50);
    }

    public void printRes(int size, double d, int n, int m) {
        System.out.printf("Board Size: %d%n", size);
        System.out.printf("Time: %s seconds%n", String.format("%.9f", d));
        System.out.printf("Nodes: %d%n", n);
        System.out.printf("Moves: %d%n", m);
    }

    public void test5() {
        int c = 8;
        for (int i = 0; i < 5; i++) {
            test(goal15, c++);
            printAll();
        }
        c = 7;
        for (int i = 0; i < 5; i++) {
            test(goal24, c++);
            printAll();
        }
    }

    public void test50() {
        Random r = new Random();
        for (int i = 0; i < 50; i++) test(goal15, r.nextInt(2) + 10);
        printAvgAll();
        resetVars();
        for (int i = 0; i < 50; i++) test(goal24, r.nextInt(2) + 9);
        printAvgAll();
    }

    public void test(int[][] board, int moves) {
        len = board.length;
        startBoard = new PuzzleNode(board, 0, moves);
        System.out.println(startBoard);

        // Initialize and execute BFS
        pGraph = new PuzzleGraph();
        bfsNode = new PuzzleNode(startBoard.getBoardConfiguration(), 0, 0);
        Stack<PuzzleNode> stack = new Stack<>();
        bfsObj = new BFS(pGraph, bfsNode, stack);
        durSecBfs = bfsObj.getDurationInSeconds();
        durAvgBfs += durSecBfs;
        nAmtBfs = bfsObj.getNodeCount();
        nAvgBfs += nAmtBfs;
        mCntBfs = bfsObj.getMoveCount();
        mAvgBfs += mCntBfs;
        for (PuzzleNode node : stack) {
            System.out.println(node);
        }

        // Initialize and execute A* with Dijkstra
        astarDNode = new PuzzleNode(startBoard.getBoardConfiguration(), 0, 0);
        astarDGraph = new PuzzleGraph();
        astarD = new AStar(astarDGraph, astarDNode);
        durSecD = astarD.getDurationInSeconds();
        durAvgD += durSecD;
        nAmtD = astarD.getNodeAmount();
        nAvgD += nAmtD;
        mCntD = astarD.getMoveCount();
        mAvgD += mCntD;

        // Initialize and execute A* with Manhattan Distance
        astarNode = new PuzzleNode(startBoard.getBoardConfiguration(), 1, 0);
        astarGraph = new PuzzleGraph();
        astar = new AStar(astarGraph, astarNode);
        durSecA = astar.getDurationInSeconds();
        durAvgA += durSecA;
        nAmtA = astar.getNodeAmount();
        nAvgA += nAmtA;
        mCntA = astar.getMoveCount();
        mAvgA += mCntA;

        // Initialize and execute A* with a non-admissible heuristic
        astarRNode = new PuzzleNode(startBoard.getBoardConfiguration(), 2, 0);
        astarRGraph = new PuzzleGraph();
        astarR = new AStar(astarRGraph, astarRNode);
        durSecR = astarR.getDurationInSeconds();
        durAvgR += durSecR;
        nAmtR = astarR.getNodeAmount();
        nAvgR += nAmtR;
        mCntR = astarR.getMoveCount();
        mAvgR += mCntR;
    }

    public static void main(String[] args) {
        Main main = new Main();
        int[] result = PuzzleInput.getManualMoves();

        if (result[0] > 0) {
            main.test(goal15, result[0]);
            main.printAll();
            main.test(goal24, result[0]);
            main.printAll();
        } else if (result[1] == 15) {
            int[][] initialBoard15 = PuzzleInput.getManualInput15();
            if (initialBoard15 != null) {
                main.test(initialBoard15, 0);
                main.printAll();
            }
        } else if (result[1] == 24) {
            int[][] initialBoard24 = PuzzleInput.getManualInput24();
            if (initialBoard24 != null) {
                main.test(initialBoard24, 0);
                main.printAll();
            }
        } else {
            System.out.println("Starting test5");
            main.test5();
            System.out.println("Starting test50");
            main.test50();
        }
    }
}
