import java.util.ArrayList;
import java.util.List;

class Solution {
    
    public int minReorder(int n, int[][] connections) {
        List<int[]>[] adjList = new ArrayList[n];
        for(int city=0; city<n; city++) adjList[city] = new ArrayList<>();
        for(int[] connection : connections) {
            int cityU = connection[0];
            int cityV = connection[1];
            adjList[cityU].add(new int[] { cityV, -1 });
            adjList[cityV].add(new int[] { cityU, 1 }); 
        }
        return count(adjList, 0, -1);
    }

    private int count(List<int[]>[] adjList, int city, int prevCity) {
        int count = 0;
        for(int[] nextCity : adjList[city]) {
            if(nextCity[0]==prevCity) continue;
            if(nextCity[1]==-1) count++;
            count += count(adjList, nextCity[0], city);
        }
        return count;
    }
}
