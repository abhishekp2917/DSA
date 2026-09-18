import java.util.ArrayList;
import java.util.List;

class Solution {

    private int longestPath = 1;

    public int longestPath(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] tree = new ArrayList[n];
        for(int node=0; node<n; node++) tree[node] = new ArrayList<>();
        for(int node=1; node<n; node++) {
            tree[parent[node]].add(node);
        }
        getLongestPath(tree, s, 0);
        return this.longestPath;
    }

    private int getLongestPath(List<Integer>[] tree, String s, int root) {
        if(tree[root].isEmpty()) return 1;
        char rootChar = s.charAt(root);
        int longestChildPathLen = 0;
        int secondLongestChildPathLen = 0;
        for(int child : tree[root]) {
            char childChar = s.charAt(child);
            int childPathLength = getLongestPath(tree, s, child);
            if(rootChar==childChar) continue;
            if(childPathLength>=longestChildPathLen) {
                secondLongestChildPathLen = longestChildPathLen;
                longestChildPathLen = childPathLength;
            }
            else if(childPathLength>=secondLongestChildPathLen) {
                secondLongestChildPathLen = childPathLength;
            }
        }
        this.longestPath = Math.max(
            this.longestPath, 
            1 + longestChildPathLen + secondLongestChildPathLen
        );
        return 1 + longestChildPathLen;
    }
}