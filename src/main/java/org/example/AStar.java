package org.example;

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Queue;

public class AStar {
    private HashSet<int[][]> allBoards = new HashSet<>();
    private PuzzleNode start;
    private PuzzleGraph graph;
    private int moveCount = 0;
    private int nodeAmount = 0;
    private double durationInSeconds;
    private long startTime;
    private long endTime;

    public AStar(PuzzleGraph graph, PuzzleNode startNode) {
        this.graph = graph;
        this.start = startNode;
        performAStarSearch();
    }

    private void performAStarSearch() {
        startTimeMeasurement();
        if (start.isGoalState()) {
            System.out.println("Starting board is already the goal.");
            endTimeMeasurement();
            return;
        }

        PriorityQueue<PuzzleNode> queue = new PriorityQueue<>(Comparator.comparingInt(PuzzleNode::getEstimatedTotalCost));
        initializeStartNode();
        queue.add(start);
        allBoards.add(start.getBoardConfiguration());

        while (!queue.isEmpty()) {
            PuzzleNode currentNode = queue.poll();
            if (currentNode.isGoalState()) {
                endTimeMeasurement();
                moveCount = currentNode.getPathCostFromStart();
                tracePath(currentNode);
                return;
            }
            processNeighbors(currentNode, queue);
        }

        System.out.println("No path found to the goal.");
    }

    private void initializeStartNode() {
        start.setPathCostFromStart(0);
        updateHeuristic(start);
        start.setEstimatedTotalCost(start.getEstimatedDistanceToGoal());
        start.setDistance(start.getPathCostFromStart());
    }

    private void processNeighbors(PuzzleNode currentNode, PriorityQueue<PuzzleNode> queue) {
        nodeAmount++;
        for (PuzzleNode neighbor : currentNode.generateNeighbors(allBoards)) {
            neighbor.setPathCostFromStart(currentNode.getPathCostFromStart() + 1);
            updateHeuristic(neighbor);
            neighbor.setEstimatedTotalCost(neighbor.getPathCostFromStart() + neighbor.getEstimatedDistanceToGoal());//g + h
            neighbor.setDistance(neighbor.getPathCostFromStart());
            neighbor.setPredecessor(currentNode);
            queue.add(neighbor);
        }
    }

    private void updateHeuristic(PuzzleNode node) {
        if (node.getHeuristic() == 0) {
            node.setEstimatedDistanceToGoal(0);
        } else if (node.getHeuristic() == 1) {
            node.setEstimatedDistanceToGoal(node.calculateManhattanDistance());
        } else {
            node.setEstimatedDistanceToGoal(node.calculateNonAdmissibleHeuristic());
        }
    }

    private void startTimeMeasurement() {
        startTime = System.nanoTime();
    }

    private void endTimeMeasurement() {
        endTime = System.nanoTime();
        durationInSeconds = (endTime - startTime) / 1e9;
    }

    public double getDurationInSeconds() {
        return durationInSeconds;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getNodeAmount() {
        return nodeAmount;
    }

    private void tracePath(PuzzleNode goalNode) {
        PuzzleNode currentNode = goalNode;
        while (currentNode != null && currentNode.getPredecessor() != null) {
            graph.addEdge(currentNode, currentNode.getPredecessor());
            currentNode = currentNode.getPredecessor();
        }
    }
}
