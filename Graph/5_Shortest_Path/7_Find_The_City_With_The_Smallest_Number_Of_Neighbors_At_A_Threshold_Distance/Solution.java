import java.util.Arrays;

class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        // Create an adjacency matrix to represent the graph since it is recomended in Floyd-Warshal algo 
        int[][] adjMatrix = getAdjMatrix(n, edges);
        // minimum count of reachable city
        int minReachableCityCount = n;
        // city with minimum reachable city count
        int city = 0;
        // compute the shortest distance for each (i, j) city pair using Floyd-Warshal algo 
        for(int k=0; k<n; k++) {
            for(int i=0; i<n; i++) {
                for(int j=0; j<n; j++) {
                    int i_To_k = adjMatrix[i][k];
                    int k_To_j = adjMatrix[k][j];
                    if(i_To_k==Integer.MAX_VALUE || k_To_j==Integer.MAX_VALUE) continue;
                    int prevDist = adjMatrix[i][j];
                    int newDist = i_To_k + k_To_j;
                    adjMatrix[i][j] = Math.min(prevDist, newDist);
                }
            }
        }
        // iterate over each city 'i'
        for(int i=0; i<n; i++) {
            int reachableCityCount = 0;
            // count the number of city whose distance from city 'i' is atmost distanceThreshold
            for(int j=0; j<n; j++) {
                int dist = adjMatrix[i][j];
                if(dist<=distanceThreshold) reachableCityCount++;
            }
            // if count of city that are reachable is less than the current value, then update the count and city  
            if(reachableCityCount<=minReachableCityCount) {
                minReachableCityCount = reachableCityCount;
                city = i;
            }
        }
        return city;
    }

    private int[][] getAdjMatrix(int n, int[][] edges) {
        int[][] adjMatrix = new int[n][n];
        for(int i=0; i<n; i++) {
            Arrays.fill(adjMatrix[i], Integer.MAX_VALUE);
            adjMatrix[i][i] = 0;
        }
        for(int i=0; i<edges.length; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            int w = edges[i][2];
            adjMatrix[a][b] = w;
            adjMatrix[b][a] = w;
        }
        return adjMatrix;
    }
}