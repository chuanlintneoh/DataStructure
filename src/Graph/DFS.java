package Graph;

import java.util.ArrayList;
import java.util.Stack;
import Graph.Graph.Node;

public class DFS {
    // Depth-First Search Traverse Algorithm
    public Stack<Node> stack;
    public ArrayList<Node> visited;
    Node root;

    public DFS(Node root, boolean traverseNow){
        this.stack = new Stack<>();
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
                for (int i = currentNeighbors.size() - 1; i >= 0; i--){
                    // currentNeigbors is sorted ascendingly, since stack pop elements from behind, insertion needs to be reserved to preserve the behavior of choosing lower primary key first
                    if (stack.contains(currentNeighbors.get(i))){
                        System.out.printf("Neighbor node %s already existed in stack, thus will be moved to top of stack.\n", currentNeighbors.get(i));
                        if (!stack.remove(currentNeighbors.get(i))){
                            success = false;
                            System.out.printf("Failed to remove node %s from stack.\n", currentNeighbors.get(i));
                            break;
                        }
                        else {
                            stack.push(currentNeighbors.get(i));
                            System.out.printf("Node %s moved to top of stack.\n", currentNeighbors.get(i));
                            System.out.println("Stack: " + stack);
                        }
                        continue;
                    }
                    if (visited.contains(currentNeighbors.get(i))){
                        System.out.printf("Neighbor node %s was visited before, thus not pushed to stack.\n", currentNeighbors.get(i));
                        continue;
                    }
                    stack.push(currentNeighbors.get(i));
                    System.out.printf("Pushed node %s to stack.\n", currentNeighbors.get(i));
                    System.out.println("Stack: " + stack);
                }
                if (!success){
                    break;
                }
            }
            else {
                System.out.printf("Node %s does not have any neighbor.\n", current);
            }
            if (stack.empty()){
                break;
            }
            Node next = stack.pop();
            System.out.printf("Next node: %s\n", next);
            visited.addLast(next);
            System.out.println("Stack: " + stack);
            System.out.println("Visited: " + visited);
        }
        printTraversedNodes();
    }
    public ArrayList<Node> getTraversedNodeList(){
        return visited;
    }
    public String getTraversedNodes(){
        StringBuilder traversedList = new StringBuilder();
        traversedList.append("\nDepth-First Search Traversed Nodes:\n");
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
