import java.util.Arrays;

class Solution {
    
    // Main function to find the minimum cost of the Minimum Spanning Tree (MST) using Kruskal's algorithm
    static int kruskalsMST(int V, int[][] edges) {
        int minCost = 0; // This will store the final weight of the MST

        int[] parent = new int[V];  // Parent array for DSU: parent[i] = parent of node i
        int[] size = new int[V];    // Size array for union by size optimization

        // Initially, each node is its own parent (self-rooted), and size is 1
        Arrays.fill(size, 1);
        for (int i = 0; i < V; i++) {
            parent[i] = i;
        }

        // Sort all edges based on their weights (non-decreasing order) so that we will connect the edges with minimum cost first
        Arrays.sort(edges, (edge1, edge2) -> Integer.compare(edge1[2], edge2[2]));

        // Iterate through all sorted edges
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];   // Start vertex of edge
            int v = edges[i][1];   // End vertex of edge
            int cost = edges[i][2]; // Weight of edge

            // If u and v are not in the same set then merge the two set making a single graph
            if (!isSameSet(parent, u, v)) {
                merge(parent, size, u, v);
                // After merging, add edge cost to MST total weight
                minCost += cost;
            }
        }

        return minCost; // Return total weight of MST
    }

    // Union operation: merge two sets using union by size optimization
    private static void merge(int[] parent, int[] size, int u, int v) {
        int uRootNode = findRootNode(parent, u); // Find root of set containing u
        int vRootNode = findRootNode(parent, v); // Find root of set containing v

        // Attach smaller tree under the root of the larger tree
        // Update the size of the root node to which other root node got attached
        if (size[uRootNode] < size[vRootNode]) {
            parent[uRootNode] = vRootNode;
            size[vRootNode] += size[uRootNode];
        } else {
            parent[vRootNode] = uRootNode;
            size[uRootNode] += size[vRootNode];
        }
    }

    // Check if two nodes belong to the same set
    private static boolean isSameSet(int[] parent, int u, int v) {
        return findRootNode(parent, u) == findRootNode(parent, v);
    }

    // Find operation with path compression
    // Returns the root node of the set containing u
    private static int findRootNode(int[] parent, int u) {
        if (parent[u] == u) return u; // u is root of its set

        // Path compression: update parent[u] to point directly to root
        parent[u] = findRootNode(parent, parent[u]);
        return parent[u];
    }
}
