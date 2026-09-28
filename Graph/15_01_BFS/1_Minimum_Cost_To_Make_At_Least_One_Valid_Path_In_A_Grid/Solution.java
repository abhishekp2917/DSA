import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

class Solution {

    public int minCost(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        
        int[][] dirs = new int[][] { {1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        int[][] minCost = new int[n][m];
        for(int i=0; i<n; i++) Arrays.fill(minCost[i], Integer.MAX_VALUE);
        minCost[0][0] = 0;
        
        Deque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] {0, 0, 0});
        
        while(!queue.isEmpty()) {
            int currRow = queue.peekFirst()[0];
            int currCol = queue.peekFirst()[1];
            int currCost = queue.peekFirst()[2];
            queue.pollFirst();
            for(int[] dir : dirs) {
                int nextRow = currRow + dir[0];
                int nextCol = currCol + dir[1];
                if(nextRow<0 || nextRow>=n || nextCol<0 || nextCol>=m) continue;
                int weight = getEdgeWeight(currRow, currCol, nextRow, nextCol, grid[currRow][currCol]);
                int nextCost = weight + currCost;
                if(minCost[nextRow][nextCol]>nextCost) {
                    minCost[nextRow][nextCol] = nextCost;
                    int[] nextCell = new int[] { nextRow, nextCol, nextCost };
                   
                    if(weight==0) queue.addFirst(nextCell);
                    else queue.addLast(nextCell);
                }
            }
        }
        return minCost[n-1][m-1];
    }

    private int getEdgeWeight(int currRow, int currCol, int nextRow, int nextCol, int direction) {
        if(direction==1) currCol++;
        else if(direction==2) currCol--;
        else if(direction==3) currRow++;
        else currRow--;
        return (currRow==nextRow && currCol==nextCol)? 0 : 1;
    }
}