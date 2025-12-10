package ai;

import java.util.ArrayList;
import main.GamePanel;

public class PathFinder {

    GamePanel gp;                 // Reference to main game panel for accessing world data
    Node[][] node;                // 2D array of nodes representing the world grid
    ArrayList<Node> openList = new ArrayList<>();  // Nodes to be evaluated
    public ArrayList<Node> pathList = new ArrayList<>(); // Final path from start to goal
    Node startNode, goalNode, currentNode; // Key nodes in the search process
    boolean goalReached = false;  // Flag indicating if a path was found
    int step = 0;                 // Step counter to prevent infinite loops

    // Constructor: Initialize PathFinder with game panel and create node grid
    public PathFinder(GamePanel gp) {
        this.gp = gp;
        instantiateNode();
    }

    // Initialize the node grid based on world dimensions
    private void instantiateNode() {
        node = new Node[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;

        // Create Node objects for each cell in the world grid
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            node[col][row] = new Node(col, row);
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    // Reset all nodes to their default state and clear lists
    public void resetNodes() {
        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            // Reset node states for a new pathfinding search
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

        // Clear search data
        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    // Prepare nodes for a new search between start and goal positions
    public void setNode(int startCol, int startRow, int goalCol, int goalRow) {
        resetNodes();

        // Set start, current, and goal nodes
        startNode = node[startCol][startRow];
        currentNode = startNode;
        goalNode = node[goalCol][goalRow];
        openList.add(currentNode); // Start with the start node in the open list

        int col = 0;
        int row = 0;

        // Configure each node in the grid
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            // Mark solid nodes based on tile collision data
            int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
            if (gp.tileM.tile[tileNum].collision == true) {
                node[col][row].solid = true;
            }

            // Mark solid nodes for destructible interactive tiles
            for (int i = 0; i < gp.iTile[1].length; i++) {
                if (gp.iTile[gp.currentMap][i] != null && gp.iTile[gp.currentMap][i].destructible == true) {
                    int itCol = gp.iTile[gp.currentMap][i].worldX / gp.tileSize;
                    int itRow = gp.iTile[gp.currentMap][i].worldY / gp.tileSize;
                    node[itCol][itRow].solid = true;
                }
            }

            // Calculate costs for each node (g, h, f)
            getCost(node[col][row]);

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    // Calculate gCost, hCost, and fCost for a given node
    public void getCost(Node node) {
        // G-Cost: Manhattan distance from start node
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // H-Cost: Manhattan distance to goal node (heuristic)
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F-Cost: total estimated cost of path through this node
        node.fCost = node.gCost + node.hCost;
    }

    // Perform A* search to find a path from start to goal
    public boolean search() {
        // Continue until goal is found or step limit is reached (prevents infinite loops)
        while (goalReached == false && step < 500) {
            int col = currentNode.col;
            int row = currentNode.row;

            // Mark current node as evaluated
            currentNode.checked = true;
            openList.remove(currentNode);

            // Explore neighboring nodes (up, left, down, right)
            if (row - 1 >= 0) {
                openNode(node[col][row - 1]); // Up
            }
            if (col - 1 >= 0) {
                openNode(node[col - 1][row]); // Left
            }
            if (row + 1 < gp.maxWorldRow) {
                openNode(node[col][row + 1]); // Down
            }
            if (col + 1 < gp.maxWorldCol) {
                openNode(node[col + 1][row]); // Right
            }

            // Find the best node in openList to explore next
            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                // Prioritize lower F cost
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                // Tie-breaker: if F costs are equal, choose lower G cost
                else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            // If no nodes left to explore, path doesn't exist
            if (openList.size() == 0) {
                break;
            }

            // Move to the best node for next iteration
            currentNode = openList.get(bestNodeIndex);

            // Check if goal is reached
            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath(); // Reconstruct the final path
            }
            step++;
        }

        return goalReached;
    }

    // Add a node to openList if it's walkable and not already processed
    public void openNode(Node node) {
        if (node.open == false && node.checked == false && node.solid == false) {
            node.open = true;
            node.parent = currentNode; // Link back to current node for path tracking
            openList.add(node);
        }
    }

    // Backtrack from goal to start to construct the final path
    public void trackThePath() {
        Node current = goalNode;

        // Follow parent pointers back to start
        while (current != startNode) {
            pathList.add(0, current); // Add to front to maintain order from start to goal
            current = current.parent;
        }
    }
}