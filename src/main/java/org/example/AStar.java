import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;
import java.util.HashSet;

public class AStar {
    // HashSet is used so that there wont be duplicates boards
    public HashSet<int[][]> allBoards = new HashSet<>();

    public long startTime;
    public long endTime;
    public double durationInSeconds;

    private int moveCount = 0;
    public PuzzleNode start;
    public PuzzleGraph graph;
    public int nodeAmount;
    public int len;

    public AStar(PuzzleGraph graph, PuzzleNode startNode)
    {
        this.len = startNode.getLength();
        this.graph = graph;
        this.start = startNode;
        this.nodeAmount = 0;
        astar();                

    }
    public void astar()
    {
        startTime = System.nanoTime();
        PriorityQueue<PuzzleNode> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getF()));
        if(start.isGoal())
        {
            System.out.println("Starting Board is already goal");
            return;
        }
        start.setG(0);
        if(start.getHeuristic() == 0)
            start.setH(0);
        else if (start.getHeuristic() == 1) 
            start.setH(start.calculateManhattanDistance());
        else
            start.setH(start.calculateRandomHeuristic());     
        start.setF(start.getH());
        // Setting Distance so that the nodes will be sorted for the graph with HashCode function in PuzzleNode.java
        start.setDistance(start.getG());
        queue.add(start);
        allBoards.add(start.getBoardConfiguration());

        while (!queue.isEmpty()) 
        {
            PuzzleNode u = queue.poll();
            nodeAmount++;
            if (u.isGoal()) {
                endTime = System.nanoTime();
                durationInSeconds = (endTime - startTime) / 1e9;
                moveCount = u.getG();
                getPath(u);
                return;
            }            

            Queue<PuzzleNode> q = u.getNodeNeighbors(allBoards);
            for (PuzzleNode node : q) 
            {
                node.setG(u.getG() + 1);
                if(node.getHeuristic() == 0)
                    node.setH(0);
                else if (node.getHeuristic() == 1) 
                    node.setH(node.calculateManhattanDistance());
                else
                    node.setH(node.calculateRandomHeuristic()); 
                node.setF(node.getG() + node.getH());
                // Setting Distance so that the nodes will be sorted for the graph with HashCode function in PuzzleNode.java
                node.setDistance(node.getG());
                node.setPredecessor(u);
                queue.add(node);
            
            }
        
        }
        System.out.println("No path found to the goal.");
        System.out.printf("Nodes Found: %d", moveCount);

    }
    public double getTime()
    {
    return durationInSeconds;
    }
    public int getMoveCount() {
        return moveCount;
    }
    public int getNodeCount()
    {
        return nodeAmount;
    }

    public void getPath(PuzzleNode goalNode)
    {
        int i = moveCount + 1;
        // Goal Node
        PuzzleNode newNode = goalNode;
        // Getting the path and filling the graph
        while(i > 0)
        {
            // if not starting Node
            if(newNode.getPredecessor() != null)
            {
                graph.addEdge(newNode, newNode.getPredecessor());
                newNode = newNode.getPredecessor();
            }
            // First Starting Node
            else
            {
                for(PuzzleNode secondNode : graph.map.keySet())
                {
                    if (secondNode.getPredecessor() == newNode)
                    {
                        graph.addEdge(newNode, secondNode);
                        break;
                    }
                }
            }
            i--;
            }
        }
}
