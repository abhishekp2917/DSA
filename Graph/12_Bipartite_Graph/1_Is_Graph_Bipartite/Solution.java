class Solution {
    public boolean isBipartite(int[][] adjList) {
        int n = adjList.length;
        boolean[] visited = new boolean[n];
        int[] nodeValue = new int[n];
        // declare a variable to store the result of the dfs of the nodes
        boolean result = true;
        for(int i=0; i<n; i++) {
            // if the node is not visited, then call the dfs with the node value as 1
            if(!visited[i]) {
                // update the result with the result of the dfs of the node using AND operation because if any of the node is not bipartite, then the whole graph is not bipartite
                result = result && dfs(adjList, i, -1, 1, visited, nodeValue);
            } 
        }
        return result;
    }

    private boolean dfs(int[][] adjList, int curr, int parent, int currValue, boolean[] visited, int[] nodeValue) {
        // if the node is already visited, then it's value should be same as what we are trying to assign
        // if it's not same, then it's not bipartite so return false else return true
        if(visited[curr]) {
            if(nodeValue[curr]==-currValue) return false;
            return true;
        }
        // mark the node as visited and assign the value
        visited[curr] = true;
        nodeValue[curr] = currValue;
        // declare a variable to store the result of the dfs of the neighbours
        boolean result = true;
        // iterate over the neighbours execpt for the parent and call the dfs
        for(int neighbour : adjList[curr]) {
            if(neighbour==parent) continue;
            // alternate the currValue for the neighbour node and call the dfs
            // use AND operation to store the result of the dfs of the neighbours because if any of the neighbour is not bipartite, then the whole graph is not bipartite
            result = result && dfs(adjList, neighbour, curr, -currValue, visited, nodeValue);
        }
        return result;
    }
}


