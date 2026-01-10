import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public int minMaxWeight(int n, int[][] edges, int threshold) {
        // We aim to find a spanning tree (or connected subgraph) that connects all 'n' nodes
        // such that we can minimize the **maximum edge weight** used in this connection.
        // If all nodes are not reachable from node 0 using such edges, return -1.
        
        int maxWeight = -1;     // This will track the maximum edge weight used in the traversal
        int nodeCount = 0;      // Counts how many nodes we can reach from node 0

        // Build adjacency list to represent the undirected graph
        List<int[]>[] adjList = getAdjList(n, edges);

        boolean[] visited = new boolean[n]; // Track visited nodes to avoid cycles

        // PriorityQueue stores edges as (destination node, edge weight), sorted by weight
        // so that while connecting the nodes, we would first connect nodes with smaller edge weight so as to minimize the max edge weight
        PriorityQueue<int[]> queue = new PriorityQueue<>((edge1, edge2) -> {
            int weight1 = edge1[1];
            int weight2 = edge2[1];
            return Integer.compare(weight1, weight2);
        });

        // Start from node 0; use -1 as the dummy edge weight
        queue.add(new int[] {0, -1});

        while (!queue.isEmpty()) {
            int node = queue.peek()[0];         // Get node to process
            int edgeWeight = queue.peek()[1];   // Edge weight used to reach this node
            queue.poll();

            if (visited[node]) continue;        // Skip if already processed

            visited[node] = true;               // Mark current node as visited
            nodeCount++;                        // Increase count of reachable nodes
            maxWeight = Math.max(maxWeight, edgeWeight); // Update max weight used so far

            // Add all adjacent edges of current node into the queue
            for (int[] edge : adjList[node]) {
                queue.add(edge); // Each edge is {neighbor node, weight}
            }
        }

        // If we reached all 'n' nodes, return the max weight used in the connection
        // Otherwise, return -1 meaning it's impossible to connect all nodes from node 0
        return (nodeCount == n) ? maxWeight : -1;
    }

    // Helper function to build adjacency list for the graph
    private List<int[]>[] getAdjList(int n, int[][] edges) {
        List<int[]>[] adjList = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adjList[i] = new ArrayList<>();
        }
        // since condition is all nodes should be able to reach to node '0', we would reverse the edge direction
        // so that condition would become all nodes should be reachable from node '0' giving us only one starting node i.e. '0'
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int weight = edges[i][2];
            adjList[v].add(new int[] {u, weight});
        }
        return adjList;
    }
}
