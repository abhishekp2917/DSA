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

    private TreeNode prev;
    private TreeNode first;
    private TreeNode second;

    public void recoverTree(TreeNode root) {
        findIncorrectNodes(root);
        int temp = this.first.val;
        this.first.val = this.second.val;
        this.second.val = temp;
    }

    private void findIncorrectNodes(TreeNode root) {
        if(root==null) return;
        findIncorrectNodes(root.left);
        if(this.prev!=null && this.prev.val>root.val) {
            if(this.first==null) {
                this.first = this.prev;   
            }
            this.second = root;
        }
        this.prev = root;
        findIncorrectNodes(root.right);
    }
}