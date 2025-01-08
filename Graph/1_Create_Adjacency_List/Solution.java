import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution {

    public HashMap<Integer, List<Integer>> createAdjacencyListOfUndirectedGraph(int[][] edges) {

        // Create a HashMap to store the adjacency list of the graph
        HashMap<Integer, List<Integer>> adjacencyList = new HashMap<>();

        // Iterate through the edges array
        for(int i=0; i<edges.length; i++) {

            // Get the two nodes of the edge
            int node1 = edges[i][0];
            int node2 = edges[i][1];

            // Get the adjacent nodes list of node1 and node2
            // If the node is not present in the adjacency list, create an empty list
            List<Integer> node1AdjacentNodes = adjacencyList.getOrDefault(node1, new ArrayList<>());
            List<Integer> node2AdjacentNodes = adjacencyList.getOrDefault(node2, new ArrayList<>());

            // Add node2 to the adjacent nodes list of node1 and vice versa
            // since the graph is undirected, we need to add both the nodes to each other's adjacent nodes list
            node1AdjacentNodes.add(node2);
            node2AdjacentNodes.add(node1);

            // Update the adjacency list with the new adjacent nodes list
            adjacencyList.put(node1, node1AdjacentNodes);
            adjacencyList.put(node2, node2AdjacentNodes);
        }
        return adjacencyList;
    }

    public HashMap<Integer, List<Integer>> createAdjacencyListOfDirectedGraph(int[][] edges) {

        // Create a HashMap to store the adjacency list of the graph
        HashMap<Integer, List<Integer>> adjacencyList = new HashMap<>();

        // Iterate through the edges array
        for(int i=0; i<edges.length; i++) {

            // Get the two nodes of the edge
            int node1 = edges[i][0];
            int node2 = edges[i][1];

            // Get the adjacent nodes list of node1
            // If the node is not present in the adjacency list, create an empty list
            List<Integer> node1AdjacentNodes = adjacencyList.getOrDefault(node1, new ArrayList<>());

            // Add node2 to the adjacent nodes list of node1
            // since the graph is directed, we only need to add node2 to the adjacent nodes list of node1 and not vice versa
            node1AdjacentNodes.add(node2);
            
            // Update the adjacency list with the new adjacent nodes list
            adjacencyList.put(node1, node1AdjacentNodes);
        }
        return adjacencyList;
    }
}