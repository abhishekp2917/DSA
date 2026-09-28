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
    public TreeNode balanceBST(TreeNode root) {
        List<TreeNode> inorder = new ArrayList<>();
        getNodes(root, inorder);
        return buildTree(inorder, 0, inorder.size()-1);
    }

    private TreeNode buildTree(List<TreeNode> inorder, int start, int end) {
        if(start>end) return null;
        if(start==end) return inorder.get(start);
        int mid = start + (end-start)/2;
        TreeNode node = inorder.get(mid);
        node.left = buildTree(inorder, start, mid-1);
        node.right = buildTree(inorder, mid+1, end);
        return node;
    }

    private void getNodes(TreeNode root, List<TreeNode> inorder) {
        if(root==null) return;
        getNodes(root.left, inorder);
        inorder.add(root);
        getNodes(root.right, inorder);
        root.left = null;
        root.right = null;
    } 
}