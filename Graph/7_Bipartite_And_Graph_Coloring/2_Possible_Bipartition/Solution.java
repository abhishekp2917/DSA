import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution {
    public boolean possibleBipartition(int n, int[][] dislikes) {
        HashMap<Integer, List<Integer>> adjList = getAdjList(n, dislikes);
        boolean[] visited = new boolean[n+1];
        int[] nodeValue = new int[n+1];
        // declare a variable to store the result of the dfs of the nodes
        boolean result = true;
        for(int i=1; i<=n; i++) {
            // if the node is not visited, then call the dfs with the node value as 1
            if(!visited[i]) {
                // update the result with the result of the dfs of the node using AND operation because if any of the node is not bipartite, then the whole graph is not bipartite
                result = result && dfs(adjList, i, -1, 1, visited, nodeValue);
            } 
        }
        return result;
    }

    private boolean dfs(HashMap<Integer, List<Integer>> adjList, int curr, int parent, int currValue, boolean[] visited, int[] nodeValue) {
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
        for(int neighbour : adjList.get(curr)) {
            if(neighbour==parent) continue;
            // alternate the currValue for the neighbour node and call the dfs
            // use AND operation to store the result of the dfs of the neighbours because if any of the neighbour is not bipartite, then the whole graph is not bipartite
            result = result && dfs(adjList, neighbour, curr, -currValue, visited, nodeValue);
        }
        return result;
    }

    private HashMap<Integer, List<Integer>> getAdjList(int n, int[][] dislikes) {
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        for(int i=1; i<=n; i++) {
            adjList.put(i, new ArrayList<>());
        }
        for(int i=0; i<dislikes.length; i++) {
            int person1 = dislikes[i][0];
            int person2 = dislikes[i][1];
            adjList.get(person1).add(person2);
            adjList.get(person2).add(person1);
        }
        return adjList;
    }
}
