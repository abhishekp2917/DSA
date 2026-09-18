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

    private long maxProduct;

    public int maxProduct(TreeNode root) {
        final int MOD = 1000_000_007;
        long totalTreeSum = getTreeSum(root);
        getMaxProduct(root, totalTreeSum);
        return (int)(this.maxProduct%MOD);
    }

    private long getMaxProduct(TreeNode root, long totalTreeSum) {
        if(root==null) return 0;
        long leftSubTreeSum = getMaxProduct(root.left, totalTreeSum);
        long rightSubTreeSum = getMaxProduct(root.right, totalTreeSum);
        long subTreeSum = root.val + leftSubTreeSum + rightSubTreeSum;
        long otherSubTreeSum = totalTreeSum-subTreeSum;
        this.maxProduct = Math.max(this.maxProduct, subTreeSum*otherSubTreeSum);
        return subTreeSum;
    }

    private long getTreeSum(TreeNode root) {
        if(root==null) return 0L;
        return root.val + getTreeSum(root.left) + getTreeSum(root.right);
    }
}