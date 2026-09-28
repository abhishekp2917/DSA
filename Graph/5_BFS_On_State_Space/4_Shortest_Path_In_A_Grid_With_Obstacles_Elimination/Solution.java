import java.util.Queue;
import java.util.ArrayDeque;


class Solution {
    
    public int shortestPath(int[][] grid, int k) {
        int n = grid.length;
        int m = grid[0].length;
        if(n==1 && m==1) return 0;
        int[][] dirs = new int[][] { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
    
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] { 0, 0, k });

        boolean[][][] isVisited = new boolean[n][m][k+1];
        for(int i=0; i<=k; i++) isVisited[0][0][i] = true;

        int minStep = 0;

        while(!queue.isEmpty()) {
            
            int size = queue.size();
            minStep++;

            while(size-->0) {
                int r1 = queue.peek()[0];
                int c1 = queue.peek()[1];
                int k1 = queue.peek()[2];
                queue.poll();

                for(int[] dir : dirs) {
                    int r2 = r1 + dir[0];
                    int c2 = c1 + dir[1];
                    if(r2<0 || r2>=n || c2<0 || c2>=m) continue;
                    boolean hasObstacle = grid[r2][c2]==1;
                    int k2 = (hasObstacle)? k1-1: k1;
                    if(k2<0 || isVisited[r2][c2][k2]) continue;
                    if(r2==n-1 && c2==m-1) return minStep;
                    
                    queue.add(new int[] { r2, c2, k2 });
                    isVisited[r2][c2][k2] = true;
                }
            }
        }
        return -1;
    }
}