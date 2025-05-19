import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Solution {
    public int removeStones(int[][] stones) {
        final int n = 10000;  // max value of x and y coordinate used to generate unique IDs for (x, y) positions
        Map<Integer, Integer> parents = new HashMap<>();  // DSU parent map
        Map<Integer, Integer> sizes = new HashMap<>();    // DSU size map
        Set<Integer> rootSet = new HashSet<>();           // To track unique connected components (island roots)

        // Initialize DSU: Every stone is its own parent and has size 1
        for (int i = 0; i < stones.length; i++) {
            int x = stones[i][0];
            int y = stones[i][1];
            int posId = getId(x, y, n); // Convert 2D position to a unique ID
            parents.put(posId, posId);  // Each node is its own root initially
            sizes.put(posId, 1);  // Size of each component is 1 initially
        }

        // Sort the array based on their x coordinate so that stones of same row will be in continguous sequence
        Arrays.sort(stones, (pos1, pos2) -> Integer.compare(pos1[0], pos2[0]));
        for (int i = 0; i < stones.length - 1; i++) {
            // take two adjacent stones and check if they are in same row by comparing their x coordinate
            int[] pos1 = stones[i];
            int[] pos2 = stones[i + 1];
            // if two adjacent nodes are at same row, then merge them using DSU
            if (pos1[0] == pos2[0]) { 
                int id1 = getId(pos1[0], pos1[1], n);
                int id2 = getId(pos2[0], pos2[1], n);
                merge(parents, sizes, id1, id2); 
            }
        }

        // Sort the array based on their y coordinate so that stones of same column will be in continguous sequence
        Arrays.sort(stones, (pos1, pos2) -> Integer.compare(pos1[1], pos2[1]));
        for (int i = 0; i < stones.length - 1; i++) {
            // take two adjacent stones and check if they are in same column by comparing their y coordinate
            int[] pos1 = stones[i];
            int[] pos2 = stones[i + 1];
            // if two adjacent nodes are at same column, then merge them using DSU
            if (pos1[1] == pos2[1]) { 
                int id1 = getId(pos1[0], pos1[1], n);
                int id2 = getId(pos2[0], pos2[1], n);
                merge(parents, sizes, id1, id2);
            }
        }

        // After above two process, stones of same row and column will be part of same set 
        // Store the root node of each node in a set which will give the number of connected components
        for (int root : parents.values()) {
            int rootNode = getRootNode(parents, root); 
            rootSet.add(rootNode); // Store unique root
        }

        // Drom a component of size N, we can remove N-1 stones i.e. from each component theer will be one stone left which can't be removed
        // If there are N component then we can't remove N stones.
        // Max removable stones = total stones - number of connected components
        return stones.length - rootSet.size();
    }

    // Union operation with union by size optimization
    private void merge(Map<Integer, Integer> parents, Map<Integer, Integer> sizes, int u, int v) {
        int root1 = getRootNode(parents, u);
        int root2 = getRootNode(parents, v);
        if (root1 == root2) return; // Already connected

        int size1 = sizes.get(root1);
        int size2 = sizes.get(root2);

        // Attach smaller tree under the larger tree's root
        if (size1 < size2) {
            parents.put(root1, root2);
            sizes.put(root2, size1 + size2);
        } else {
            parents.put(root2, root1);
            sizes.put(root1, size1 + size2);
        }
    }

    // Find operation with path compression optimization
    private int getRootNode(Map<Integer, Integer> parents, int n) {
        if (parents.get(n) == n) return n; // Base case: root found
        int rootNode = getRootNode(parents, parents.get(n));
        parents.put(n, rootNode); // Path compression
        return rootNode;
    }

    // Convert 2D position to a unique ID (avoiding collisions)
    private int getId(int x, int y, int n) {
        return (n * x) + y;
    }
}
