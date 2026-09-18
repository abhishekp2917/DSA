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
    
    public void flatten(TreeNode root) {
        buildLinkedList(root);
    }

    private TreeNode buildLinkedList(TreeNode root) {
        if(root==null) return null;
        TreeNode leftTail = buildLinkedList(root.left);
        TreeNode rightTail = buildLinkedList(root.right);
        TreeNode leftHead = root.left;
        TreeNode rightHead = root.right;
        root.left = null;
        TreeNode tail = root;
        if(leftTail!=null) {
            tail.right = leftHead;
            tail = leftTail;
        }
        if(rightTail!=null) {
            tail.right = rightHead;
            tail = rightTail;
        }
        return tail;
    }
}