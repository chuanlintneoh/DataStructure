package Graph;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Runner {
    public static void runner(){
        Graph graph = new Graph();
        Scanner sc = new Scanner(System.in);
        Map<String, String> commands = new LinkedHashMap<>();
        commands.put("n", "Add node");
        commands.put("ns", "Add nodes");
        commands.put("cn", "Update node value");
        commands.put("xn", "Delete node");
        commands.put("xns", "Delete nodes");
        commands.put("d", "Add directed edge");
        commands.put("ds", "Add directed edges");
        commands.put("u", "Add undirected edge");
        commands.put("us", "Add undirected edges");
        commands.put("cdf", "Update directed edge from");
        commands.put("cdt", "Update directed edge to");
        commands.put("cdw", "Update directed edge weight");
        commands.put("xd", "Remove directed edge");
        commands.put("xu", "Remove undirected edge");
        commands.put("bfs", "Breadth-First Search traverse");
        commands.put("dfs", "Depth-First Search traverse");
        commands.put("xdn", "Clear edges of a node");
        commands.put("xds", "Clear all edges");
        commands.put("xx", "Clear all nodes and edges");
        commands.put("p", "Print graph");
        commands.put("pl", "Print adjacency list");
        commands.put("pm", "Print adjacency matrix");
        commands.put("q", "Quit");
        StringBuilder commandBuilder = new StringBuilder();
        commandBuilder.append("----------------------------------------\n");
        commandBuilder.append("\n[Commands]\n");
        int commandIndex = 1;
        for (Map.Entry<String, String> entry: commands.entrySet()){
            commandBuilder.append(String.format("%2d | %3s: %s\n", commandIndex, entry.getKey(), entry.getValue()));
            commandIndex++;
        }
        String command = commandBuilder.toString();

        System.out.println(command);
        System.out.print("Enter: ");
        String input = sc.nextLine();
        while (!input.equals("q")){
            switch (input) {
                case "n": // add node
                    System.out.print("Enter primary key, value: ");
                    String nodeDetail = sc.nextLine();
                    String[] nodeDetails = nodeDetail.split(", ");
                    if (nodeDetails.length != 2){
                        System.out.printf("Mismatch node details [%d]. Please try again.\n", nodeDetails.length);
                        break;
                    }
                    graph.addNode(Integer.parseInt(nodeDetails[0]), nodeDetails[1]);
                    break;
                case "ns": // add nodes
                    System.out.print("Enter primary_key1, value1, primary_key2, value2, ...: ");
                    String nodesDetail = sc.nextLine();
                    String[] nodesDetails = nodesDetail.split(", ");
                    if (nodesDetails.length % 2 != 0){
                        System.out.printf("Mismatch node details [%d]. Please try again.\n", nodesDetails.length);
                        break;
                    }
                    int[] primaryKeys = new int[nodesDetails.length/2];
                    String[] values = new String[nodesDetails.length/2];
                    for (int i = 0; i < primaryKeys.length; i++){
                        primaryKeys[i] = Integer.parseInt(nodesDetails[2*i]);
                        values[i] = nodesDetails[2*i + 1];
                    }
                    graph.addNodes(primaryKeys, values);
                    break;
                
                case "cn": // update node value
                    System.out.print("Enter primary key, value: ");
                    String updatedNodeDetail = sc.nextLine();
                    String[] updatedNodeDetails = updatedNodeDetail.split(", ");
                    if (updatedNodeDetails.length != 2){
                        System.out.printf("Mismatch update node value details [%d]. Please try again.\n", updatedNodeDetails.length);
                        break;
                    }
                    graph.updateNodeValue(Integer.parseInt(updatedNodeDetails[0]), updatedNodeDetails[1]);
                    break;
                case "xn": // delete node
                    System.out.print("Enter primary key: ");
                    String deleteNode = sc.nextLine();
                    int deleteNodeKey = Integer.parseInt(deleteNode);
                    graph.deleteNode(deleteNodeKey);
                    break;
                case "xns": // delete nodes
                    System.out.print("Enter primary_key1, primary_key2, ...: ");
                    String deleteNodesKey = sc.nextLine();
                    String[] deleteNodes = deleteNodesKey.split(", ");
                    int[] deleteNodesKeys = new int[deleteNodes.length];
                    for (int i = 0; i < deleteNodes.length; i++){
                        deleteNodesKeys[i] = Integer.parseInt(deleteNodes[i]);
                    }
                    graph.deleteNodes(deleteNodesKeys);
                    break;
                
                case "d": // add directed edge
                    System.out.print("Enter from node primary key, to node primary key, weight: ");
                    String edgeDetail = sc.nextLine();
                    String[] edgeDetails = edgeDetail.split(", ");
                    if (edgeDetails.length != 3){
                        System.out.printf("Mismatch directed edge details [%d]. Please try again.\n", edgeDetails.length);
                        break;
                    }
                    graph.addDirectedEdge(Integer.parseInt(edgeDetails[0]), Integer.parseInt(edgeDetails[1]), Double.parseDouble(edgeDetails[2]));
                    break;
                case "ds": // add directed edges
                    System.out.print("Enter from_node_key1, to_node_key1, weight1, from_node_key2, to_node_key2, weight2, ...: ");
                    String edgesDetail = sc.nextLine();
                    String[] edgesDetails = edgesDetail.split(", ");
                    if (edgesDetails.length % 3 != 0){
                        System.out.printf("Mismatch directed edges details [%d]. Please try again.\n", edgesDetails.length);
                        break;
                    }
                    int[] from_node_keys = new int[edgesDetails.length/3];
                    int[] to_node_keys = new int[edgesDetails.length/3];
                    double[] weights = new double[edgesDetails.length/3];
                    for (int i = 0; i < from_node_keys.length; i++){
                        from_node_keys[i] = Integer.parseInt(edgesDetails[3*i]);
                        to_node_keys[i] = Integer.parseInt(edgesDetails[3*i + 1]);
                        weights[i] = Double.parseDouble(edgesDetails[3*i + 2]);
                    }
                    graph.addDirectedEdges(from_node_keys, to_node_keys, weights);
                    break;
                case "u": // add undirected edge
                    System.out.print("Enter node 1 primary key, node 2 primary key, weight: ");
                    String undirectedEdgeDetail = sc.nextLine();
                    String[] undirectedEdgeDetails = undirectedEdgeDetail.split(", ");
                    if (undirectedEdgeDetails.length != 3){
                        System.out.printf("Mismatch undirected edge details [%d]. Please try again.\n", undirectedEdgeDetails.length);
                        break;
                    }
                    graph.addUndirectedEdge(Integer.parseInt(undirectedEdgeDetails[0]), Integer.parseInt(undirectedEdgeDetails[1]), Double.parseDouble(undirectedEdgeDetails[2]));
                    break;
                case "us": // add undirected edges
                    System.out.print("Enter node1_key1, node2_key1, weight1, node1_key2, node2_key2, weight2, ...: ");
                    String undirectedEdgesDetail = sc.nextLine();
                    String[] undirectedEdgesDetails = undirectedEdgesDetail.split(", ");
                    if (undirectedEdgesDetails.length % 3 != 0){
                        System.out.printf("Mismatch undirected edges details [%d]. Please try again.\n", undirectedEdgesDetails.length);
                        break;
                    }
                    int[] node1_keys = new int[undirectedEdgesDetails.length/3];
                    int[] node2_keys = new int[undirectedEdgesDetails.length/3];
                    double[] undirectedWeights = new double[undirectedEdgesDetails.length/3];
                    for (int i = 0; i < node1_keys.length; i++){
                        node1_keys[i] = Integer.parseInt(undirectedEdgesDetails[3*i]);
                        node2_keys[i] = Integer.parseInt(undirectedEdgesDetails[3*i + 1]);
                        undirectedWeights[i] = Double.parseDouble(undirectedEdgesDetails[3*i + 2]);
                    }
                    graph.addUndirectedEdges(node1_keys, node2_keys, undirectedWeights);
                    break;
                
                case "cdf": // update directed edge from
                    System.out.print("Enter initial from node primary key, to node primary key, new from node primary key: ");
                    String updateFromDetail = sc.nextLine();
                    String[] updateFromDetails = updateFromDetail.split(", ");
                    if (updateFromDetails.length != 3){
                        System.out.printf("Mismatch update directed edge from details [%d]. Please try again.\n", updateFromDetails.length);
                        break;
                    }
                    graph.updateDirectedEdgeFrom(Integer.parseInt(updateFromDetails[0]), Integer.parseInt(updateFromDetails[1]), Integer.parseInt(updateFromDetails[2]));
                    break;
                case "cdt": // update directed edge to
                    System.out.print("Enter from node primary key, initial to node primary key, new to node primary key: ");
                    String updateToDetail = sc.nextLine();
                    String[] updateToDetails = updateToDetail.split(", ");
                    if (updateToDetails.length != 3){
                        System.out.printf("Mismatch update directed edge to details [%d]. Please try again.\n", updateToDetails.length);
                        break;
                    }
                    graph.updateDirectedEdgeTo(Integer.parseInt(updateToDetails[0]), Integer.parseInt(updateToDetails[1]), Integer.parseInt(updateToDetails[2]));
                    break;
                case "cdw": // update directed edge weight
                    System.out.print("Enter from node primary key, to node primary key, new weight: ");
                    String updateWeightDetail = sc.nextLine();
                    String[] updateWeightDetails = updateWeightDetail.split(", ");
                    if (updateWeightDetails.length != 3){
                        System.out.printf("Mismatch update directed edge weight details [%d]. Please try again.\n", updateWeightDetails.length);
                        break;
                    }
                    graph.updateDirectedEdgeWeight(Integer.parseInt(updateWeightDetails[0]), Integer.parseInt(updateWeightDetails[1]), Double.parseDouble(updateWeightDetails[2]));
                    break;
                
                case "xd": // remove directed edge
                    System.out.print("Enter from node primary key, to node primary key: ");
                    String removeDirectedEdgeDetail = sc.nextLine();
                    String[] removeDirectedEdgeDetails = removeDirectedEdgeDetail.split(", ");
                    if (removeDirectedEdgeDetails.length != 2){
                        System.out.printf("Mismatch remove directed edge details [%d]. Please try again.\n", removeDirectedEdgeDetails.length);
                        break;
                    }
                    graph.removeDirectedEdge(Integer.parseInt(removeDirectedEdgeDetails[0]), Integer.parseInt(removeDirectedEdgeDetails[1]));
                    break;
                case "xu": // remove undirected edge
                    System.out.print("Enter node 1 primary key, node 2 primary key: ");
                    String removeUndirectedEdgeDetail = sc.nextLine();
                    String[] removeUndirectedEdgeDetails = removeUndirectedEdgeDetail.split(", ");
                    if (removeUndirectedEdgeDetails.length != 2){
                        System.out.printf("Mismatch remove undirected edge details [%d]. Please try again.\n", removeUndirectedEdgeDetails.length);
                        break;
                    }
                    graph.removeUndirectedEdge(Integer.parseInt(removeUndirectedEdgeDetails[0]), Integer.parseInt(removeUndirectedEdgeDetails[1]));
                    break;

                case "bfs":
                    System.out.print("Enter root node primary key: ");
                    String bfs_rootNode = sc.nextLine();
                    int bfs_rootKey = Integer.parseInt(bfs_rootNode);
                    if (!graph.doesNodeExist(bfs_rootKey)){
                        System.out.printf("Root node of primary key %d does not exist.\n", bfs_rootKey);
                        break;
                    }
                    new BFS(graph.nodes.get(bfs_rootKey), true);
                    break;
                case "dfs":
                    System.out.print("Enter root node primary key: ");
                    String dfs_rootNode = sc.nextLine();
                    int dfs_rootKey = Integer.parseInt(dfs_rootNode);
                    if (!graph.doesNodeExist(dfs_rootKey)){
                        System.out.printf("Root node of primary key %d does not exist.\n", dfs_rootKey);
                        break;
                    }
                    new DFS(graph.nodes.get(dfs_rootKey), true);
                    break;
                
                case "xdn": // clear all edges of a node
                    System.out.print("Enter node primary key: ");
                    String removeEdgesNode = sc.nextLine();
                    int removeEdgesNodeKey = Integer.parseInt(removeEdgesNode);
                    graph.clearEdges(removeEdgesNodeKey);
                    System.out.println(graph);
                    break;
                case "xds": // clear all edges
                    graph.clearEdges();
                    System.out.println(graph);
                    break;
                case "xx": // clear all nodes and edges
                    graph.resetGraph();
                    System.out.println(graph);
                    break;
                
                case "p": // print graph
                    System.out.println(graph);
                    break;
                case "pl": // print adjacency list
                    graph.printAdjList();
                    break;
                case "pm": // print adjacency matrix
                    graph.printAdjMatrix();
                    break;

                default:
                    System.out.println("Invalid input!");
                    break;
            }
            System.out.println(command);
            System.out.print("Enter: ");
            input = sc.nextLine();
        }
        System.out.println("Terminating program...");
        System.out.println(graph);
        sc.close();
    }
    public static void main(String[] args) {
        runner();
    }
}
