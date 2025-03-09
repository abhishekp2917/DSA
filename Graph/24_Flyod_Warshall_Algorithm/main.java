import java.util.*;

class Solution {
    public int maximumDetonation(int[][] bombs) {
        int n = bombs.length;
        int maxDetonation = 0;
        List<List<Integer>> adjList = getAdjList(n, bombs);
        boolean[] visited = new boolean[n];
        for(int i=0; i<n; i++) {
            maxDetonation = Math.max(maxDetonation, dfs(adjList, i, visited));
        }        
        return maxDetonation;
    }

    private int dfs(List<List<Integer>> adjList, int src, boolean[] visited) {
        if(visited[src]) return 0;
        visited[src] = true;
        int count = 1;
        for(Integer neighbour : adjList.get(src)) {
            count += dfs(adjList, neighbour, visited);
        }
        return count;
    }

    private List<List<Integer>> getAdjList(int n, int[][] bombs) {
        List<List<Integer>> adjList = new ArrayList<>();
        for(int i=0; i<n; i++) {
            adjList.add(new ArrayList<>());
        }
        for(int i=0; i<n; i++) {
            for(int j=i+1; j<n; j++) {
                if(areIntersectingCircles(bombs, i, j)) {
                    adjList.get(i).add(j);
                    adjList.get(j).add(i);
                }
            }
        }
        return adjList;
    }

    private boolean areIntersectingCircles(int[][] bombs, int i, int j) {
        int x1 = bombs[i][0], y1 = bombs[i][1], r1 = bombs[i][2];
        int x2 = bombs[i][0], y2 = bombs[i][1], r2 = bombs[j][2];
        int distBetweenCenters = (int)(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
        return distBetweenCenters<=r1+r2;
    }
}