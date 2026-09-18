import java.util.ArrayList;
import java.util.List;

class Solution {

    public long maximumScoreAfterOperations(int[][] edges, int[] values) {

        int n = values.length;

        // Build the tree
        // from the given edge list.
        List<Integer>[] tree = new ArrayList[n];

        for(int i=0; i<n; i++) {
            tree[i] = new ArrayList<>();
        }

        for(int[] edge : edges) {
            tree[edge[0]].add(edge[1]);
            tree[edge[1]].add(edge[0]);
        }

        // memo[node][isAncestorNonZero]:
        //
        // stores the maximum score obtainable
        // from the subtree rooted at 'node'.
        //
        // isAncestorNonZero == 0
        // -> Every ancestor on the current path
        //    has already been reduced to zero.
        //    Therefore at least one node
        //    in this subtree must remain non-zero
        //    to keep every root-to-leaf path valid.
        //
        // isAncestorNonZero == 1
        // -> Some ancestor already remains non-zero.
        //    Hence this entire subtree
        //    is free to become zero if beneficial.
        Long[][] memo = new Long[n][2];

        return recursion(tree, values, 0, 0, 0, memo);
    }

    private long recursion(List<Integer>[] tree, int[] values, int root, int parent, int isAncestorNonZero, Long[][] memo) {

        // Leaf node.
        //
        // If an ancestor already remains non-zero,
        // this leaf may safely contribute its value.
        //
        // Otherwise this leaf itself must remain non-zero
        // to keep the path from becoming all zeros,
        // so we cannot collect its value.
        if(tree[root].size()==1 && root!=0) {
            return (isAncestorNonZero==1)
                    ? values[root]
                    : 0;
        }

        // Return previously computed answer.
        if(memo[root][isAncestorNonZero]!=null) {
            return memo[root][isAncestorNonZero];
        }

        long scoreWithoutRoot = 0;

        long scoreWithRoot = values[root];

        // Process every child independently.
        //
        // Tree DP works because
        // every subtree contributes independently
        // once the parent's decision is fixed.
        for(Integer child : tree[root]) {

            if(child==parent) continue;

            // Case 1:
            // Current node is NOT kept.
            //
            // Therefore every child
            // now knows that an ancestor
            // remains non-zero.
            scoreWithoutRoot +=
                recursion(
                    tree,
                    values,
                    child,
                    root,
                    1,
                    memo
                );

            // Case 2:
            // Current node itself remains non-zero.
            //
            // Child inherits the same ancestor state
            // because current node already satisfies
            // the path constraint.
            scoreWithRoot +=
                recursion(
                    tree,
                    values,
                    child,
                    root,
                    isAncestorNonZero,
                    memo
                );
        }

        long maxScore;

        // If an ancestor is already non-zero,
        // then current node MUST also be collected,
        // because the subtree no longer needs
        // to preserve any node for the path.
        if(isAncestorNonZero==1) {

            maxScore = scoreWithRoot;
        }

        // Otherwise,
        // we may either:
        //
        // keep current node
        //
        // OR
        //
        // force one of the descendants
        // to remain non-zero.
        else {

            maxScore =
                Math.max(
                    scoreWithoutRoot,
                    scoreWithRoot
                );
        }

        memo[root][isAncestorNonZero] = maxScore;

        return maxScore;
    }
}