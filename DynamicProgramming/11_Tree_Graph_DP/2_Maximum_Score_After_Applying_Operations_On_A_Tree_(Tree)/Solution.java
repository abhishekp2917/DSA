import java.util.ArrayList;
import java.util.List;

class Solution1 {
    public long maximumScoreAfterOperations(int[][] edges, int[] values) {
        int n = values.length;
        List<Integer>[] tree = new ArrayList[n];
        for(int i=0; i<n; i++) tree[i] = new ArrayList<>();
        for(int[] edge : edges) {
            tree[edge[0]].add(edge[1]);
            tree[edge[1]].add(edge[0]);
        }
        Long[][] memo = new Long[n][2];
        return recursion(tree, values, 0, 0, 0, memo);
    }

    private long recursion(List<Integer>[] tree, int[] values, int root, int parent, int isAncestorNonZero, Long[][] memo) {
        if(tree[root].size()==1 && root!=0) {
            return (isAncestorNonZero==1)? values[root] : 0;
        }
        if(memo[root][isAncestorNonZero]!=null) return memo[root][isAncestorNonZero];
        long maxscore = 0;
        long scoreWithoutRoot = 0;
        long scoreWithRoot = values[root];
        for(Integer child : tree[root]) {
            if(child==parent) continue;
            scoreWithoutRoot += recursion(tree, values, child, root, 1, memo);
            scoreWithRoot += recursion(tree, values, child, root, isAncestorNonZero, memo);
        }
        if(isAncestorNonZero==1) maxscore = scoreWithRoot;
        else maxscore = Math.max(
            scoreWithoutRoot,
            scoreWithRoot 
        ); 
        memo[root][isAncestorNonZero] = maxscore;
        return maxscore;
    }
}