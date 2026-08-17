class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] ans = null;
        int[] parents = new int[n];
        int[] sizes = new int[n];

        // Initially every node belongs to its own connected component.
        for(int i=0; i<n; i++) {
            parents[i] = i;
            sizes[i] = 1;
        }

        for(int i=0; i<n; i++) {
            // Convert the 1-based node labels from the input to 0-based
            // indices used by the DSU arrays.
            int uRootNode = getRootNode(parents, edges[i][0]-1);
            int vRootNode = getRootNode(parents, edges[i][1]-1);

            // If both endpoints already have the same root, they are
            // already connected. Adding this edge therefore creates a cycle,
            // making it the redundant edge.
            if(uRootNode==vRootNode) {
                ans = edges[i];
                break;
            }

            // Otherwise, this edge connects two previously separate
            // components, so merge them.
            else {
                merge(parents, sizes, uRootNode, vRootNode);
            }
        }

        return ans;
    }


    private void merge(int[] parents, int[] sizes, int u, int v) {
        int uSize = sizes[u];
        int vSize = sizes[v];

        // Attach the smaller component under the larger component
        // to keep the DSU tree shallow.
        if(uSize<vSize) {
            parents[u] = v;
            sizes[v] += uSize;
        }
        else {
            parents[v] = u;
            sizes[u] += vSize;
        }
    }


    private int getRootNode(int[] parents, int n) {

        // A node whose parent is itself is the representative
        // of its connected component.
        if(parents[n]==n) return n;

        // Path compression:
        // directly connect this node to the root so future find operations
        // on the same path become faster.
        parents[n] = getRootNode(parents, parents[n]);

        return parents[n];
    }
}