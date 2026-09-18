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

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

class Solution {

    public boolean isSubPath(ListNode head, TreeNode root) {
        if (root==null) return false;
        return checkPath(root, head) || isSubPath(head, root.left) || isSubPath(head, root.right);
    }

    private boolean checkPath(TreeNode root, ListNode head) {
        if (head==null) return true;
        if (root==null || root.val!=head.val) return false;
        return checkPath(root.left, head.next) || checkPath(root.right, head.next);
    }
}