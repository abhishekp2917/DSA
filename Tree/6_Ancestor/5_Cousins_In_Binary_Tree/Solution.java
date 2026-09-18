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

    private boolean isCousins;

    public boolean isCousins(TreeNode root, int x, int y) {
        traverse(root, x, y, 0);
        return this.isCousins;
    }

    private Integer traverse(TreeNode root, int x, int y, int level) {
        if(root==null) return null;
        Integer left = traverse(root.left, x, y, level+1);
        Integer right = traverse(root.right, x, y, level+1);
        if(
            left!=null && 
            right!=null && 
            left==right &&
            (
                !(root.left.val==x && root.right.val==y) && 
                !(root.left.val==y && root.right.val==x)
            )
        ) {
            this.isCousins = true;
        }
        if(root.val==x || root.val==y) return level; 
        return (left!=null)? left : right;
    }
}