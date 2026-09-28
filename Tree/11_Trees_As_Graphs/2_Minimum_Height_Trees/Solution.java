import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Solution {

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> mhtRoots = new ArrayList<>();
        List<Integer>[] adjList = new ArrayList[n];
        int[] inDegree = new int[n];
        for(int node=0; node<n; node++) adjList[node] = new ArrayList<>();
        for(int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adjList[u].add(v);
            adjList[v].add(u);
            inDegree[u]++;
            inDegree[v]++;
        }
        Queue<Integer> queue = new ArrayDeque<>();
        for(int node=0; node<n; node++) {
            if(inDegree[node]<=1) queue.add(node);
        }

        int remainingNodes  = n;
        
        while(remainingNodes>2) {
            int currentLayerSize  = queue.size();
            
            while(currentLayerSize -->0) {
                int node = queue.poll();
                remainingNodes--;

                for(int neighbor : adjList[node]) {
                    inDegree[neighbor]--;
                    if(inDegree[neighbor]==1)  queue.add(neighbor);
                }
            }
        }
        while(!queue.isEmpty()) mhtRoots.add(queue.poll());
        return mhtRoots;
    }
}