import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int latestDayToCross(int row, int col, int[][] cells) {
        int n = row*col;

        // Add two extra DSU nodes representing the entire top and bottom rows.
        // Connecting land cells in the first/last row to these virtual nodes
        // lets us check whether there is a continuous land path from top to bottom.
        int nodeCount = n + 2;
        final int TOP_VIRTUAL_ID = nodeCount-2;
        final int BOTTOM_VIRTUAL_ID = nodeCount-1;

        final int TOP_ROW = 0;
        final int BOTTOM_ROW = row-1;

        int[] sizes = new int[nodeCount];
        int[] parents = new int[nodeCount];

        // Initially every cell and both virtual nodes are separate components.
        Arrays.fill(sizes, 1);
        for(int i=0; i<nodeCount; i++) parents[i] = i;

        // isLand[r][c] tells whether this cell has been converted back to land
        // during the reverse-time processing.
        boolean[][] isLand = new boolean[row][col];

        int[][] dirs = new int[][] {
            {1, 0}, {0, 1}, {-1, 0}, {0, -1}
        };

        /*
         * Forward direction:
         *
         *     Day 1 -> Day 2 -> ... -> Day n
         *
         * cells[i] represents a cell that becomes water on that day.
         *
         * It is difficult to efficiently remove nodes/edges from DSU.
         *
         * Instead, process the days backwards:
         *
         *     Day n -> Day n-1 -> ... -> Day 1
         *
         * When processing cells[day], we turn that cell back into land.
         * This means we only need DSU additions, which DSU handles efficiently.
         *
         * The first time the top and bottom virtual nodes become connected
         * while going backwards corresponds to the latest day on which
         * crossing was still possible in the forward process.
         */
        for(int day=n-1; day>=0; day--) {
            int r = cells[day][0]-1;
            int c = cells[day][1]-1;

            // Restore this cell as land.
            isLand[r][c] = true;

            int currLandId = getCellId(col, r, c);

            // Connect this newly restored land cell to all adjacent cells
            // that have already been restored and are therefore also land.
            for(Integer adjLandId : getAdjacentLandId(row, col, isLand, dirs, r, c)) {
                merge(parents, sizes, currLandId, adjLandId);
            }

            // Any land cell in the first row is connected to the top virtual node.
            // Therefore, TOP_VIRTUAL_ID represents the entire top boundary.
            if(r==TOP_ROW) {
                merge(parents, sizes, currLandId, TOP_VIRTUAL_ID);
            }

            // Similarly, any land cell in the last row is connected to
            // the bottom virtual node.
            if(r==BOTTOM_ROW) {
                merge(parents, sizes, currLandId, BOTTOM_VIRTUAL_ID);
            }

            // If the two virtual nodes are connected, there is now a continuous
            // land path from the top row to the bottom row.
            //
            // Since we are processing backwards, this is the first point at
            // which crossing becomes possible again. Therefore, 'day' is the
            // latest day on which crossing was possible in the original process.
            if(findRootNode(parents, TOP_VIRTUAL_ID) == findRootNode(parents, BOTTOM_VIRTUAL_ID)) {
                return day;
            }
        }

        return -1;
    }


    private void merge(int[] parents, int[] sizes, int nodeU, int nodeV) {
        // Find the representatives of both connected components.
        int nodeURoot = findRootNode(parents, nodeU);
        int nodeVRoot = findRootNode(parents, nodeV);

        // Already connected, so this edge does not create a new component.
        if(nodeURoot==nodeVRoot) return;

        int nodeURootSize = sizes[nodeURoot];
        int nodeVRootSize = sizes[nodeVRoot];

        // Union by size:
        // attach the smaller component under the larger component
        // to keep the DSU tree shallow.
        if(nodeURootSize>nodeVRootSize) {
            parents[nodeURoot] = nodeVRoot;
            sizes[nodeVRoot] += nodeURootSize;
        }
        else {
            parents[nodeVRoot] = nodeURoot;
            sizes[nodeURoot] += nodeVRootSize;
        }
    }


    private List<Integer> getAdjacentLandId(int row, int col, boolean[][] isLand, int[][] dirs, int r, int c) {
        List<Integer> landIds = new ArrayList<>();

        // Check all four neighboring cells.
        for(int[] dir : dirs) {
            int adjR = r + dir[0];
            int adjC = c + dir[1];

            // Ignore out-of-bound cells and neighboring water cells.
            if(adjR<0 || adjR>=row ||
               adjC<0 || adjC>=col ||
               !isLand[adjR][adjC]) {
                continue;
            }

            // Convert the 2D coordinate into its DSU node ID.
            landIds.add(getCellId(col, adjR, adjC));
        }

        return landIds;
    }


    private int findRootNode(int[] parents, int node) {

        // A node whose parent is itself is the representative of
        // its connected component.
        if(parents[node]==node) return node;

        // Path compression:
        // directly connect this node to the component root so that
        // future find operations become faster.
        return parents[node] = findRootNode(parents, parents[node]);
    }


    private int getCellId(int col, int r, int c) {

        // Flatten the 2D grid coordinate into a unique DSU node ID.
        //
        // Example for col = 4:
        //
        // (0,0) -> 0
        // (0,1) -> 1
        // (1,0) -> 4
        // (1,1) -> 5
        //
        // This allows the 2D grid cells to be represented inside
        // the one-dimensional DSU arrays.
        return r*col + c;
    }
}