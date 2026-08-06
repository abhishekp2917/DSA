// Definition for a binary tree node.

import java.util.HashMap;
import java.util.Map;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

class Solution1 {

    public int rob(TreeNode root) {

        // We maintain two memo tables.
        //
        // memo[0]:
        // stores answer when parent
        // has NOT been robbed.
        //
        // memo[1]:
        // stores answer when parent
        // HAS been robbed.
        //
        // Parent's state affects
        // whether current node
        // is allowed to be robbed.
        Map<TreeNode, Integer>[] memo =
            new HashMap[] {
                new HashMap<>(),
                new HashMap<>()
            };

        return recursion(root, 0, memo);
    }

    private Integer recursion(TreeNode root, int parentRobbed, Map<TreeNode, Integer>[] memo) {

        // Empty subtree contributes
        // no money.
        if(root==null) return 0;

        // Leaf node.
        //
        // If parent was robbed,
        // this node cannot be robbed.
        //
        // Otherwise robbing it
        // is always optimal.
        if(root.left==null && root.right==null) {
            return (parentRobbed==0) ? root.val : 0;
        }

        // Return previously computed answer.
        if(memo[parentRobbed].get(root)!=null) {
            return memo[parentRobbed].get(root);
        }

        // Option 1:
        // Do NOT rob current house.
        //
        // Since current node is skipped,
        // both children become free
        // to either rob or skip themselves.
        int maxMoney =
            recursion(root.left, 0, memo) +
            recursion(root.right, 0, memo);

        // Option 2:
        // Rob current house.
        //
        // This is possible only if
        // parent was not robbed.
        //
        // Once current node is robbed,
        // both children become forbidden
        // from being robbed.
        if(parentRobbed==0) {

            maxMoney = Math.max(
                maxMoney,
                root.val +
                recursion(root.left, 1, memo) +
                recursion(root.right, 1, memo)
            );
        }

        // Store best answer
        // for this state.
        memo[parentRobbed].put(root, maxMoney);

        return maxMoney;
    }
}

class Solution2 {

    public int rob(TreeNode root) {

        // recursion(root) returns
        // answers for both states
        // of the current node.
        int[] maxMoney = recursion(root);

        // Root may either be robbed
        // or skipped.
        return Math.max(maxMoney[0], maxMoney[1]);
    }

    private int[] recursion(TreeNode root) {

        // Empty subtree.
        //
        // Both states contribute zero.
        if(root==null) return new int[2];

        // Leaf node.
        //
        // If skipped -> 0
        // If robbed -> node value.
        if(root.left==null && root.right==null) {
            return new int[] {0, root.val};
        }

        // Compute answers
        // for both subtrees.
        int[] left = recursion(root.left);
        int[] right = recursion(root.right);

        // maxMoney[0]:
        // Maximum money when
        // current node is NOT robbed.
        //
        // Since current node is skipped,
        // each child independently chooses
        // whichever state gives more money.
        int skipCurrent =
            Math.max(left[0], left[1]) +
            Math.max(right[0], right[1]);

        // maxMoney[1]:
        // Maximum money when
        // current node IS robbed.
        //
        // Therefore both children
        // MUST be skipped.
        int robCurrent =
            root.val +
            left[0] +
            right[0];

        return new int[] {
            skipCurrent,
            robCurrent
        };
    }
}