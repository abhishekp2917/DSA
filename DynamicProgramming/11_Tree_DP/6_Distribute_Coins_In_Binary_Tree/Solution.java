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

    private int minMoves;

    public int distributeCoins(TreeNode root) {
        countMoves(root);
        return this.minMoves;   
    }

    private int countMoves(TreeNode root) {
        if(root==null) return 0;
        int excessCoinsFromLeft = countMoves(root.left);
        int excessCoinsFromRight = countMoves(root.right);
        int excessCoinsAtRoot = root.val-1;
        int excessCoins = excessCoinsAtRoot + excessCoinsFromLeft + excessCoinsFromRight;
        minMoves += Math.abs(excessCoins);
        return excessCoins;
    }
}