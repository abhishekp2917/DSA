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
    public int countNodes(TreeNode root) {
        int leftHeight = getLeftHeight(root);
        int rightHeight = getRightHeight(root);
        if(leftHeight==rightHeight) return (int)(Math.pow(2, leftHeight)-1);
        else return 1 + countNodes(root.left) + countNodes(root.right); 
    }

    private int getLeftHeight(TreeNode root) {
        if(root==null) return 0;
        return 1 + getLeftHeight(root.left);
    }

    private int getRightHeight(TreeNode root) {
        if(root==null) return 0;
        return 1 + getRightHeight(root.right);
    }
}