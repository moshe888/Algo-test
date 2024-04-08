package org.example;
import  java.util.Stack;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class BFS {
    private HashSet<int[][]> visitedBoards = new HashSet<>();
    private int puzzleSize;
    private long startTime;
    private long endTime;
    private double durationInSeconds;
    private int moveCount = 0;

    private PuzzleNode startNode;
    private PuzzleGraph graph;
    public Stack<PuzzleNode> stack  ;
    private int nodeCount;

    public BFS(PuzzleGraph graph, PuzzleNode startNode ,  Stack<PuzzleNode> stack )
    {
        this.puzzleSize = startNode.getLength();
        this.graph = graph;
        this.startNode = startNode;
        this.nodeCount = 0;
        this.stack = stack;
        executeBFS();
    }

    private void executeBFS() {
        startTime = System.nanoTime();
        Queue<PuzzleNode> queue = new ArrayDeque<>();
        startNode.setColor(PuzzleNode.Color.GRAY);
        startNode.setDistance(0);
        startNode.setPredecessor(null);

        if (startNode.isGoalState()) { // פתור
            System.out.println("Starting board is already at the goal state.");
            return;
        }
//        System.out.println("BFS");
        queue.add(startNode);
        visitedBoards.add(startNode.getBoardConfiguration());
        while (!queue.isEmpty()) {
            PuzzleNode currentNode = queue.poll();
//            System.out.println(currentNode);
            nodeCount++;
             for (PuzzleNode neighbor : currentNode.generateNeighbors(visitedBoards)) {
                if (neighbor.getColor() == PuzzleNode.Color.WHITE) {
                     neighbor.setColor(PuzzleNode.Color.GRAY);
                    neighbor.setDistance(currentNode.getDistance() + 1);
                    neighbor.setPredecessor(currentNode);
                    queue.add(neighbor);

                    if (neighbor.isGoalState()) {
                        endTime = System.nanoTime();
                        durationInSeconds = (endTime - startTime) / 1e9;
                        moveCount = neighbor.getDistance();
                        tracePath(neighbor);
                        System.out.println("----------");
                        return;
                    }
                }
            }
            currentNode.setColor(PuzzleNode.Color.BLACK);
        }
    }

    public double getDurationInSeconds() {
        return durationInSeconds;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    private void tracePath(PuzzleNode goalNode) {
        PuzzleNode currentNode = goalNode;

        while (currentNode != null) {
            if (currentNode.getPredecessor() != null) {
                stack.push(currentNode);
                graph.addEdge(currentNode, currentNode.getPredecessor());
            }
            currentNode = currentNode.getPredecessor();
        }

    }
}
