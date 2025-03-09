import java.util.Arrays;

class Solution {
    static int[] bellmanFord(int n, int[][] edges, int src) {
        // Step 1: Initialize distances array with a large value (∞ equivalent)
        int[] distances = new int[n];
        Arrays.fill(distances, 100000000); // Represents "infinity" for unreachable nodes
        distances[src] = 0; // Distance from source to itself is always 0

        // Perform (n-1) iterations to relax all edges
        // In each iteration, we go through all edges and update shortest distances
        // (n-1) iterations ensure correct shortest paths
        for (int i = 0; i < n - 1; i++) {  
            for (int j = 0; j < edges.length; j++) {
                int u = edges[j][0]; // Start node of the edge
                int v = edges[j][1]; // End node of the edge
                int cost = edges[j][2]; // Edge weight

                // If the source node of the edge has not been reached yet, skip
                if (distances[u] == 100000000) continue;

                // Relaxation: If taking edge (u → v) results in a shorter path to v, update it
                int prevDistance = distances[v];
                int newDistance = distances[u] + cost;
                if (newDistance < prevDistance) {
                    distances[v] = newDistance;
                }
            }
        }

        // Check for negative weight cycles
        // If we can still relax an edge, then a negative cycle exists
        for (int j = 0; j < edges.length; j++) {
            int u = edges[j][0];
            int v = edges[j][1];
            int cost = edges[j][2];

            if (distances[u] == 100000000) continue;

            int prevDistance = distances[v];
            int newDistance = distances[u] + cost;
            if (newDistance < prevDistance) {
                // Negative weight cycle detected
                return new int[]{-1};
            }
        }

        // Return the shortest distances from the source to all nodes
        return distances;
    }
}
