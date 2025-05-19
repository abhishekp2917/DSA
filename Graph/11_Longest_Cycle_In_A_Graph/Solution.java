class Solution {
    // Main function to find the length of the longest cycle in a directed graph
    // represented as an array where edges[i] points to the next node or -1 if none.
    public int longestCycle(int[] edges) {
        // visited[i] = true means node i has been visited at least once in any DFS
        boolean[] visited = new boolean[edges.length];
        
        // pathVisitedOrder[i] stores the DFS visit order for node i in the current path
        // pathVisitedOrder[i] = N means the current node comes in Nth position from starting node in current path
        // If 0, it means node i is not currently in the DFS path.
        int[] pathVisitedOrder = new int[edges.length];
        
        int longestLength = -1;  // Result variable, -1 means no cycle found yet
        
        // Iterate over all nodes to start DFS from unvisited nodes (handle disconnected graph)
        for(int i = 0; i < edges.length; i++) {
            if(!visited[i]) {
                // DFS starting from node i, order starts from 1
                longestLength = Math.max(longestLength, dfs(edges, visited, pathVisitedOrder, i, 1));
            }
        }
        
        return longestLength;
    }

    private int dfs(int[] edges, boolean[] visited, int[] pathVisitedOrder, int curr, int order) {
        // If current node is visited before AND not part of current path (pathVisitedOrder[curr] == 0)
        // or no cycle from this node as it is already fully processed
        // that means there is no cycle in current path
        if ((visited[curr] && pathVisitedOrder[curr] == 0) || edges[curr] == -1) {
            return -1;  
        }

        // If current node is already in current DFS path (pathVisitedOrder[curr] != 0) then cycle detected!
        // Cycle length = current DFS position - position when node was first visited in this path
        if (pathVisitedOrder[curr] != 0) {
            return order - pathVisitedOrder[curr];
        }

        // Mark current node as visited globally and record its order in current path
        visited[curr] = true;
        pathVisitedOrder[curr] = order;

        // DFS recurse on the next node pointed by edges[curr]
        int length = dfs(edges, visited, pathVisitedOrder, edges[curr], order + 1);

        // Backtrack: reset current node's order in current path so
        // it can be re-used if visited from another DFS path
        pathVisitedOrder[curr] = 0;

        return length;  // Return the detected cycle length or -1 if none
    }
}
