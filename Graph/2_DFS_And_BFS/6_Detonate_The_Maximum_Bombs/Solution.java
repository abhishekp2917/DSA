import java.util.ArrayList;
import java.util.List;

class Solution {
    public int maximumDetonation(int[][] bombs) {
        int n = bombs.length;
        int maxDetonation = 0;
        // get adjacency list of bombs where adjList[i] contains all the bombs that lies within the detonation radius of bomb i
        List<List<Integer>> adjList = getAdjList(n, bombs);
        for(int i=0; i<n; i++) {
            // for each bomb, find the maximum number of bombs that can be detonated if bomb i is detonated
            // create a boolean array for each bomb to keep track of visited bombs
            // and pass it to dfs method to find the number of bombs that can be detonated if bomb i is detonated
            boolean[] pathVisited = new boolean[n];
            maxDetonation = Math.max(maxDetonation, dfs(adjList, i, pathVisited));
        }        
        return maxDetonation;
    }

    // method to perform dfs traversal on the adjacency list of bombs
    // to find the number of bombs that can be detonated if bomb src is detonated
    // and return the count of bombs that can be detonated
    private int dfs(List<List<Integer>> adjList, int src, boolean[] pathVisited) {
        if(pathVisited[src]) return 0;
        pathVisited[src] = true;
        int count = 1;
        for(Integer neighbour : adjList.get(src)) {
            count += dfs(adjList, neighbour, pathVisited);
        }
        return count;
    }

    // method to get adjacency list of bombs
    // adjList[i] contains all the bombs that lies within the detonation radius of bomb i
    // due to which if bomb i is detonated, all the bombs in adjList[i] will also be detonated
    private List<List<Integer>> getAdjList(int n, int[][] bombs) {
        List<List<Integer>> adjList = new ArrayList<>();
        for(int i=0; i<n; i++) {
            adjList.add(new ArrayList<>());
        }
        // iterate for each pair of bombs
        for(int i=0; i<n; i++) {
            for(int j=i+1; j<n; j++) {
                int r1 = bombs[i][2];
                int r2 = bombs[j][2];
                double distBetweenCenters = getDistBetweenCenters(bombs, i, j);
                // if bomb j lies within the detonation radius of bomb i, add j to adjList[i]
                if(distBetweenCenters<=r1) {
                    adjList.get(i).add(j);
                }
                // if bomb i lies within the detonation radius of bomb j, add i to adjList[j]
                if(distBetweenCenters<=r2) {
                    adjList.get(j).add(i);
                }
            }
        }
        return adjList;
    }

    // method to calculate distance between centers of two bombs radius
    private double getDistBetweenCenters(int[][] bombs, int i, int j) {
        int x1 = bombs[i][0], y1 = bombs[i][1];
        int x2 = bombs[j][0], y2 = bombs[j][1];
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }
}
