import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Solution {
    public int removeStones(int[][] stones) {
        final int n = 10000;
        Map<Integer, Integer> parents = new HashMap<>();
        Map<Integer, Integer> sizes = new HashMap<>();
        Set<Integer> rootSet = new HashSet<>();
        for(int i=0; i<stones.length; i++) {
            int x = stones[i][0];
            int y = stones[i][1];
            int posId = getId(x, y, n);
            parents.put(posId, posId);
            sizes.put(posId, 1);
        }
        Arrays.sort(stones, (pos1, pos2) -> Integer.compare(pos1[0], pos2[0]));
        for(int i=0; i<stones.length-1; i++) {
            int[] pos1 = stones[i];
            int[] pos2 = stones[i+1];
            int row1 = pos1[0], col1 = pos1[1];
            int row2 = pos2[0], col2 = pos2[1];
            if(row1==row2) {
                int id1 = getId(row1, col1, n);
                int id2 = getId(row2, col2, n);
                merge(parents, sizes, id1, id2);
            }
        }
        Arrays.sort(stones, (pos1, pos2) -> Integer.compare(pos1[1], pos2[1]));
        for(int i=0; i<stones.length-1; i++) {
            int[] pos1 = stones[i];
            int[] pos2 = stones[i+1];
            int row1 = pos1[0], col1 = pos1[1];
            int row2 = pos2[0], col2 = pos2[1];
            if(col1==col2) {
                int id1 = getId(row1, col1, n);
                int id2 = getId(row2, col2, n);
                merge(parents, sizes, id1, id2);
            }
        }
        for(int root : parents.values()) {
            int rootNode = getRootNode(parents, root);
            rootSet.add(rootNode);
        }
        return stones.length-rootSet.size();
    }

    private void merge(Map<Integer, Integer> parents, Map<Integer, Integer> sizes, int u, int v) {
        int root1 = getRootNode(parents, u);
        int root2 = getRootNode(parents, v);
        int size1 = sizes.get(u);
        int size2 = sizes.get(v);
        if(size1<size2) {
            parents.put(root1, root2);
            sizes.put(root2, size1+size2);
        }
        else {
            parents.put(root2, root1);
            sizes.put(root1, size1+size2);
        }
    }

    private int getRootNode(Map<Integer, Integer> parents, int n) {
        if(parents.get(n)==n) return n;
        int parentNode = parents.get(n);
        int rootNode = getRootNode(parents, parentNode);
        parents.put(n, rootNode);
        return rootNode;
    }

    private int getId(int x, int y, int n) {
        return (n*x)+y;
    }
}