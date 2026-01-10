class Solution {
    public boolean isCyclic(int V, ArrayList<ArrayList<Integer>> adjList) {
        // for directed graph, we need to keep track of visited nodes and path visited nodes
        // visited array will keep track of all the nodes which are visited so that we don't start dfs from those nodes again
        boolean[] visited = new boolean[V];
        // pathVisited array will keep track of all the nodes which are visited in the current path 
        // in directed graph, not all the nodes are reachable from a single node and a node could be visited only through a specific path.
        // but there could be a node which could be reachable through multiple paths and if we visit that node through different paths then that node would appear as already visited.
        // due to which the algo will consider that there is a cycle in the graph which is not true.
        // so to avoid this, we need to keep track of the nodes which are visited in the current path and if we visit that node again in the current path then there is a cycle.
        boolean[] pathVisited = new boolean[V];
        boolean hasCycle = false;
        for(int i=0; i<V; i++) {
            // if the current node is not visited then start dfs from that node
            if(!visited[i]) {
                hasCycle = hasCycle || detectCycle(adjList, pathVisited, visited, i);
            } 
        }
        return hasCycle;
    }
    
    // dfs traversal to detect cycle in a single connected component
    private boolean detectCycle(ArrayList<ArrayList<Integer>> adjList, boolean[] pathVisited, boolean[] visited, int curr) {
        // if the current node is already visited in current path, then there is a cycle so return true
        if(pathVisited[curr]) return true;
        // marking the current node as globally visited so that we don't start dfs from this node since we know the outcome
        visited[curr] = true;
        // marking the current node as path visited so that we don't visit it again in the current path
        pathVisited[curr] = true;
        boolean hasCycle = false;
        for(Integer neighbour : adjList.get(curr)) {
            // since in directed graph there is no concept of back edge, so we don't need to check for parent node
            // traverse all the neighbours of the current node and if any of the neighbour has cycle then hasCycle will be true
            hasCycle = hasCycle || detectCycle(adjList, pathVisited, visited, neighbour);
        }
        // after traversing all the neighbours of the current node, mark the current node as false in pathVisited array
        // so that we might visit this node again through some other path and if this node would be maked as visited then the algo will 
        // consider there is a cycle which is not true
        pathVisited[curr] = false;
        return hasCycle;
    }  
}