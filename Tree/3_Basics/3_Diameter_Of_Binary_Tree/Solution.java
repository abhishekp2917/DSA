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

    int diameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        getHeight(root);
        return this.diameter;
    }

    private int getHeight(TreeNode root) {
        if(root==null) return 0; 
        if(root.left==null && root.right==null) return 1;
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        this.diameter = Math.max(
            this.diameter,
            leftHeight + rightHeight
        );
        return 1 + Math.max(leftHeight, rightHeight);
    }
}