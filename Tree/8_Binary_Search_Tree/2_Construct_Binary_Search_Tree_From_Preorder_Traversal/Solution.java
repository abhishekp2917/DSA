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

    private int preorderIdx;

    public TreeNode bstFromPreorder(int[] preorder) {
        return buildBST(preorder, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private TreeNode buildBST(int[] preorder, int lowVal, int highVal) {
        if( this.preorderIdx>=preorder.length ||
            preorder[this.preorderIdx]<lowVal || 
            preorder[this.preorderIdx]>highVal) return null;
        int val = preorder[this.preorderIdx++];
        return new TreeNode(
            val, 
            buildBST(preorder, lowVal, val-1),
            buildBST(preorder, val+1, highVal)
        );
    }
}