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
        Map<TreeNode, Integer>[] memo = new HashMap[] { new HashMap<>(), new HashMap<>() };
        return recursion(root, 0, memo);
    }

    private Integer recursion(TreeNode root, int parentRobbed, Map<TreeNode, Integer>[] memo) {
        if(root==null) return 0;
        if(root.left==null && root.right==null) {
            return (parentRobbed==0)? root.val : 0;
        } 
        if(memo[parentRobbed].get(root)!=null) return memo[parentRobbed].get(root);
        int maxMoney = recursion(root.left, 0, memo) + recursion(root.right, 0, memo);
        if(parentRobbed==0) {
            maxMoney = Math.max(
                maxMoney,
                root.val + recursion(root.left, 1, memo) + recursion(root.right, 1, memo)
            );
        }
        memo[parentRobbed].put(root, maxMoney);
        return maxMoney;
    }
}

class Solution2 {
    public int rob(TreeNode root) {
        int[] maxMoney = recursion(root);
        return Math.max(maxMoney[0], maxMoney[1]);
    }

    private int[] recursion(TreeNode root) {
        if(root==null) return new int[2];
        if(root.left==null && root.right==null) {
            return new int[] {0, root.val }; 
        } 
        int[] left = recursion(root.left);
        int[] right = recursion(root.right);
        int[] maxMoney = new int[2];
        maxMoney[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]); 
        maxMoney[1] = root.val + left[0] + right[0]; 
        return maxMoney;
    }
}