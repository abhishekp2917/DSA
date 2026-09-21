import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    
    public int maxDistance(int[][] grid) {
        int n = grid.length;
        int maxDistance = 0;
        int[][] distance = new int[n][n];
        Queue<int[]> queue = new LinkedList<>(); 
        int[][] dirs = new int[][] { {1, 0}, {-1, 0}, {0, -1}, {0, 1} };
        for(int row=0; row<n; row++) Arrays.fill(distance[row], Integer.MAX_VALUE);
        for(int row=0; row<n; row++) {
            for(int col=0; col<n; col++) {
                if(grid[row][col]==1) {
                    distance[row][col] = 0;
                    queue.add(new int[]{ row, col});
                }
            }
        }
        while(!queue.isEmpty()) {
            int row = queue.peek()[0];
            int col = queue.peek()[1];
            queue.poll();
            for(int[] dir : dirs) {
                int nextRow = row + dir[0];
                int nextCol = col + dir[1];
                if(nextRow<0 || nextRow>=n || nextCol<0 || nextCol>=n || distance[nextRow][nextCol]!=Integer.MAX_VALUE) continue;
                distance[nextRow][nextCol] = distance[row][col]+1;
                queue.add(new int[]{ nextRow, nextCol });
                maxDistance = Math.max(maxDistance, distance[nextRow][nextCol]);
            }
        }
        return (maxDistance!=0)? maxDistance : -1;
    }
}