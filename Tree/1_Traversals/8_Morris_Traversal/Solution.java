import java.util.ArrayList;
import java.util.List;

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

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();

        TreeNode curr = root;

        while (curr != null) {

            // No left subtree
            if (curr.left == null) {
                inorder.add(curr.val);       // Visit
                curr = curr.right;
            }

            else {
                // Find inorder predecessor
                TreeNode predecessor = curr.left;

                while (predecessor.right != null &&
                       predecessor.right != curr) {
                    predecessor = predecessor.right;
                }

                // First time reaching curr
                if (predecessor.right == null) {
                    predecessor.right = curr;   // Create thread
                    curr = curr.left;
                }

                // Second time reaching curr
                else {
                    predecessor.right = null;   // Remove thread
                    inorder.add(curr.val);       // Visit
                    curr = curr.right;
                }
            }
        }
        return inorder;
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> preorder = new ArrayList<>();

        TreeNode curr = root;

        while (curr != null) {

            // No left subtree
            if (curr.left == null) {
                preorder.add(curr.val);       // Visit
                curr = curr.right;
            }

            else {
                // Find inorder predecessor
                TreeNode predecessor = curr.left;

                while (predecessor.right != null &&
                    predecessor.right != curr) {
                    predecessor = predecessor.right;
                }

                // First time reaching curr
                if (predecessor.right == null) {
                    preorder.add(curr.val);       // Visit FIRST
                    predecessor.right = curr;   // Create thread
                    curr = curr.left;
                }

                // Second time reaching curr
                else {
                    predecessor.right = null;   // Remove thread
                    curr = curr.right;
                }
            }
        }

        return preorder;
    }
}