/**
 * Disjoint Set (Union-Find) data structure.
 * 
 * Supports efficient operations:
 * - find(): To check if two elements are in the same set.
 * - union(): To merge two disjoint sets into one.
 * 
 * Optimizations used:
 * - Path Compression (in `findRootNode`) to flatten trees.
 * - Union by Rank and Union by Size to keep trees shallow.
 */
class DisjointSet {

    private int[] parent; // Tracks the parent of each node
    private int[] rank;   // Tracks the rank (depth) of each node's tree for union by rank
    private int[] size;   // Tracks the size of each set for union by size

    /**
     * Constructor: Initializes n disjoint sets (0 to n-1).
     * Initially, each element is its own parent (self loop).
     */
    public DisjointSet(int n) {
        this.parent = new int[n];
        this.rank = new int[n];
        this.size = new int[n];
        for (int i = 0; i < n; i++) {
            this.parent[i] = i;   // Each node is initially its own parent
            this.size[i] = 1;     // Each set initially has size 1
            // rank[i] defaults to 0, so no need to explicitly set it
        }
    }

    /**
     * Checks if two elements `u` and `v` are in the same set.
     * Returns true if their root parents are the same.
     */
    public boolean find(int u, int v) {
        return findRootNode(u) == findRootNode(v);
    }

    /**
     * Union by Rank merges the sets containing `u` and `v` based on tree height (rank).
     * The smaller tree is attached under the root of the larger tree to make sure merged tree height is short
     */
    public void unionByRank(int u, int v) {
        int uRootNode = findRootNode(u);
        int vRootNode = findRootNode(v);
        // If both elements already belong to the same set, do nothing
        if (uRootNode == vRootNode) return;
        int uRank = this.rank[uRootNode];
        int vRank = this.rank[vRootNode];
        // Attach the shorter tree under the taller one
        if (uRank < vRank) {
            this.parent[uRootNode] = vRootNode;
        } else if (uRank > vRank) {
            this.parent[vRootNode] = uRootNode;
        } else {
            // If ranks are equal, arbitrarily attach v to u and increase u's rank
            this.parent[vRootNode] = uRootNode;
            this.rank[uRootNode]++;
        }
    }

    /**
     * Union by Size merges the sets containing `u` and `v` based on number of elements.
     * The smaller set is attached under the larger set.
     */
    public void unionBySize(int u, int v) {
        int uRootNode = findRootNode(u);
        int vRootNode = findRootNode(v);
        // If both elements already belong to the same set, do nothing
        if (uRootNode == vRootNode) return;
        int uSize = this.size[uRootNode];
        int vSize = this.size[vRootNode];
        // Attach the smaller set under the larger one and update the size
        if (uSize < vSize) {
            this.parent[uRootNode] = vRootNode;
            this.size[vRootNode] += uSize;
        } else {
            this.parent[vRootNode] = uRootNode;
            this.size[uRootNode] += vSize;
        }
    }

    /**
     * Finds the representative (root parent) of a node using Path Compression.
     * This optimization flattens the structure of the tree for fast future queries.
     */
    private int findRootNode(int k) {
        if (this.parent[k] == k) return k; // If node is its own parent, return it

        // Path Compression: update parent to point directly to the root
        return this.parent[k] = findRootNode(this.parent[k]);
    }
}
