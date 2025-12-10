package ai;

public class Node {

    Node parent;          // Parent node in the path, used to backtrack the final route
    public int col;       // Column (x-coordinate) of the node in the grid
    public int row;       // Row (y-coordinate) of the node in the grid
    public int gCost;     // Distance/cost from the start node to this node
    public int hCost;     // Heuristic cost (estimated distance) from this node to the goal
    public int fCost;     // Total cost: f = g + h, used to decide which node to explore next
    public boolean solid; // If true, node is blocked/not walkable
    public boolean open;  // If true, node is in the openList (to be evaluated)
    public boolean checked; // If true, node has been evaluated already

    // Constructor: Initialize node with its grid position
    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }
}