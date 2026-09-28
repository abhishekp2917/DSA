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
    
    private int total;

    public TreeNode bstToGst(TreeNode root) {
        convertToGst(root);
        return root;
    }

    private void convertToGst(TreeNode root) {
        if(root==null) return;
        convertToGst(root.right);
        root.val += total;
        total = root.val;
        convertToGst(root.left);
    }
}