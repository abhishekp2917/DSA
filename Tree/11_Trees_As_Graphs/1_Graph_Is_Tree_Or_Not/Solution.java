import java.util.ArrayList;
import java.util.List;

class Solution {
    
    public boolean isTree(int n, int noOfEdges, int[][] edges) {
        if(noOfEdges!=n-1) return false;
        List<Integer>[] adjList = new ArrayList[n];
        for(int node=0; node<n; node++) adjList[node] = new ArrayList<>();
        for(int[] edge : edges) {
            adjList[edge[0]].add(edge[1]);
            adjList[edge[1]].add(edge[0]);
        }
        return countNodes(adjList, 0, new boolean[n])==n;
    }
    
    private int countNodes(List<Integer>[] adjList, int root, boolean[] visited) {
        if(visited[root]) return 0;
        visited[root] = true;
        int count = 1;
        for(int child : adjList[root]) {
            count += countNodes(adjList, child, visited);   
        }
        return count;
    }
}