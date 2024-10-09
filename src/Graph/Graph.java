package Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Graph {
    public Map<Integer, Node> nodes = new HashMap<>();
    public Map<NodePair, Edge> edges = new HashMap<>();
    public Map<Integer, ArrayList<Integer>> adjacencyList = new HashMap<>();

    public boolean doesNodeExist(int primaryKey){
        return nodes.containsKey(primaryKey);
    }
    public boolean doesDirectedEdgeExist(int from, int to){
        return adjacencyList.get(from).contains(to);
    }
    public boolean doesUndirectedEdgeExist(int primaryKey1, int primaryKey2){
        if (doesDirectedEdgeExist(primaryKey1, primaryKey2) && doesDirectedEdgeExist(primaryKey2, primaryKey1)){
            return getDirectedEdgeWeight(primaryKey1, primaryKey2) == getDirectedEdgeWeight(primaryKey2, primaryKey1);
        }
        return false;
    }

    public double getDirectedEdgeWeight(int from, int to){
        double weight = -1.00;
        for (Map.Entry<NodePair, Edge> entry: edges.entrySet()){
            if (entry.getKey().getFrom() == from && entry.getKey().getTo() == to) weight = entry.getValue().getWeight();
        }
        return weight;
    }

    public boolean addNode(int primaryKey, String value){
        if (doesNodeExist(primaryKey)){
            System.out.printf("Node of primary key %d already existed.\n", primaryKey);
            return false;
        }
        Node newNode = new Node(primaryKey, value);
        nodes.put((Integer) primaryKey, newNode);
        adjacencyList.put((Integer) primaryKey, new ArrayList<Integer>());
        System.out.printf("Node of primary key %d, value %s added.\n", primaryKey, value);
        return true;
    }
    public void addNodes(int[] primaryKeys, String[] names){
        if (primaryKeys.length != names.length){
            System.out.printf("Array sizes must match, each node should have a primary key and their corresponding names. [%d][%d]\n", primaryKeys.length, names.length);
            return;
        }
        for (int i = 0; i < primaryKeys.length; i++){
            addNode(primaryKeys[i], names[i]);
        }
    }

    public boolean updateNodeValue(int primaryKey, String value){
        if (!doesNodeExist(primaryKey)){
            System.out.printf("Node of primary key %d does not exist.\n", primaryKey);
            return false;
        }
        String initialValue = nodes.get(primaryKey).getValue();
        nodes.get(primaryKey).setValue(value);
        System.out.printf("Value of node of primary key %d has been changed from %s to %s.\n", primaryKey, initialValue, value);
        return true;
    }

    public boolean deleteNode(int primaryKey){
        if (!doesNodeExist(primaryKey)){
            System.out.printf("Node of primary key %d does not exist.\n", primaryKey);
            return false;
        }
        nodes.remove(primaryKey);
        Iterator<Map.Entry<NodePair, Edge>> iterator = edges.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<NodePair, Edge> entry = iterator.next();
            if (entry.getKey().getFrom() == primaryKey || entry.getKey().getTo() == primaryKey){
                iterator.remove();
            }
        }
        adjacencyList.remove(primaryKey);
        for (Map.Entry<Integer, ArrayList<Integer>> entry: adjacencyList.entrySet()){
            entry.getValue().remove((Object) primaryKey); // specify use .remove(Object o) instead of .remove(int index)
        }
        System.out.printf("Node of primary key %d deleted.\n", primaryKey);
        return true;
    }
    public void deleteNodes(int[] primaryKeys){
        for (int i = 0; i < primaryKeys.length; i++){
            deleteNode(primaryKeys[i]);
        }
    }

    public boolean addDirectedEdge(int primaryKey_fromNode, int primaryKey_toNode, double weight){
        if (primaryKey_fromNode == primaryKey_toNode){
            System.out.printf("Not allowed to add edge to same nodes. (%d, %d)\n", primaryKey_fromNode, primaryKey_toNode);
            return false;
        }
        if (!doesNodeExist(primaryKey_fromNode)){
            System.out.printf("From node of primary key %d does not exist, please insert it first.\n", primaryKey_fromNode);
            return false;
        }
        if (!doesNodeExist(primaryKey_toNode)){
            System.out.printf("To node of primary key %d does not exist, please insert it first.\n", primaryKey_toNode);
            return false;
        }
        if (doesDirectedEdgeExist(primaryKey_fromNode, primaryKey_toNode)){
            System.out.printf("Directed edge (%d, %d) already existed.\n", primaryKey_fromNode, primaryKey_toNode);
            return false;
        }
        Edge newEdge = new Edge(nodes.get((Integer) primaryKey_fromNode), nodes.get((Integer) primaryKey_toNode), weight);
        NodePair newNodePair = new NodePair(primaryKey_fromNode, primaryKey_toNode);
        edges.put((NodePair) newNodePair, newEdge);
        adjacencyList.get((Integer) primaryKey_fromNode).add((Integer) primaryKey_toNode);
        Collections.sort(adjacencyList.get(primaryKey_fromNode)); // Sort arraylist
        System.out.printf("Directed edge of weight %f from node of primary key %d to node of primary key %d added.\n", weight, primaryKey_fromNode, primaryKey_toNode);
        return true;
    }
    public void addDirectedEdges(int[] primaryKeys_fromNode, int[] primaryKeys_toNode, double[] weights){
        if (primaryKeys_fromNode.length != primaryKeys_toNode.length || primaryKeys_fromNode.length != weights.length){
            System.out.printf("Array sizes must match, each edge should have primary keys of from_node and to_node and their respective weights. [%d][%d][%d]\n", primaryKeys_fromNode.length, primaryKeys_toNode.length, weights.length);
            return;
        }
        for (int i = 0; i < primaryKeys_fromNode.length; i++){
            addDirectedEdge(primaryKeys_fromNode[i], primaryKeys_toNode[i], weights[i]);
        }
    }

    public boolean addUndirectedEdge(int primaryKey1, int primaryKey2, double weight){
        if (doesDirectedEdgeExist(primaryKey1, primaryKey2) || doesDirectedEdgeExist(primaryKey2, primaryKey1)){
            if (doesDirectedEdgeExist(primaryKey1, primaryKey2)){
                System.out.printf("Directed edge (%d, %d) already existed. Please remove it first.\n", primaryKey1, primaryKey2);
            }
            if (doesDirectedEdgeExist(primaryKey2, primaryKey1)){
                System.out.printf("Directed edge (%d, %d) already existed. Please remove it first.\n", primaryKey2, primaryKey1);
            }
            if (doesUndirectedEdgeExist(primaryKey1, primaryKey2)){
                System.out.printf("Undirected edge (%d, %d) already existed. Please remove it first.\n", primaryKey1, primaryKey2);
            }
            return false;
        }
        boolean result1 = addDirectedEdge(primaryKey1, primaryKey2, weight);
        boolean result2 = addDirectedEdge(primaryKey2, primaryKey1, weight);
        return result1 && result2;
    }
    public void addUndirectedEdges(int[] primaryKeys1, int[] primaryKeys2, double[] weights){
        if (primaryKeys1.length != primaryKeys2.length || primaryKeys1.length != weights.length){
            System.out.printf("Array sizes must match, each edge should have primary keys of from_node and to_node and their respective weights. [%d][%d][%d]\n", primaryKeys1.length, primaryKeys2.length, weights.length);
            return;
        }
        for (int i = 0; i < primaryKeys1.length; i++){
            addUndirectedEdge(primaryKeys1[i], primaryKeys2[i], weights[i]);
        }
    }

    public boolean updateDirectedEdgeFrom(int initialFrom, int to, int newFrom){
        if (initialFrom == newFrom){
            System.out.printf("Same initial (%d) and new from node (%d), no change neccessary.\n", initialFrom, newFrom);
            return true;
        }
        if (newFrom == to){
            System.out.printf("Not allowed to add edge to same node. (%d, %d)\n", newFrom, to);
            return false;
        }
        if (!doesNodeExist(initialFrom)){
            System.out.printf("From node of primary key %d does not exist.\n", initialFrom);
            return false;
        }
        if (!doesNodeExist(newFrom)){
            System.out.printf("From node of primary key %d does not exist.\n", newFrom);
            return false;
        }
        if (!doesNodeExist(to)){
            System.out.printf("To node of primary key %d does not exist.\n", to);
            return false;
        }
        if (!doesDirectedEdgeExist(initialFrom, to)){
            System.out.printf("Directed edge (%d, %d) does not exist.\n", initialFrom, to);
            return false;
        }
        double weight = getDirectedEdgeWeight(initialFrom, to);
        removeDirectedEdge(initialFrom, to);
        addDirectedEdge(newFrom, to, weight);
        System.out.printf("Directed edge of weight %f has been updated from (%d, %d) to (%d, %d).\n", weight, initialFrom, to, newFrom, to);
        return true;
    }
    public boolean updateDirectedEdgeTo(int from, int initialTo, int newTo){
        if (initialTo == newTo){
            System.out.printf("Same initial (%d) and new to node (%d), no change neccessary.\n", initialTo, newTo);
            return true;
        }
        if (from == newTo){
            System.out.printf("Not allowed to add edge to same node. (%d, %d)\n", from, newTo);
            return false;
        }
        if (!doesNodeExist(from)){
            System.out.printf("From node of primary key %d does not exist.\n", from);
            return false;
        }
        if (!doesNodeExist(initialTo)){
            System.out.printf("To node of primary key %d does not exist.\n", initialTo);
            return false;
        }
        if (!doesNodeExist(newTo)){
            System.out.printf("To node of primary key %d does not exist.\n", newTo);
            return false;
        }
        if (!doesDirectedEdgeExist(from, initialTo)){
            System.out.printf("Directed edge (%d, %d) does not exist.\n", from, initialTo);
            return false;
        }
        double weight = getDirectedEdgeWeight(from, initialTo);
        removeDirectedEdge(from, initialTo);
        addDirectedEdge(from, newTo, weight);
        System.out.printf("Directed edge of weight %f has been updated from (%d, %d) to (%d, %d).\n", weight, from, initialTo, from, newTo);
        return true;
    }
    public boolean updateDirectedEdgeWeight(int from, int to, double weight){
        if (!doesNodeExist(from)){
            System.out.printf("From node with primary key %d does not exist.\n", from);
            return false;
        }
        if (!doesNodeExist(to)){
            System.out.printf("To node with primary key %d does not exist.\n", to);
            return false;
        }
        if (!doesDirectedEdgeExist(from, to)){
            System.out.printf("Directed edge (%d, %d) does not exist.\n", from, to);
            return false;
        }
        double initialWeight = -1.00;
        for (Map.Entry<NodePair, Edge> entry: edges.entrySet()){
            if (entry.getKey().getFrom() == from && entry.getKey().getTo() == to){
                initialWeight = entry.getValue().getWeight();
                entry.getValue().setWeight(weight);
                break;
            }
        }
        System.out.printf("Weight of directed edge (%d, %d) has been updated from %f to %f.\n", from, to, initialWeight, weight);
        return true;
    }

    public boolean removeDirectedEdge(int from, int to){
        if (!doesNodeExist(from)){
            System.out.printf("From node of primary key %d does not exist.\n", from);
            return false;
        }
        if (!doesNodeExist(to)){
            System.out.printf("To node of primary key %d does not exist.\n", to);
            return false;
        }
        if (!doesDirectedEdgeExist(from, to)){
            System.out.printf("Directed edge (%d, %d) does not exist.\n", from, to);
            return false;
        }
        double weight = getDirectedEdgeWeight(from, to);
        Iterator<Map.Entry<NodePair, Edge>> iterator = edges.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<NodePair, Edge> entry = iterator.next();
            if (entry.getKey().getFrom() == from && entry.getKey().getTo() == to){
                iterator.remove();
                break;
            }
        }
        adjacencyList.get(from).remove((Object) to); // specify use .remove(Object o) instead of .remove(int index)
        System.out.printf("Directed edge (%d, %d) of weight %f has been removed.\n", from, to, weight);
        return true;
    }
    public boolean removeUndirectedEdge(int node1, int node2){
        if (!doesUndirectedEdgeExist(node1, node2)){
            System.out.printf("Undirected edge (%d, %d) does not exist.\n", node1, node2);
            if (!doesDirectedEdgeExist(node1, node2) || !doesDirectedEdgeExist(node2, node1)){
                if (!doesDirectedEdgeExist(node1, node2)){
                    System.out.printf("Directed edge (%d, %d) does not exist.\n", node1, node2);
                }
                if (!doesDirectedEdgeExist(node2, node1)){
                    System.out.printf("Directed edge (%d, %d) does not exist.\n", node2, node1);
                }
            }
            else {
                System.out.printf("Undirected edge (%d, %d) does not exist because weight of directed edge (%d, %d), %f does not match weight of directed edge (%d, %d), %f\n", node1, node2, node1, node2, getDirectedEdgeWeight(node1, node2), node2, node1, getDirectedEdgeWeight(node2, node1));
            }
            return false;
        }
        boolean result1 = removeDirectedEdge(node1, node2);
        boolean result2 = removeDirectedEdge(node2, node1);
        return result1 && result2;
    }

    public boolean clearEdges(int primaryKey){
        if (!doesNodeExist(primaryKey)){
            System.out.printf("Node of primary key %d does not exist.\n", primaryKey);
            return false;
        }
        Iterator<Map.Entry<NodePair, Edge>> iterator = edges.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<NodePair, Edge> entry = iterator.next();
            if (entry.getKey().getFrom() == primaryKey || entry.getKey().getTo() == primaryKey){
                iterator.remove();
            }
        }
        adjacencyList.get(primaryKey).clear();
        for (Map.Entry<Integer, ArrayList<Integer>> entry: adjacencyList.entrySet()){
            entry.getValue().remove((Object) primaryKey); // specify use .remove(Object o) instead of .remove(int index)
        }
        System.out.printf("Cleared all edges with association with node of primary key %d.\n", primaryKey);
        return true;
    }
    public void clearEdges(){
        edges = new HashMap<NodePair, Edge>();
        for (Map.Entry<Integer, ArrayList<Integer>> entry: adjacencyList.entrySet()){
            adjacencyList.put(entry.getKey(), new ArrayList<Integer>());
        }
        System.out.println("Cleared all edges of the graph.");
    }
    public void resetGraph(){
        nodes = new HashMap<Integer, Node>();
        edges = new HashMap<NodePair, Edge>();
        adjacencyList = new HashMap<Integer, ArrayList<Integer>>();
        System.out.println("Cleared all nodes and edges of the graph.");
    }

    public String generateAdjList(){
        StringBuilder adjList = new StringBuilder();
        adjList.append("Adjacency List:\n");
        for (Map.Entry<Integer, ArrayList<Integer>> entry: adjacencyList.entrySet()){
            adjList.append(String.format("%4d: %s\n", entry.getKey(), entry.getValue()));
        }
        adjList.append("\n");

        return adjList.toString();
    }
    public String generateAdjMatrix(){
        StringBuilder adjMatrix = new StringBuilder();
        adjMatrix.append("Adjacency Matrix:\n");
        if (nodes.size() != 0) {
            adjMatrix.append("      |");
            for (Map.Entry<Integer, Node> entry: nodes.entrySet()){
                adjMatrix.append(String.format(" %6d |", entry.getKey()));
            }
            adjMatrix.append("\n");
            for (Map.Entry<Integer, ArrayList<Integer>> i: adjacencyList.entrySet()){
                adjMatrix.append(String.format(" %4d |", i.getKey()));
                for (Map.Entry<Integer, Node> j: nodes.entrySet()){
                    if (i.getValue().contains((Integer) j.getKey())){
                        double weight = -1.00;
                        for (Map.Entry<NodePair, Edge> k: edges.entrySet()){
                            if (k.getKey().getFrom() == i.getKey() && k.getValue().getTo() == j.getValue()){
                                weight = k.getValue().getWeight();
                            }
                        }
                        adjMatrix.append(String.format(" %6.2f |", weight));
                    }
                    else {
                        adjMatrix.append(String.format(" %6d |", 0));
                    }
                }
                adjMatrix.append("\n");
            }
        }
        adjMatrix.append("\n");

        return adjMatrix.toString();
    }
    public void printAdjList(){
        StringBuilder adjList = new StringBuilder();
        adjList.append(generateAdjList());

        System.out.println(adjList);
    }
    public void printAdjMatrix(){
        StringBuilder adjMatrix = new StringBuilder();
        adjMatrix.append(generateAdjMatrix());
        
        System.out.println(adjMatrix);
    }

    @Override
    public String toString(){
        StringBuilder graph = new StringBuilder();
        graph.append("\nGraph:\n");
        graph.append("----------------------------------------\n");

        // Nodes
        graph.append("Nodes:\n");
        graph.append(nodes.toString());
        graph.append("\n\n");

        // Edges
        graph.append("Directed Edges:\n");
        graph.append(edges.toString());
        graph.append("\n\n");

        // Adjacency List
        graph.append(generateAdjList());

        // Adjacency Matrix
        graph.append(generateAdjMatrix());

        graph.append("----------------------------------------\n");
        return graph.toString();
    }

    public class Node {
        private int primaryKey;
        private String value;
        public Node(int primaryKey, String value){
            this.primaryKey = primaryKey;
            this.value = value;
        }
        public int getPrimaryKey(){
            return primaryKey;
        }
        public String getValue(){
            return value;
        }
        public void setValue(String value){
            this.value = value;
        }
        public ArrayList<Node> getNeighbors(){
            ArrayList<Node> neighbors = new ArrayList<>();
            ArrayList<Integer> neighborsKeys = adjacencyList.get(primaryKey);
            for (Integer key: neighborsKeys){
                neighbors.add(nodes.get(key));
            }
            return neighbors;
        }
        @Override
        public String toString(){
            return String.format("[%d => value = %s]", getPrimaryKey(), getValue());
        }
    }
    public class Edge {
        private Node from, to;
        private double weight;
        public Edge(Node from, Node to, double weight){
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
        public Node getFrom(){
            return from;
        }
        public Node getTo(){
            return to;
        }
        public double getWeight(){
            return weight;
        }
        public void setWeight(double weight){
            this.weight = weight;
        }
        @Override
        public String toString(){
            return String.format("[(%s, %s) => weight = %f]", getFrom(), getTo(), getWeight());
        }
    }
    class NodePair{
        private int from, to;
        public NodePair(int primaryKey_fromNode, int primaryKey_toNode){
            this.from = primaryKey_fromNode;
            this.to = primaryKey_toNode;
        }
        public int getFrom(){
            return from;
        }
        public int getTo(){
            return to;
        }
        
        @Override
        public String toString(){
            return String.format("(%d, %d)", from, to);
        }
    }
}