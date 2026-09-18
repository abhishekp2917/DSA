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

    private int longestPath = 1;

    public int longestUnivaluePath(TreeNode root) {
        if(root==null) return 0;
        recursion(root);
        return this.longestPath-1;
    }

    private int recursion(TreeNode root) {
        if(root==null) return 0;
        int leftPathLength = recursion(root.left);
        int rightPathLength = recursion(root.right);
        if(root.left!=null && root.val!=root.left.val) leftPathLength = 0;
        if(root.right!=null && root.val!=root.right.val) rightPathLength = 0;
        this.longestPath = Math.max(
            this.longestPath, 
            1 + leftPathLength + rightPathLength
        );
        return 1 + Math.max(leftPathLength, rightPathLength);
    }
}