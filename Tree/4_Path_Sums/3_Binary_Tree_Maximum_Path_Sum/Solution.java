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

class Solution {

    int maxPathSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        pathSumFromRoot(root);
        return this.maxPathSum;
    }

    private int pathSumFromRoot(TreeNode root) {
        if(root==null) return 0;
        int leftPathSum = pathSumFromRoot(root.left);
        int rightPathSum = pathSumFromRoot(root.right);
        if(leftPathSum<0) leftPathSum = 0;
        if(rightPathSum<0) rightPathSum = 0;
        maxPathSum = Math.max(
            maxPathSum,
            root.val + leftPathSum + rightPathSum
        );
        return root.val + Math.max(leftPathSum, rightPathSum);
    }
}


