package org.example;

import java.util.ArrayList;
import java.util.HashMap;

public class PuzzleGraph {

    private HashMap<PuzzleNode, ArrayList<PuzzleNode>> adjacencyList = new HashMap<>();

    // Adds a new puzzle node to the graph if it doesn't already exist
    public void addNode(PuzzleNode node) {
        adjacencyList.computeIfAbsent(node, k -> new ArrayList<>());
    }

    // Adds an edge between two puzzle nodes, ensuring no duplicates
    public void addEdge(PuzzleNode source, PuzzleNode destination) {
        addNode(source);
        addNode(destination);

        if (!adjacencyList.get(source).contains(destination)) {
            adjacencyList.get(source).add(destination);
        }

        // Assuming this is an undirected graph
        if (!adjacencyList.get(destination).contains(source)) {
            adjacencyList.get(destination).add(source);
        }
    }


    // Prints the graph by listing each node and its connections
    public void printGraph() {
        for (PuzzleNode node : adjacencyList.keySet()) {
            System.out.println("Node: ");
            System.out.println(node);
            System.out.print("Connected to: ");
            for (PuzzleNode neighbor : adjacencyList.get(node)) {
                System.out.print(neighbor + " ");
            }
            System.out.println("\n");
        }
    }
}
