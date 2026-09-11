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

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        traverse(root, inorder);
        return inorder;
    }

    private void traverse(TreeNode root, List<Integer> inorder) {
        if(root==null) return;
        traverse(root.left, inorder);
        inorder.add(root.val);
        traverse(root.right, inorder);
    }
}

// Approach 2 : Iterative
class Solution2 {

    public List<Integer> inorderTraversal(TreeNode root) {
        if(root==null) return new ArrayList<>(); 
        List<Integer> inorder = new ArrayList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        stack.addLast(root);
        while(!stack.isEmpty()) {
            while(stack.peekLast().left!=null) {
                stack.addLast(stack.peekLast().left);
            }
            while(!stack.isEmpty() && stack.peekLast().right==null) {
                inorder.add(stack.removeLast().val);
            }
            if(!stack.isEmpty()) {
                TreeNode node = stack.removeLast();
                inorder.add(node.val);
                stack.addLast(node.right);
            }
        }
        return inorder;
    }
}





