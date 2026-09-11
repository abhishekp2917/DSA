import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
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

// Approach 1 : Recursion
class Solution1 {

    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> postorder = new ArrayList<>();
        traverse(root, postorder);
        return postorder;
    }

    private void traverse(TreeNode root, List<Integer> postorder) {
        if(root==null) return;
        traverse(root.left, postorder);
        traverse(root.right, postorder);
        postorder.add(root.val);
    }
}

// Approach 2 : Iterative (reversing preorder traversal [root -> right -> left] to get postorder)
class Solution {

    public List<Integer> postorderTraversal(TreeNode root) {
        if(root==null) return new ArrayList<>(); 
        List<Integer> postorder = new ArrayList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        stack.addLast(root);

        while(!stack.isEmpty()) {
            TreeNode node = stack.removeLast();
            postorder.add(node.val);
            if(node.left!=null) stack.addLast(node.left);
            if(node.right!=null) stack.addLast(node.right);
        }

        Collections.reverse(postorder);
        return postorder;
    }
}