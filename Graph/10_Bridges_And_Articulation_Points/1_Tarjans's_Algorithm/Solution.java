import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

class Solution {

    private int[] ids;              // Discovery time (index) of each node
    private int[] lowIds;           // Lowest reachable index (low-link) from the node
    private int time;               // Time counter 
    private boolean[] onStack;      // Flag to check if a node is on the current DFS path (stack)
    private boolean[] visited;      // Whether a node has been visited in DFS
    private Stack<Integer> stack;   // Stack to maintain current path in DFS
    private ArrayList<ArrayList<Integer>> result;  // Stores list of SCCs

    // Function to return all Strongly Connected Components (SCCs)
    public ArrayList<ArrayList<Integer>> tarjans(int n, ArrayList<ArrayList<Integer>> adj) {
        this.ids = new int[n];
        this.lowIds = new int[n];
        this.time = 0;  
        this.onStack = new boolean[n];
        this.visited = new boolean[n];
        this.stack = new Stack<>();
        this.result = new ArrayList<>();

        // Start DFS from every unvisited node
        for (int i = 0; i < n; i++) {
            if (this.visited[i]) continue;
            dfs(adj, i);
        }

        // Sorting each SCC and all SCCs for lexicographic order (optional)
        Collections.sort(this.result, (comp1, comp2) -> Integer.compare(comp1.get(0), comp2.get(0)));
        return this.result;
    }

    // Core DFS function used to explore the graph and detect SCCs
    private void dfs(ArrayList<ArrayList<Integer>> adj, int curr) {
        // Push current node on the DFS stack so that we would know which nodes are present in current path
        this.stack.push(curr);
        this.onStack[curr] = true;

        // Assign discovery id and lowlink value to the current node
        this.ids[curr] = this.lowIds[curr] = this.time++;
        this.visited[curr] = true;

        // Explore all neighbors of current node
        for (int neighbour : adj.get(curr)) {
            // if neighbour node is not visited then traverse that node
            if (!this.visited[neighbour]) {
                dfs(adj, neighbour);
                // After visiting child, update current node’s lowlink based on child while backtracking
                this.lowIds[curr] = Math.min(this.lowIds[curr], this.lowIds[neighbour]);
            } 
            // if node is already visited but it is on stack, that means we visited a node which is part of current path
            // so update node’s lowlink based on the visited node
            else if (onStack[neighbour]) {
                this.lowIds[curr] = Math.min(this.lowIds[curr], this.lowIds[neighbour]);
            }
        }

        // If lowlink == id, current node is the root of a SCC
        // so we would fill all the conected components in a list
        if (this.ids[curr] == this.lowIds[curr]) {
            // Start forming a new SCC
            ArrayList<Integer> components = new ArrayList<>();
            while (true) {
                int node = this.stack.pop();        // Pop until we reach the root
                this.onStack[node] = false;
                components.add(node);
                if (node == curr) break;
            }
            Collections.sort(components);     // Sort the component nodes (optional)
            this.result.add(components);           // Add the complete SCC to the result
        }
    }
}
