class Solution {

    public int[][] createAdjacencyMatrixOfUndirectedGraph(int n, int[][] edges) {

        // Create a 2D array to store the adjacency matrix of the graph of size n x n
        // n being the number of nodes in the graph
        int[][] adjacencyMatrix = new int[n][n];

        for(int i=0; i<edges.length; i++) {

            // Get the two nodes of the edge
            int node1 = edges[i][0];
            int node2 = edges[i][1];

            // Update the adjacency matrix with the edge from node1 to node2 and vice versa by setting the value to 1
            // This value 1 indicates that there is an edge between one node to another
            // Since the graph is undirected, we need to update the adjacency matrix for both node1 and node2
            adjacencyMatrix[node1][node2] = 1;
            adjacencyMatrix[node2][node1] = 1;
            
        }
        return adjacencyMatrix;
    }

    public int[][] createAdjacencyMatrixOfDirectedGraph(int n, int[][] edges) {

        // Create a 2D array to store the adjacency matrix of the graph of size n x n
        // n being the number of nodes in the graph
        int[][] adjacencyMatrix = new int[n][n];

        for(int i=0; i<edges.length; i++) {

            // Get the two nodes of the edge
            int node1 = edges[i][0];
            int node2 = edges[i][1];

            // Update the adjacency matrix with the edge from node1 to node2 by setting the value to 1
            // This value 1 indicates that there is an edge between one node to another
            // Since the graph is directed, we only need to update the adjacency matrix for node1
            adjacencyMatrix[node1][node2] = 1;
            
        }
        return adjacencyMatrix;
    }
}