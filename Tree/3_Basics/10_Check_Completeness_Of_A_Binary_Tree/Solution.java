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

    private int nodeCount;
    private int maxNodeIndex;

    public boolean isCompleteTree(TreeNode root) {
        countNodes(root, 1);
        return this.nodeCount==this.maxNodeIndex;
    }

    private void countNodes(TreeNode root, int nodeIndex) {
        if(root==null) return;
        this.nodeCount++;
        this.maxNodeIndex = Math.max(maxNodeIndex, nodeIndex);
        countNodes(root.left, 2*nodeIndex);
        countNodes(root.right, 2*nodeIndex+1);
    }
}