import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class BFS {
    // HashSet is used so that there wont be duplicates boards
    public HashSet<int[][]> allBoards = new HashSet<>();

    public int len;
    public long startTime;
    public long endTime;
    public double durationInSeconds;

    private int moveCount = 0;
    public PuzzleNode start;
    public PuzzleGraph graph;
    public int nodeAmount;

    public BFS(PuzzleGraph graph, PuzzleNode startNode)
    {
        this.len = startNode.getLength();
        this.graph = graph;
        this.start = startNode;
        this.nodeAmount = 0;
        bfs();
    }
    public void bfs() {
        startTime = System.nanoTime();
        Queue<PuzzleNode> queue = new ArrayDeque<>();
        start.setColor(PuzzleNode.Color.GRAY);
        start.setDistance(0);
        start.setPredecessor(null);
        if(start.isGoal())
        {
            System.out.println("Starting Board is already goal");
            return;
        }    

        queue.add(start);
        allBoards.add(start.getBoardConfiguration());
        while (!queue.isEmpty()) {
            PuzzleNode u = queue.poll();
            nodeAmount++;
            Queue<PuzzleNode> q = u.getNodeNeighbors(allBoards);
            for (PuzzleNode node : q) 
            {
                if(node.getColor() == PuzzleNode.Color.WHITE)
                {
                    node.setColor(PuzzleNode.Color.GRAY);
                    node.setDistance(u.getDistance() + 1);
                    node.setPredecessor(u);
                    queue.add(node);
                    if(node.isGoal())
                    {
                        endTime = System.nanoTime();
                        durationInSeconds = (endTime - startTime) / 1e9;
                        moveCount = node.getDistance();
                        getPath(node);
                        return;
                    }
                }
            }
            u.setColor(PuzzleNode.Color.BLACK);     
            }
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
