class Solution {

    /*
        Intuition:

        We need the minimum time 't' such that we can travel from (0,0) to (n-1,n-1).

        At time = t:
            We are allowed to step only on cells where grid[i][j] <= t.

        So the problem becomes:

            What is the minimum t such that there exists a path from start to end
            using only cells <= t ?

        Key Observation:
            If we can reach destination at time t, then we can also reach at any time > t.

        This gives MONOTONIC behavior:
            feasible(t) = true → feasible(t+1) = true

        So we can Binary Search on time.
    */

    public int swimInWater(int[][] grid) {

        int n = grid.length;

        /*
            Time range:
            Minimum possible time: 0 (theoretically)
            Maximum possible time: n*n - 1 (since grid contains 0 to n*n-1)
            We take maxTime = n*n for safe upper bound.
        */
        int minTime = 0, maxTime = n * n;
        int answer = -1;

        // Binary Search on the ANSWER (minimum time)
        while (minTime <= maxTime) {

            // Mid represents a candidate time
            int time = minTime + (maxTime - minTime) / 2;

            /*
                We check:
                If grid[0][0] <= time
                AND
                there exists a path using only cells <= time

                Why check grid[0][0] <= time?
                Because if starting cell itself is higher,
                we cannot even begin.
            */
            if (grid[0][0] <= time &&
                isPoss(grid, 0, 0, new boolean[n][n], time)) {

                // This time is feasible
                answer = time;

                // Since we want MINIMUM feasible time, try to find smaller one
                maxTime = time - 1;
            } else {
                // Not feasible → need more time
                minTime = time + 1;
            }
        }

        return answer;
    }

    /*
        This function checks whether we can reach bottom-right from (x, y) using only cells <= maxTime.
        This is just a DFS reachability check.
    */
    private boolean isPoss(int[][] grid, int x, int y, boolean[][] visited, int maxTime) {
        int n = grid.length;

        /*
            Base cases:

            1) Out of bounds
            2) Already visited
            3) Cell value > allowed time
        */
        if (x < 0 || y < 0 || x >= n || y >= n
                || visited[x][y]
                || grid[x][y] > maxTime)
            return false;

    
        // If we reached destination, path exists.
        if (x == n - 1 && y == n - 1)
            return true;

        // Mark current cell as visited
        visited[x][y] = true;

        
        // Explore 4 directions (standard grid DFS)
        int[][] dirs = new int[][] {
                {1, 0},   // down
                {0, 1},   // right
                {-1, 0},  // up
                {0, -1}   // left
        };

        for (int[] dir : dirs) {

            /*
                If ANY direction leads to destination, return true immediately.
                This is important: We stop early once path is found.
            */
            if (isPoss(grid,
                       x + dir[0],
                       y + dir[1],
                       visited,
                       maxTime))
                return true;
        }

        /*
            If no direction worked, path not possible from here.
        */
        return false;
    }
}
