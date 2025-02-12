import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public int minimumEffortPath(int[][] heights) {
        // Get the dimensions of the grid
        int n = heights.length;
        int m = heights[0].length;
        
        // 2D array to store the minimum effort required to reach each cell
        int[][] pathEfforts = new int[n][m];
        
        // Directions array to explore neighbors (up, down, left, right)
        int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        // Initialize all cells with maximum possible effort (Infinity)
        for(int i=0; i<n; i++) {
            Arrays.fill(pathEfforts[i], Integer.MAX_VALUE);
        }
        
        // Starting point has zero effort
        pathEfforts[0][0] = 0;
        
        // Priority Queue to process cells based on minimum effort required
        // Using lambda function to compare path efforts of two positions
        PriorityQueue<Integer[]> queue = new PriorityQueue<>((pos1, pos2) -> 
            pathEfforts[pos1[0]][pos1[1]] - pathEfforts[pos2[0]][pos2[1]]
        );
        
        // Start from the top-left corner
        queue.add(new Integer[]{0, 0});
        
        // BFS-like approach using PriorityQueue (Dijkstra's algorithm)
        while(!queue.isEmpty()) {
            // Get the current position
            int x = queue.peek()[0];
            int y = queue.peek()[1];
            queue.poll();
            
            // Explore all 4 neighboring cells
            for(int[] direction : directions) {
                int newX = x + direction[0];
                int newY = y + direction[1];
                
                // Check if the new position is within grid bounds
                if(newX >= 0 && newY >= 0 && newX < n && newY < m) {
                    // Calculate the effort to move to the new cell which is the maximum of effort to reach current cell and height difference between current and neighbour cell
                    int heightDiff = Math.abs(heights[x][y] - heights[newX][newY]);
                    int maxPathEffort = Math.max(pathEfforts[x][y], heightDiff); 
                    
                    // If the new path has less effort, update and push to queue to explore further else no need to add to queue 
                    if(maxPathEffort < pathEfforts[newX][newY]) {
                        pathEfforts[newX][newY] = maxPathEffort;
                        queue.add(new Integer[] {newX, newY});
                    }
                }
            }
        }
        
        // Return the minimum effort required to reach the bottom-right corner
        return pathEfforts[n-1][m-1];
    }
}
