import java.util.Arrays;

class Solution {

    public int findCircleNum(int[][] adjMatrix) {
        int n = adjMatrix.length; // Number of cities 

        // To store the final count of provinces (connected components)
        int provincesCount = 0;

        // Initialize DSU (Disjoint Set Union)
        int[] size = new int[n];    // size[i] = size of the set whose root is i
        int[] parent = new int[n];  // parent[i] = parent of node i in the union-find tree

        // Initially, every city is its own parent (self root) and size 1
        Arrays.fill(size, 1);
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        // Traverse the isConnected matrix to group connected cities
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // If city i and j are directly connected
                // and they are not already in the same group (i.e., not part of same province)
                if (adjMatrix[i][j] == 1 && !isSameSet(parent, i, j)) {
                    // Merge the sets (i.e., group the two cities)
                    merge(parent, size, i, j);
                }
            }
        }

        // After grouping all cities, count how many root node are there
        for (int i = 0; i < n; i++) {
            // A node is considered root node of a set if it is parent of itself
            if (parent[i] == i) {
                provincesCount++;
            }
        }

        return provincesCount; // Final number of provinces
    }

    
    // Merge two sets containing u and v using Union by Size optimization.
    // We attach the smaller set under the larger set to keep trees shallow.
    private void merge(int[] parent, int[] size, int u, int v) {
        // Find root of each set
        int uRootNode = findRootNode(parent, u);
        int vRootNode = findRootNode(parent, v);

        // Attach smaller tree under larger tree and update the size
        if (size[uRootNode] < size[vRootNode]) {
            parent[uRootNode] = vRootNode;
            size[vRootNode] += size[uRootNode];
        } else {
            parent[vRootNode] = uRootNode;
            size[uRootNode] += size[vRootNode];
        }
    }

    
    // Check if nodes u and v belong to the same set
    private boolean isSameSet(int[] parent, int u, int v) {
        return findRootNode(parent, u) == findRootNode(parent, v);
    }

    
    // Find the root of node u with path compression
    // This optimization flattens the tree, speeding up future lookups
    private int findRootNode(int[] parent, int u) {
        if (parent[u] == u) return u; // u is the root of its own set

        // Recursively find the root and apply path compression
        parent[u] = findRootNode(parent, parent[u]);
        return parent[u];
    }
}
