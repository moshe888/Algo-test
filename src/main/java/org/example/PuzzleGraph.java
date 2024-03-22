import java.util.ArrayList;
import java.util.HashMap;

public class PuzzleGraph {

    public HashMap<PuzzleNode, ArrayList<PuzzleNode> > map = new HashMap<>();

    public void addNode(PuzzleNode node) {
        if (!map.containsKey(node))
            map.put(node, new ArrayList<PuzzleNode>());          
    }

    public void addEdge(PuzzleNode source, PuzzleNode destination) {

        addNode(source);
        addNode(destination);
        
        if(!map.get(source).contains(destination))
            map.get(source).add(destination);
        
        if(!map.get(destination).contains(source))
            map.get(destination).add(source);
    }
    
    public void getNodeCount()
    {
        System.out.println("The graph has "
                           + map.keySet().size()
                           + " node");
    }

    public void getEdgesCount()
    {
        int count = 0;
        for (PuzzleNode v : map.keySet()) {
            count += map.get(v).size();
        }
        count = count / 2;
        System.out.println("The graph has "
                           + count
                           + " edges.");
    }

    public void hasVertex(PuzzleNode s)
    {
        if (map.containsKey(s)) {
            System.out.println("The graph contains "
                               + s + " as a vertex.");
        }
        else {
            System.out.println("The graph does not contain "
                               + s + " as a vertex.");
        }
    }
 
    public void hasEdge(PuzzleNode s, PuzzleNode d)
    {
        if (map.get(s).contains(d)) {
            System.out.println("The graph has an edge between "
                               + s + " and " + d + ".");
        }
        else {
            System.out.println("The graph has no edge between "
                               + s + " and " + d + ".");
        }
    }
    // Printing the puzzle nodes in the graph, Shows the way to the solution from starting puzzle
    public void printGraph()
    {
        for(PuzzleNode node : map.keySet()){
            node.printBoard(node.getBoardConfiguration());
            System.out.println();
            System.out.println();
        }
    }
}
