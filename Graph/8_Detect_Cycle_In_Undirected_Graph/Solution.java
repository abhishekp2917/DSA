class Solution {
    public boolean isCycle(ArrayList<ArrayList<Integer>> adjList) {
        boolean[] visited = new boolean[adjList.size()];
        boolean hasCycle = false;
        // since the graph can be disconnected, we need to check for cycle in all the connected components
        // if there would have been only one connected component, then we could have started from only one node
        for(int i=0; i<adjList.size(); i++) {
            // to make sure that we are not traversing the same connected component again, we need to check if the node is already visited
            // update the hasCycle variable with the result of the detectCycle function
            // if there is a cycle in any of the connected components, then the hasCycle will be become true
            if(!visited[i]) hasCycle = hasCycle || detectCycle(adjList, visited, i, -1);
        }
        return hasCycle;
    }
    
    // dfs traversal to detect cycle in a single connected component
    private boolean detectCycle(ArrayList<ArrayList<Integer>> adjList, boolean[] visited, int curr, int parent) {
        // if the current node is already visited, then there is a cycle so return true
        if(visited[curr]) return true;

        // else mark the current node as visited and traverse all its neighbours
        visited[curr]=true;

        // declare a boolean variable to store if there is a cycle 
        boolean hasCycle = false;
        for(Integer neighbour : adjList.get(curr)) {
            // traverse all the neighbours except the parent and update the hasCycle variable
            // if there is a cycle in any of the neighbours, then the hasCycle will be true
            if(neighbour!=parent) hasCycle = hasCycle || detectCycle(adjList, visited, neighbour, curr);
        }
        // return the hasCycle variable
        return hasCycle;
    }  
}