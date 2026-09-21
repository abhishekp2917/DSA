import java.util.ArrayList;
import java.util.List;

class Solution {
    boolean graphColoring(int v, int[][] edges, int m) {
        List<Integer>[] adjList = new ArrayList[v];
        for(int node=0; node<v; node++) adjList[node] = new ArrayList<>();
        for(int[] edge : edges) {
            if(edge[0]==edge[1]) continue;
            adjList[edge[0]].add(edge[1]);
            adjList[edge[1]].add(edge[0]);
        } 
        return isPossibleToColor(v, adjList, new int[v], m, 0);
    }

    private boolean isPossibleToColor(int v, List<Integer>[] adjList, int[] nodeColor, int m, int node) {
        if(node==v) return true;
        for(int color=1; color<=m; color++) {
            if(!isColorAvail(adjList, nodeColor, color, node)) continue;
            nodeColor[node] = color;
            if(isPossibleToColor(v, adjList, nodeColor, m, node+1)) return true;
            nodeColor[node] = 0;
        }
        return false;
    }

    private boolean isColorAvail(List<Integer>[] adjList, int[] nodeColor, int color, int node) {
        for(int neighbour : adjList[node]) {
            if(nodeColor[neighbour]==color) return false;
        }
        return true;
    }
}