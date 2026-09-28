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
    
    private int k;
    private int kthSmallest;

    public int kthSmallest(TreeNode root, int k) {
        this.k = k;
        getKthSmallest(root);
        return this.kthSmallest;
    }

    private void getKthSmallest(TreeNode root) {
        if(root==null || this.k==0) return;
        getKthSmallest(root.left);
        if(--this.k==0) this.kthSmallest = root.val;
        getKthSmallest(root.right);

    }
}





