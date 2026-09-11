import java.util.ArrayDeque;
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

// Approach 1 : Recursion
class Solution1 {

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> preorder = new ArrayList<>();
        traverse(root, preorder);
        return preorder;
    }

    private void traverse(TreeNode root, List<Integer> preorder) {
        if(root==null) return;
        preorder.add(root.val);
        traverse(root.left, preorder);
        traverse(root.right, preorder);
    }
}


// Approach 2 : Iterative
class Solution2 {

    public List<Integer> preorderTraversal(TreeNode root) {
        if(root==null) return new ArrayList<>(); 
        List<Integer> preorder = new ArrayList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        stack.addLast(root);

        while(!stack.isEmpty()) {
            TreeNode node = stack.removeLast();
            preorder.add(node.val);
            if(node.right!=null) stack.addLast(node.right);
            if(node.left!=null) stack.addLast(node.left);
        }
        return preorder;
    }
}