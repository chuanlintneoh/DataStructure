package Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import Graph.Graph.Node;

public class BFS {
    // Breadth-First Search Traverse Algorithm
    public Queue<Node> queue;
    public ArrayList<Node> visited;
    public Node root;

    public BFS(Node root, boolean traverseNow){
        this.queue = new LinkedList<>();
        this.visited = new ArrayList<>();
        this.root = root;
        if (traverseNow) traverse();
    }
    public void traverse(){
        visited.add(root);
        while (true){
            Node current = visited.getLast();
            System.out.printf("Current node: %s\n", current);
            ArrayList<Node> currentNeighbors = current.getNeighbors(); // sorted by nodes' primary key by default
            if (currentNeighbors.size() != 0){
                System.out.printf("Neighbors of current node: %s\n", currentNeighbors);
                boolean success = true;
                for (Node neighbor: currentNeighbors){
                    if (visited.contains(neighbor)){
                        System.out.printf("Neighbor node %s was visited before, thus not offered to queue.\n", neighbor);
                        continue;
                    }
                    if (!queue.offer(neighbor)){
                        success = false;
                        System.out.printf("Failed to offer node %s into queue.\n", neighbor);
                        break;
                    }
                    System.out.printf("Offered node %s into queue.\n", neighbor);
                    System.out.println("Queue: " + queue);
                }
                if (!success) break;
            }
            else {
                System.out.printf("Node %s does not have any neighbor.\n", current);
            }
            if (queue.peek() == null){
                break;
            }
            Node next = queue.remove();
            System.out.printf("Next node: %s\n", next);
            visited.addLast(next);
            System.out.println("Queue: " + queue);
            System.out.println("Visited: " + visited);
        }
        printTraversedNodes();
    }
    public ArrayList<Node> getTraversedNodeList(){
        return visited;
    }
    public String getTraversedNodes(){
        StringBuilder traversedList = new StringBuilder();
        traversedList.append("\nBreadth-First Search Traversed Nodes:\n");
        for (Node node: visited){
            traversedList.append(node);
            traversedList.append("\n");
        }
        return traversedList.toString();
    }
    public void printTraversedNodes(){
        System.out.println(getTraversedNodes());
    }
}
