class Solution {
    public void floydWarshall(int[][] dist) {
        int n = dist.length;
        // A large constant to represent "infinity" for unreachable paths
        final int INF = 100000000; 

        // Compute distnace for each possible intermediate node 'k' ('i'-> 'k' -> 'j')
        for(int k=0; k<n; k++) {
            // compute distnace for each path ('i' -> 'j') where 'k' is the intermediate node.
            for(int i=0; i<n; i++) {
                for(int j=0; j<n; j++) {
                    // distance from node 'i' to 'k' 
                    int i_To_k = dist[i][k];
                    // distance from node 'k' to 'j' 
                    int k_To_j = dist[k][j];
                    // if any of these distance is infinity that means there is no path through intermediate node so skip it.
                    if(i_To_k==INF || k_To_j==INF) continue;
                    int prevDist = dist[i][j];
                    int newDist = i_To_k + k_To_j;
                    // if newly computed distance is shorter than previous one, then update the distance
                    dist[i][j] = Math.min(prevDist, newDist);
                }
            }
        }
    }
}