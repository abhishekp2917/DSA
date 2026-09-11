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

    private boolean isBalanced = true;

    public boolean isBalanced(TreeNode root) {
        getHeight(root);
        return this.isBalanced;
    }

    private int getHeight(TreeNode root) {
        if(root==null) return 0; 
        if(root.left==null && root.right==null) return 1;
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        if(Math.abs(leftHeight-rightHeight)>1) this.isBalanced = false;
        return 1 + Math.max(leftHeight, rightHeight);
    }
}




