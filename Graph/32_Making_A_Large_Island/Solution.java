import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public int largestIsland(int[][] grid) {
        int len = grid.length;
        int maxSize = 0;

        // parents[i][j] stores a unique ID for Union-Find; initially, each cell is its own parent.
        int[][] parents = new int[len][len];

        // size[i][j] stores the size of the island rooted at position (i, j).
        int[][] size = new int[len][len];

        // Direction vectors: up, down, right, left
        int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

        // List of coordinates of all '0' cells to consider flipping
        List<int[]> zeroes = new ArrayList<>();

        // Initialize parents and size arrays for Union-Find
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                parents[i][j] = (len * i) + j;  // Flattened index
                if (grid[i][j] == 1) {
                    size[i][j] = 1;  // Initially, every land cell is its own island of size 1
                }
            }
        }

        // Connect all adjacent '1's to form islands using Union-Find
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (grid[i][j] == 1) {
                    int islandSize = 1;

                    // Try to union with neighboring land cells in 4 directions
                    for (int[] direction : directions) {
                        int x = i + direction[0];
                        int y = j + direction[1];

                        if (x >= 0 && x < len && y >= 0 && y < len && grid[x][y] == 1) {
                            int uParentNode = parents[x][y];
                            int vParentNode = parents[i][j];

                            int uRootNode = getRootNode(parents, len, uParentNode);
                            int vRootNode = getRootNode(parents, len, vParentNode);

                            if (uRootNode != vRootNode) {
                                // Merge two islands and update islandSize
                                islandSize = merge(parents, size, len, uRootNode, vRootNode);
                            }
                        }
                    }

                    // Track the maximum island size so far
                    maxSize = Math.max(maxSize, islandSize);
                } else {
                    // Save location of all water cells (0s)
                    zeroes.add(new int[] {i, j});
                }
            }
        }

        // Step 2: Try flipping each '0' to '1' and see what max island it connects to
        for (int[] zeroPos : zeroes) {
            int x = zeroPos[0];
            int y = zeroPos[1];
            int islandSize = 1; // We are converting this '0' to '1', so count starts at 1

            Set<Integer> rootNodeSet = new HashSet<>(); // To avoid counting same island multiple times

            // Look at 4 neighbors of the 0 cell
            for (int[] direction : directions) {
                int X = x + direction[0];
                int Y = y + direction[1];

                if (X >= 0 && X < len && Y >= 0 && Y < len && grid[X][Y] == 1) {
                    int parentNode = parents[X][Y];
                    int rootNode = getRootNode(parents, len, parentNode);
                    rootNodeSet.add(rootNode); // Add the root of neighboring island to the set
                }
            }

            // Sum the sizes of all distinct adjacent islands
            for (int rootNode : rootNodeSet) {
                int[] pos = getPos(rootNode, len);
                islandSize += size[pos[0]][pos[1]];
            }

            // Update maxSize if flipping results in a bigger island
            maxSize = Math.max(maxSize, islandSize);
        }

        return maxSize;
    }

    // Merge two islands by size and return new total size
    private int merge(int[][] parents, int[][] size, int len, int u, int v) {
        int[] uPos = getPos(u, len);
        int[] vPos = getPos(v, len);
        int uX = uPos[0], uY = uPos[1], vX = vPos[0], vY = vPos[1];
        int uSize = size[uX][uY];
        int vSize = size[vX][vY];

        if (uSize < vSize) {
            parents[uX][uY] = v; // Point u to v
            size[vX][vY] += uSize;
            return size[vX][vY];
        } else {
            parents[vX][vY] = u; // Point v to u
            size[uX][uY] += vSize;
            return size[uX][uY];
        }
    }

    // Find the root of a node with path compression
    private int getRootNode(int[][] parents, int len, int u) {
        int[] pos = getPos(u, len);
        int x = pos[0];
        int y = pos[1];

        if (parents[x][y] == u) return u;

        int rootNode = getRootNode(parents, len, parents[x][y]);
        parents[x][y] = rootNode; // Path compression
        return rootNode;
    }

    // Convert flattened index to 2D coordinates
    private int[] getPos(int num, int len) {
        int x = num / len;
        int y = num % len;
        return new int[] {x, y};
    }
}
