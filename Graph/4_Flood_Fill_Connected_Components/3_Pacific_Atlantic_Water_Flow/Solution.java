import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {

    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        int n = heights.length;
        int m = heights[0].length;

        List<List<Integer>> result = new ArrayList<>();
        
        boolean[][] pacific = new boolean[n][m];
        boolean[][] atlantic = new boolean[n][m];

        int[][] dirs = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

        // Pacific: top and left boundaries.
        for (int col=0; col<m; col++) {
            traverse(heights, dirs, pacific, 0, col);
        }

        for (int row=0; row<n; row++) {
            traverse(heights, dirs, pacific, row, 0);
        }

        // Atlantic: bottom and right boundaries.
        for (int col=0; col<m; col++) {
            traverse(heights, dirs, atlantic, n-1, col);
        }

        for (int row=0; row<n; row++) {
            traverse(heights, dirs, atlantic, row, m-1);
        }

        // Cells reachable from both oceans.
        for (int row=0; row<n; row++) {
            for (int col=0; col<m; col++) {
                if (pacific[row][col] && atlantic[row][col]) {
                    result.add(Arrays.asList(row, col));
                }
            }
        }

        return result;
    }

    private void traverse(int[][] heights, int[][] dirs, boolean[][] visited, int row, int col) {
        int n = heights.length;
        int m = heights[0].length;

        if (visited[row][col]) return;
        visited[row][col] = true;

        for (int[] dir : dirs) {
            int nextRow = row + dir[0];
            int nextCol = col + dir[1];

            if (nextRow < 0 || nextRow >= n ||
                nextCol < 0 || nextCol >= m ||
                visited[nextRow][nextCol] ||
                heights[nextRow][nextCol]<heights[row][col]) {
                continue;
            }

            traverse(heights, dirs, visited, nextRow, nextCol);
        }
    }
}