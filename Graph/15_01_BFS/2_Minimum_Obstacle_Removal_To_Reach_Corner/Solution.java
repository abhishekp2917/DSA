import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

class Solution {

    public int minimumObstacles(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        
        int[][] dirs = new int[][] { {1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        int[][] minObstacles = new int[n][m];
        for(int row=0; row<n; row++) Arrays.fill(minObstacles[row], Integer.MAX_VALUE);
        minObstacles[0][0] = 0;
        
        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] {0, 0, 0});
        
        while(!queue.isEmpty()) {
            int currRow = queue.peekFirst()[0];
            int currCol = queue.peekFirst()[1];
            int currRemovalCost = queue.peekFirst()[2];
            queue.poll();
            
            for(int[] dir : dirs) {
                int nextRow = currRow + dir[0];
                int nextCol = currCol + dir[1];
                if(nextRow<0 || nextRow>=n || nextCol<0 || nextCol>=m) continue;
                int weight = grid[nextRow][nextCol];
                int nextRemovalCost = weight + currRemovalCost;
                if(minObstacles[nextRow][nextCol]>nextRemovalCost) {
                    minObstacles[nextRow][nextCol] = nextRemovalCost;

                    int[] nextCell = new int[] { nextRow, nextCol, nextRemovalCost };
                    if(weight==0) queue.addFirst(nextCell);
                    else queue.addLast(nextCell);
                }
            }
        }
        return minObstacles[n-1][m-1];
    }
}